package com.ee

object Runner extends App {

  val header =
    """
      | Common core standards parser
      | --------------------------------------------------
      |
    """.stripMargin


  val usage =
    """
      | Usage
      | -------------------------------------------------
      | args are required in this order:
      | corestandards_xml_uri_1, ..., corestandards_xml_uri_N 
      | 
      | Where the corestandards url points to an xml file on corestandards.org
      | eg:
      |
    """.stripMargin

  println(header)

  val MATH_URL = "http://corestandards.org/Math.xml"
  val ELA_URL = "http://corestandards.org/ELA-Literacy.xml"
  
  val urls : Seq[String] = if( args.length == 0){
    Seq(MATH_URL, ELA_URL)
  } else {
    args.toSeq
  }

  println( CoreStandardsParser.run( urls: _* ))


}