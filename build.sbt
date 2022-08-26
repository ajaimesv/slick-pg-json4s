ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "json4s",
    libraryDependencies ++= Seq(
        "org.json4s" %% "json4s-jackson" % "4.0.5",
        "org.postgresql" % "postgresql" % "42.4.2",
        "com.typesafe.slick" %% "slick" % "3.3.3",
        "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
        "com.github.tminglei" %% "slick-pg" % "0.20.4",
        "com.github.tminglei" %% "slick-pg_json4s" % "0.20.4"
    )
)
