lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion       := "2.12.6",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  libraryDependencies ++= Seq(
    "org.graalvm" % "graal-sdk" % "1.0.0-rc1"
  ),
  resolvers += Resolver.sonatypeRepo("releases")
)

lazy val `graalvm-impact-on-scala` = project.in(file("."))
  .settings(moduleName := "graalvm-impact-on-scala")
  .settings(baseSettings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "graalvm-impact-on-scala-core")
  .settings(baseSettings: _*)


lazy val slides = project
  .settings(moduleName := "graalvm-impact-on-scala-slides")
  .settings(baseSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "../docs",
    watchSources ++= (tutSourceDirectory.value ** "*.html").get
  ).dependsOn(core)
  .enablePlugins(TutPlugin)
