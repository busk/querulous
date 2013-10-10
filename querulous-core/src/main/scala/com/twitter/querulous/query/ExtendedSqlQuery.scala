package com.twitter.querulous.query

import java.sql.{ Connection, ResultSet }

class ExtendedSqlQueryFactory(fetchSize: Int) extends QueryFactory {
  def apply(connection: Connection, queryClass: QueryClass, query: String, params: Any*) = {
    new ExtendedSqlQuery(connection, query, fetchSize, params: _*)
  }
}
class ExtendedSqlQuery(connection: Connection, query: String, fetchSize: Int, params: Any*) extends SqlQuery(connection, query, params:_*) {
  override def select[A](f: ResultSet => A): Seq[A] = {
    if (statementInstance.isEmpty) {
      val stm = super.statement
      stm.setFetchSize(fetchSize)
    }
    super.select(f)
  }
}