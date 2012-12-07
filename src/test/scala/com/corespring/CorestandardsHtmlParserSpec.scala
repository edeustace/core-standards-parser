package com.corespring

import org.specs2.mutable.Specification


class CorestandardsHtmlParserSpec extends Specification{

  def assertParse(url:String,category:String,subCategory:String) = {

    val maybeSubCategory = if (subCategory.isEmpty) None else Some(subCategory)
    CoreStandardsHtmlParser.parse("http://www.corestandards.org" + url) ===
      (Some(category), maybeSubCategory)
  }

  "can parse" should {


    "/ELA-Literacy/SL/5/1/d" in {
     assertParse(
       "/ELA-Literacy/SL/5/1/d", 
       "Speaking & Listening", 
       "Comprehension and Collaboration" )

      assertParse(
       "/ELA-Literacy/SL/5/1/d", 
       "Speaking & Listening", 
       "Comprehension and Collaboration" )
    }


    "parse http://corestandards.org/Math/Content/K/CC/A/2/" in {
      
      CoreStandardsHtmlParser.parse("http://corestandards.org/Math/Content/K/CC/A/2/") ===
        (Some("Counting & Cardinality"),
        Some("Know number names and the count sequence."))
    }

   
    "parse A/2" in {
      assertParse("/Math/Content/K/CC/A/2",
        "Counting & Cardinality",
        "Know number names and the count sequence.")
    } 
 
    "parse HSA/SRT/D/10" in {

      assertParse("/Math/Content/HSG/SRT/D/10",
        "Similarity, Right Triangles, & Trigonometry",
        "Apply trigonometry to general triangles")
      true === true
    }
     
    "parse http://www.corestandards.org/Math/Content/K/CC/B/4/a" in {
      assertParse("/Math/Content/K/CC/B/4/a",
        "Counting & Cardinality",
        "Count to tell the number of objects.")
    }
    
   
 

    "parse A/1" in {
      assertParse("/Math/Content/K/CC/A/1",
        "Counting & Cardinality",
        "Know number names and the count sequence.")
    }

    "parse HSA" in {

      assertParse("/Math/Content/HSA/APR/A/1",
      "Arithmetic with Polynomials & Rational Expressions",
      "Perform arithmetic operations on polynomials.")
    }




    "/Math/Content/8/SP/A/4" in {
      assertParse("/Math/Content/8/SP/A/4",
        "Statistics & Probability",
        "Investigate patterns of association in bivariate data.")

    }

   
     /**/
    
    
  }
}