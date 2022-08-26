package demo.persistence

import com.github.tminglei.slickpg._
import org.json4s._

/*
 * slick-pg custom profile.
 */
trait CustomPostgresProfile extends ExPostgresProfile
  with PgJsonSupport with PgJson4sSupport with PgArraySupport with PgDate2Support {

  override def pgjson = "jsonb" // jsonb support

  // required for jackson
  // see: https://github.com/tminglei/slick-pg/issues/123
  type DOCType = JValue
  override val jsonMethods = org.json4s.jackson.JsonMethods

  // Add `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  override protected def computeCapabilities: Set[slick.basic.Capability] =
    super.computeCapabilities + slick.jdbc.JdbcCapabilities.insertOrUpdate

  override val api = CustomPGAPI

  object CustomPGAPI
    extends API
      with JsonImplicits
      with DateTimeImplicits
}

object CustomPostgresProfile extends CustomPostgresProfile
