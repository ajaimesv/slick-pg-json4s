package demo

import demo.domain.{Contact, ContactDao, Phone}
import demo.persistence.{ContactDaoImpl, Datasource}

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  val contact1 = Contact(
    name = "John",
    age = 20,
    phones = List(
      Phone(
        phoneType = "home",
        number = "12345"
      ),
      Phone(
        phoneType = "work",
        number = "54321"
      )
    )
  )

  val ds: Datasource = new Datasource()
  val dao: ContactDao = new ContactDaoImpl(ds)
  dao.create(contact1).map { id =>
    dao.read(id).foreach(c => println(s"read: $c"))
    dao.readByName("John").foreach(c => println(s"readByName: $c"))
  }

  Thread.sleep(3000)
}
