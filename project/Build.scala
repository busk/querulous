import sbt._
import Keys._

object QuerulousProject extends Build {

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.bostontechnologies",
    scalaVersion := "2.9.1",   
    version := "1.0.4",
    publishTo := Some("BT maven artifactory" at "http://maven.bostontechnologies.com/artifactory/libs-releases-local"),
    credentials += Credentials("Artifactory Realm", "maven.bostontechnologies.com", "artifactory", "Bt2@rTif@ct0ry!")
  )

  val dbcp      = "commons-dbcp" % "commons-dbcp"         % "1.4"
  val mysqljdbc = "mysql"        % "mysql-connector-java" % "5.1.18"
  val pool      = "commons-pool" % "commons-pool"         % "1.5.4"
  val utilCore  = "com.twitter" %% "util-core"            % "4.0.1"
  val scalaz    = "org.scalaz"  %% "scalaz-core"          % "6.0.4"
  
  val utilEval   = "com.twitter"            %% "util-eval"          % "4.0.1"	  % "test"  
  val hamcrest   = "org.hamcrest"            % "hamcrest-all"       % "1.1"       % "test"
  val specs      = "org.scala-tools.testing" % "specs_2.8.0"        % "1.6.5"     % "test"
  val objenesis  = "org.objenesis"           % "objenesis"          % "1.1"       % "test"
  val jmock      = "org.jmock"               % "jmock"              % "2.4.0"     % "test"
  val cglib      = "cglib"                   % "cglib"              % "2.2"       % "test"
  val asm        = "asm"                     % "asm"                % "1.5.3"     % "test"
  val dbcpTests  = "commons-dbcp"            % "commons-dbcp-tests" % "1.4"       % "test"

  val dependencies = Seq(dbcp, mysqljdbc, pool, utilCore, utilEval, hamcrest, specs, objenesis, jmock,
	cglib, asm, dbcpTests, scalaz)

  val twitterRepo = "Twitter Maven Repo" at "http://maven.twttr.com/"

  val customResolvers = Seq(twitterRepo)

  val root = Project(
    "querulous", 
    file("querulous-core"),
    settings = buildSettings ++ Seq(libraryDependencies ++= dependencies) ++ Seq(resolvers ++= customResolvers),
    configurations = Configurations.default
  )  
}
