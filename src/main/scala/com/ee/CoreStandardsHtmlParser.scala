package com.ee

import xml._
import com.corespring.models.corestandards._
import scala.collection.mutable.Map 

object CategoryPages {
  val MathRegex = """.*»(.*)""".r
  val ELARegex = """(.*)».*""".r
}

class CategoryPages {
  
  private var pages : Map[String,CategoryPage] = Map()

  def load(path:String) : CategoryPage = {
    //println(pages.toSeq.map(_._1))
    if(!pages.get(path).isDefined){
      
    val regex = if(path.contains("Math")) 
      CategoryPages.MathRegex 
    else 
      CategoryPages.ELARegex 

      pages.put( path, CategoryPage(path, html(path), regex) )
    }
    pages.get(path).get
  }

  private def html(path:String) : Node = {
    println("parsing html from: " + path)
    val parser = new Html5Parser()
    val source = new org.xml.sax.InputSource("http://corestandards.org/"+ path)
    parser.loadXML(source)
  }
}

/**
 * Parses the html pages on corestandards.org to give you a category and subCategory for an item.
 * These properties aren't part of the xml for the standard.
 */
object CoreStandardsHtmlParser {

  val categoryPages = new CategoryPages()

  object CategoryPath{
   def apply(url:String) : String = {
     val Path = """.*corestandards\.org(.*)""".r  
     val Path(p) = url
     
     if(p.contains("ELA-Literacy")){
       val ELA = """/ELA-Literacy/(.*?)/(.*?)/.*""".r
       val ELA(s,g) = p
       "/ELA-Literacy/" + s + "/" + g   
     } else {
        val Math = """/Math/Content/(.*?)/(.*?)/.*""".r
        val Math(s,g) = p
        "/Math/Content/" + s + "/" + g 
     }
   } 
  }

  private val Math = "Math"
  private val ELALiteracy = "ELA-Literacy"
  private val RelativePath = """.*corestandards\.org(.*)""".r

  def parse(url: String): (Option[String], Option[String]) = {

    val path = CategoryPath(url)
    val page = categoryPages.load(path)
    val RelativePath(p) = url

    (page.category, page.subCategoryForPath(p) )
  }
}
