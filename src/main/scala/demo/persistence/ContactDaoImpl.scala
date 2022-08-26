package demo.persistence

import demo.domain.{Contact, ContactDao}
import demo.persistence.CustomPostgresProfile.api._
import org.json4s.DefaultFormats
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ContactDaoImpl(datasource: Datasource) extends ContactDao {

  val contacts = TableQuery[ContactTable]

  def create(contact: Contact): Future[String] = {
    val record: ContactRecord = ContactRecord(contact)
    datasource.db.run(
      contacts += record
    ).map(_ => record.id)
  }

  /*
   * Query by id.
   */
  def read(id: String): Future[Option[Contact]] = {
    // "formats" is required to "extract" into a class instance
    // see: https://stackoverflow.com/questions/32378429/extract-string-value-using-json4s
    implicit val formats = DefaultFormats

    datasource.db.run(
      contacts.filter(_.id === id).result
    ).map{ seq => // sequence of results
      seq.map { res => // result
        res.data.extract[Contact] // instantiate Contact from `data` json field
          .copy(id = Some(res.id)) // copy the id, because it's not part of our json
      }.headOption
    }
  }

  /*
   * Query using the json field.
   */
  def readByName(name: String): Future[Seq[Contact]] = {
    // "formats" is required to "extract" into a class instance
    // see: https://stackoverflow.com/questions/32378429/extract-string-value-using-json4s
    implicit val formats = DefaultFormats

    datasource.db.run(
      contacts.filter(_.data +>> "name" === name).result
    ).map{ seq => // sequence of results
      seq.map { res => // result
        res.data.extract[Contact] // instantiate Contact from `data` json field
          .copy(id = Some(res.id)) // copy the id, because it's not part of our json
      }
    }
  }

}
