import sbt._
import sbt.Keys._

object CoreStandardsParserBuild extends Build {

  lazy val ccssimporter = Project(
    id = "core-standards-parser",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "core-standards-parser",
      organization := "com.ee",
      version := "0.1",
      scalaVersion := "2.9.2",
      // add other settings here
      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
      publishMavenStyle := true,
      publishTo <<= version { (v: String) =>
        def isSnapshot = v.trim.endsWith("SNAPSHOT") 
        val finalPath = (if (isSnapshot) "/snapshots" else "/releases")
        Some(
          Resolver.sftp(
            "Ed Eustace",
            "edeustace.com", 
            "/home/edeustace/edeustace.com/public/repository" + finalPath ))

       }
    )
  )
}
