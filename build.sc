import mill._
import mill.scalalib._
import mill.scalalib.publish._

trait CommonModule extends SbtModule with PublishModule {
  def scalaVersion = "2.12.8"
  def publishVersion = "0.6.7-SNAPSHOT"

  def pomSettings = PomSettings(
    description = "Google Cloud Pub/Sub stream-based client built on top of cats-effect, fs2 and http4s.",
    organization = "com.permutive",
    url = "https://github.com/permutive/fs2-google-pubsub",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("permutive", "fs2-google-pubsub"),
    developers = Seq(
      Developer("cremboc", "Paulius Imbrasas", "https://github.com/cremboc")
    )
  )

  def commonDependencies = Agg(
    ivy"org.typelevel::cats-core:1.5.0",
    ivy"org.typelevel::cats-effect:1.1.0",
    ivy"co.fs2::fs2-core:1.0.2",
  )

  def httpDependencies = Agg(
    ivy"org.http4s::http4s-dsl:0.20.0-M5",
    ivy"org.http4s::http4s-client:0.20.0-M5",
    ivy"com.auth0:java-jwt:3.5.0",
    ivy"io.chrisdavenport::log4cats-slf4j:0.2.0",
    ivy"com.github.plokhotnyuk.jsoniter-scala::jsoniter-scala-core:0.39.0",
  )

  def grpcDependencies = Agg(
    ivy"com.google.cloud:google-cloud-pubsub:1.60.0",
  )

  def httpCompileDependencies = Agg(
    ivy"com.github.plokhotnyuk.jsoniter-scala::jsoniter-scala-macros:0.39.0",
  )

  override def scalacOptions = List(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-unused:imports",             // Warn if an import is unused.
    "-Ywarn-unused:patvars"              // Warn if a variable bound in a pattern is unused.
  )
}

object `fs2-google-pubsub` extends CommonModule {
  override def ivyDeps = commonDependencies
}

object `fs2-google-pubsub-http` extends CommonModule {
  override def moduleDeps = List(`fs2-google-pubsub`)
  override def ivyDeps = commonDependencies ++ httpDependencies
  override def compileIvyDeps = httpCompileDependencies
}

object `fs2-google-pubsub-grpc` extends CommonModule {
  override def moduleDeps = List(`fs2-google-pubsub`)
  override def ivyDeps = commonDependencies ++ grpcDependencies
}