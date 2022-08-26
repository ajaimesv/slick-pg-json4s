package demo.domain

import scala.concurrent.Future

trait ContactDao {

  def create(contact: Contact): Future[String]
  def read(id: String): Future[Option[Contact]]
  def readByName(name: String): Future[Seq[Contact]]

}
