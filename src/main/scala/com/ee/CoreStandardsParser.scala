package com.ee

import models.Standard
import xml.{Node, Elem, XML}

object CoreStandardsParser {

  val LearningStandardItem: String = "LearningStandardItem"

  def run( urls : String* ) : String  = {

    def createCollection(xmlUrl: String) : Seq[Standard]  = {
      val xml = XML.load(xmlUrl)
      (xml \\ LearningStandardItem).map(Standard(_)).flatten
    }

    val allStandards = urls.toList.map(createCollection)
    //println(allStandards)
    com.codahale.jerkson.Json.generate(allStandards.flatten)
  }

}
