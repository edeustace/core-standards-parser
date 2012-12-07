package com.corespring.models.corestandards

import org.specs2.mutable.Specification
import com.corespring._
import com.corespring.models._
import java.io.FileInputStream
import scala.util.matching._

class CategoryPageSpec extends Specification {

  
  def pageFromPath(path:String, regex : Regex = CategoryPages.MathRegex ) = {
      val parser = new Html5Parser()

      val stream = this.getClass.getResourceAsStream(path)
      val source = new org.xml.sax.InputSource( stream )
      val xml = parser.loadXML(source)
      CategoryPage(path,xml, regex)
  }

  "page" should{


    "parse counting and cardinality html" in {

      val path = "/com/corespring/models/corestandards/CC.html" 
      val page = pageFromPath(path)
      page.subCategories.length === 3
      
      val lastSection = page.subCategories(2)
      lastSection.name === Some("Compare numbers.")
      lastSection.standards(0).text === """Identify whether the number of objects in one group is greater than, less than, or equal to the number of objects in another group, e.g., by using matching and counting strategies.<sup>1</sup>"""
      page.category === Some("Counting & Cardinality")
    }

    "trailing slash has no significance" in {
      val path = "/com/corespring/models/corestandards/CC.html" 
      val page = pageFromPath(path)
      page.subCategoryForPath("/Math/Content/K/CC/A/2/") === Some("Know number names and the count sequence.")
      page.subCategoryForPath("/Math/Content/K/CC/A/2") === Some("Know number names and the count sequence.")
    }

    "parse Grade3 Geometry" in {

      val path = "/com/corespring/models/corestandards/Grade3_Geometry.html" 
      val page = pageFromPath(path)
      page.subCategories.length === 1 
      
      val section = page.subCategories(0)
      section.name === Some("Reason with shapes and their attributes.")
      section.standards.length === 2
    }

    "Grade 3 Geometry - return subcategories" in {
      val path = "/com/corespring/models/corestandards/Grade3_Geometry.html" 
      val page = pageFromPath(path)
      page.subCategoryForPath("/Math/Content/3/G/A/1") === Some("Reason with shapes and their attributes.")
      page.subCategoryForPath("/Math/Content/3/G/A/2") === Some("Reason with shapes and their attributes.")
    }
    
    "parse Grade5 Numbers and Fractions" in {
      val path = "/com/corespring/models/corestandards/Grade5_Numbers_and_Fractions.html" 
      val page = pageFromPath(path)
      page.subCategories.length === 2 
      
      val section = page.subCategories(1)
      section.name === Some("Apply and extend previous understandings of multiplication and division.")
      section.standards.length === 12
    }

    "parse speaking and listening - grade 7" in {
      val path = "/com/corespring/models/corestandards/Grade7_Speaking_and_Listening.html" 
      val page = pageFromPath(path, CategoryPages.ELARegex)
      page.subCategories.length === 2 
      
      val section = page.subCategories(1)
      section.name === Some("Presentation of Knowledge and Ideas")
      section.standards.length === 3 
    }

    "get subCategory for standard" in {
      val path = "/com/corespring/models/corestandards/Grade7_Speaking_and_Listening.html" 
      val page = pageFromPath(path, CategoryPages.ELARegex)
      page.subCategoryForPath("/ELA-Literacy/SL/7/6/") === Some("Presentation of Knowledge and Ideas")
      page.subCategoryForPath("/ELA-Literacy/SL/7/1/a/") === Some("Comprehension and Collaboration")
      page.subCategoryForPath("/ELA-Literacy/sdfasdfad") === None
    }/**/

    "parse HSA_SRT" in {
      val path = "/com/corespring/models/corestandards/HSA_SRT.html" 
      val page = pageFromPath(path)
      page.subCategories.length === 4
      page.subCategories(3).standards.length === 3
    }

    "parse Grade 5 Speaking and Listening" in {
      val path = "/com/corespring/models/corestandards/Grade5_Speaking_and_Listening.html" 
      val page = pageFromPath(path, CategoryPages.ELARegex)
      println(".....")
      println(page)
      page.subCategories.length === 2 
      page.subCategories(0).standards.length === 7

      page.subCategoryForPath("/ELA-Literacy/SL/5/1/d/") === Some("Comprehension and Collaboration")

    }

    "parse ELA CCRA R" in {
      val path = "/com/corespring/models/corestandards/ELA_CCRA_R.html" 
      val page = pageFromPath(path, CategoryPages.ELARegex)
      page.subCategories.length === 4
      page.subCategories(2).name === Some("Integration of Knowledge and Ideas")
      page.subCategories(2).standards.length === 3
      page.subCategories(2).standards(2).href === "/ELA-Literacy/CCRA/R/9/"

    }
    
  }
}
