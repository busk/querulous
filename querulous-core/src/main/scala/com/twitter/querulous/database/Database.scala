package com.twitter.querulous.database

import java.sql.Connection
import com.twitter.util.Duration

object Database {
  //TODO: allow passing this via setup configuration
  private[querulous] var driverName: String = "jdbc:mysql"

  val defaultUrlOptions = Map(
    "useUnicode" -> "true",
    "characterEncoding" -> "UTF-8",
    "connectTimeout" -> "100"
  )
}

trait DatabaseFactory {
  def apply(dbhosts: List[String], dbname: String, username: String, password: String, urlOptions: Map[String, String], driverName: String): Database

  def apply(dbhosts: List[String], dbname: String, username: String, password: String, urlOptions: Map[String, String]): Database =
    apply(dbhosts, dbname, username, password, urlOptions, Database.driverName)

  def apply(dbhosts: List[String], dbname: String, username: String, password: String): Database =
    apply(dbhosts, dbname, username, password, Map.empty, Database.driverName)

  def apply(dbhosts: List[String], username: String, password: String): Database =
    apply(dbhosts, null, username, password, Map.empty, Database.driverName)

  def apply(driverName: String, dbhosts: List[String], username: String, password: String): Database =
    apply(dbhosts, null, username, password, Map.empty, driverName)
}

trait DatabaseProxy extends Database {
  def database: Database

  def driverName      = database.driverName
  def hosts           = database.hosts
  def name            = database.name
  def username        = database.username
  def extraUrlOptions = database.extraUrlOptions
  def openTimeout     = database.openTimeout
}

trait Database {
  def driverName: String
  def hosts: List[String]
  def name: String
  def username: String
  def extraUrlOptions: Map[String, String]
  def openTimeout: Duration

  def urlOptions = Database.defaultUrlOptions ++ extraUrlOptions

  def open(): Connection

  def close(connection: Connection)

  def withConnection[A](f: Connection => A): A = {
    val connection = open()
    try {
      f(connection)
    } finally {
      close(connection)
    }
  }

  protected def url(hosts: List[String], name: String, urlOptions: Map[String, String], driverName: String) = {
    val nameSegment    = if (name == null) "" else ("/" + name)
    val urlOptsSegment = urlOptions.map(Function.tupled((k, v) => k+"="+v )).mkString("&")

    driverName + "://" + hosts.mkString(",") + nameSegment + "?" + urlOptsSegment
  }
}
