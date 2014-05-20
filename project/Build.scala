import sbt._
import Keys._

object QuerulousProject extends Build {

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.bostontechnologies",
    scalaVersion := "2.10.3",
    version := "1.0.6",
    publishTo := Some(Resolver.ssh("spix-ssh-repo", "mothership.spixdiscovery.com", "/home/ubuntu/production/repos") as("ubuntu", new java.io.File(Path.userHome + "/.ssh/busk-stage.pem")) withPermissions("0644") )
    // credentials += Credentials("Artifactory Realm", "maven.bostontechnologies.com", "artifactory", "Bt2@rTif@ct0ry!")
  )

  val dbcp      = "commons-dbcp" % "commons-dbcp"         % "1.4"
  val mysqljdbc = "mysql"        % "mysql-connector-java" % "5.1.18"
  val pool      = "commons-pool" % "commons-pool"         % "1.5.4"
  val utilCore  = "com.twitter" %% "util-core"            % "6.1.0"
  val scalaz    = "org.scalaz"  %% "scalaz-core"          % "6.0.4"

  val utilEval   = "com.twitter"            %% "util-eval"          % "6.1.0"	  % "test"
  val hamcrest   = "org.hamcrest"            % "hamcrest-all"       % "1.1"       % "test"
  val specs      = "org.scala-tools.testing" % "specs_2.8.0"        % "1.6.5"     % "test"
  val objenesis  = "org.objenesis"           % "objenesis"          % "1.1"       % "test"
  val jmock      = "org.jmock"               % "jmock"              % "2.4.0"     % "test"
  val cglib      = "cglib"                   % "cglib"              % "2.2"       % "test"
  val asm        = "asm"                     % "asm"                % "1.5.3"     % "test"
  val dbcpTests  = "commons-dbcp"            % "commons-dbcp-tests" % "1.4"       % "test"

  val dependencies = Seq(dbcp, mysqljdbc, pool, utilCore, utilEval, hamcrest, specs, objenesis, jmock,
	cglib, asm, scalaz)

  val twitterRepo = "Twitter Maven Repo" at "http://maven.twttr.com/"

  val typesafeRepo = "Typesafe repo" at "http://repo.typesafe.com/typesafe/releases/"

  val customResolvers = Seq(twitterRepo, typesafeRepo)

  val root = Project(
    "querulous",
    file("querulous-core"),
    settings = buildSettings ++ Seq(libraryDependencies ++= dependencies) ++ Seq(resolvers ++= customResolvers),
    configurations = Configurations.default
  )
}
