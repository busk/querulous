import com.twitter.querulous.config.Connection

new Connection {
  val hostnames = Seq("localhost")
  val database = "db_test"
  val username = System.getenv("DB_USERNAME")   
  val password = System.getenv("DB_PASSWORD")
}
