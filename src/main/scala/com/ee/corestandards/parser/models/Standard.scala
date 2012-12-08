package com.ee.corestandards.parser.models

import xml.{Node, Elem}
import util.matching.Regex
import com.ee.corestandards.parser.CoreStandardsHtmlParser

case class Standard(var dotNotation: String = "",
                    var refId: String = "",
                    var subject: String = "",
                    var category: String = "",
                    var subCategory: String = "",
                    var standard: String = "",
                    var uri: String = "")


object Standard {

  def apply(learningStandardItem: Node): Option[Standard] = {


    def buildStandard( n: Node,
                       standard: String,
                       category: String = "",
                       subCategory: String = ""): Option[Standard] = {

      getDotNotation(n) match {
        case None => None
        case Some(dn) => Some(
          Standard(
            dotNotation = dn,
            subject = getSubject(n)._1,
            standard = standard,
            refId = getRefId(n),
            uri = getUri(n),
            category = category,
            subCategory = subCategory
          )
       )
      }
    }

    def getDotNotation(n: Node): Option[String] = {
      val fullCode = (n \\ "StatementCode").text
      if (fullCode == null || fullCode == "") {
        println("Warning - StatementCode is null or empty")
        None
      } else {

        try {
          val subject = getSubject(n)
          val substitution = if (subject._1 == "ELA-Literacy") "ELA-Literacy" else subject._1 + "\\." + subject._2
          val Regex = ("""CCSS\.""" + substitution + """\.(.*)""").r
          val Regex(dn) = fullCode
          Some(dn)
        }
        catch {
          case e : MatchError => { 
            println(n); e.printStackTrace(); 
            throw new RuntimeException("error with: getDotNotation: " + n)
          }
        }
      }
    }

    def getSubject(n: Node): (String, String) = {
      val uri = (n \\ "RefURI").text
      val Regex = """http://corestandards.org/(.*?)/(.*?)/.*""".r
      val Regex(subject, subSubject) = uri
      (subject, subSubject)
    }

    def getRefId(n: Node): String = {
      val out = (n \ "@RefID").text
      out
    }

    def getUri(n: Node): String = (n \\ "RefURI").text
    val statement = (learningStandardItem \\ "Statement").text
    val refUri = (learningStandardItem \\ "RefURI").text
    val uri = getUri(learningStandardItem)

    try{
      val (category, subCategory) = CoreStandardsHtmlParser.parse(uri)
      buildStandard(learningStandardItem, statement, category.getOrElse(""), subCategory.getOrElse("") )
    }
    catch {
      case _ : Throwable => {
        println("this item failed ->" + uri)
        None
      }
    }

  }
}
