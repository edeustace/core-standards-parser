Creates a json file from the xml at http://corestandards.org.

    http://www.corestandards.org/Math.xml
    http://www.corestandards.org/ELA-Literacy.xml

This script downloads this xml and for each standards updates our Standard model.


# Run

    java -jar ccss-importer-uber.jar db_uri collection math_xml ela_xml



# Developing

* Install sbt
* To build a fat jar: `sbt assembly`
