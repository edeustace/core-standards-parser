import AssemblyKeys._ 

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.12.2" % "test",
  "nu.validator.htmlparser" % "htmlparser" % "1.4",
  "com.codahale" % "jerkson_2.9.1" % "0.5.0"
  )

resolvers ++= Seq(
  "Sbt plugins" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/",
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "mvn repo" at "http://repo1.maven.org/maven2/",
   "codehale repo" at "http://repo.codahale.com"
)


assemblySettings
