package demo.domain

case class Contact(
  id: Option[String] = None,
  name: String,
  age: Int,
  phones: List[Phone]
)
