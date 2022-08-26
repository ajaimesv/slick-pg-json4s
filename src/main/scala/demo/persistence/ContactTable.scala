package demo.persistence

import CustomPostgresProfile.api._
import org.json4s.JValue

class ContactTable(tag: Tag) extends Table[ContactRecord](tag, Some("myschema"), "contacts") {

  def id = column[String]("id", O.PrimaryKey)
  def data = column[JValue]("data")

  val _columns =  (id, data)

  override def * =
    (id, data) <> ((ContactRecord.mapperTo _).tupled, ContactRecord.unapply)

}

