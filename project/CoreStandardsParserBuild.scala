import sbt._
import sbt.Keys._

object CoreStandardsParserBuild extends Build {

  lazy val ccssimporter = Project(
    id = "core-standards-parser",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "core-standards-parser",
      organization := "com.ee",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.2"
      // add other settings here
    )
  )
}
