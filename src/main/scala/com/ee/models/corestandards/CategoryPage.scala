package com.ee.models.corestandards
import scala.xml.{ Node, Elem }
import scala.util.matching._

case class CategoryPage(
  url:String,
  category:Option[String] = None, 
  subCategories:Seq[SubCategorySection] = Seq()){

  def subCategoryForPath(path:String) : Option[String] = {

    def clean(s:String) : String = {
      if(s.endsWith("/")) 
        s.substring(0,s.length - 1)
      else 
        s
    }

    def containsStandard(path:String)(section:SubCategorySection) = {
      section.standards.exists{ s:Standard => clean(s.href) == clean(path) }
    }

    subCategories.filter( containsStandard(path) ) match {
      case Seq(sc) => sc.name 
      case _ => None
    }
  }
}

object CategoryPage{

  def apply(url:String, xml:Node, categoryRegex : Regex) : CategoryPage = {

    val contentRight = (xml \\ "div").filter(attributeEquals("id", "content-right"))
    
    val h1 = (contentRight \ "h1")
    val h2s = (contentRight \ "h2").toSeq.drop(1)
    val uls = (contentRight \ "ul").toSeq.drop(1)

    val sections : Seq[SubCategorySection] = h2s.zipWithIndex.map{ n:(Node,Int) => 
      val (h2,index) = n
      if(index < uls.length){
        Some(SubCategorySection(n._1, uls(n._2)))
      } else {
        None
      }
    }.flatten
    
    val categoryRegex(text) = h1.text
    new CategoryPage(url,Some(text.trim), sections)
  }


  private def attributeEquals(name: String, value: String)(node: Node) = {
    node.attribute(name).filter(t => value.equals(t.toString)).isDefined
  }
}

case class SubCategorySection(name:Option[String] = None, standards : Seq[Standard] = Seq())

object SubCategorySection {

  def apply( h2 : Node, ul : Node ) : SubCategorySection = {
    val standards : Seq[Standard] = (ul \\ "li").toSeq.map(Standard(_))
    SubCategorySection(Some(h2.text.trim), standards)
  }
}

case class Standard(href:String, text : String)

object Standard {
  def apply(li:Node) : Standard = {
    val a = (li \\ "a")
    val rawA = a.toString
    val href = a(0).attribute("href").get.text
    val aText = a(0).text
    val standard = li.toString
      .replace(rawA, "")
      .replace("<li>","")
      .replace("</li>","")
      .trim
    new Standard(href, standard)
  }
}