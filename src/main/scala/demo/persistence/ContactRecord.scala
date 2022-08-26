package demo.persistence

import demo.domain.Contact
import org.json4s.{Extraction, Formats, JValue}

import java.util.UUID

case class ContactRecord(
  id: String,
  data: JValue
)

object ContactRecord {

  def apply(contact: Contact): ContactRecord =
    apply(
      id = UUID.randomUUID.toString,
      data = toJValue(contact)
    )

  def toJValue(o: AnyRef): JValue = {
    implicit val formats: Formats = org.json4s.DefaultFormats
    Extraction.decompose(o)
  }

  // a mapper is required when we have a companion object,
  // even if it does not have any `apply` methods.
  // otherwise our ContactTable instance will have issues with
  // instantiation.
  def mapperTo(
    id: String,
    data: JValue
  ): ContactRecord = apply(id, data)

}
