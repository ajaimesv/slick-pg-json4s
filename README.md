# postgres, slick, json4s, slick-pg, jackson

This demo code shows how to use slick and slick-pg to create and read `jsonb` fields from postgres.

There are some examples on the web that use Play's JSON library. This code uses json4s with jackson as an alternative to Play's library.

The code creates a json document called `ContactRecord`, from a `Contact` and a list of `Phone` instances. It then stores it in the database in a `jsonb` field.

## Additional notes

Some additional helpful examples.

### Get a JSON string from an object

```scala
val contact: Contact = Contact(...)

// Need to get a JValue because "Contact" is not a native scala type
import org.json4s.{Extraction, Formats, JValue}

implicit val formats: Formats = org.json4s.DefaultFormats
val result = Extraction.decompose(contact)

// Convert it to string
import org.json4s.JsonDSL._
import org.json4s.JsonDSL.WithBigDecimal._
import org.json4s.jackson.JsonMethods.compact
import org.json4s.jackson.JsonMethods.render

val s = compact(render(result))

println(s"json: $s")
```

Output:

```json
json: {"name":"John","age":20,"phones":[{"phoneType":"home","number":"12345"},{"phoneType":"work","number":"54321"}]}
```

### Custom serializers

The following code serializes/deserializes instances of JodaMoney.

```scala
package demo

import org.joda.money.{CurrencyUnit, Money}
import org.json4s.CustomSerializer
import org.json4s.JsonAST._

class JodaMoneySerializer extends CustomSerializer[Money](format => (
  {
    // from json to instance
    case JObject(JField("code", JString(code)) ::
                 JField("amount", JDouble(amount)) ::
                 Nil) =>
        Money.of(CurrencyUnit.of (code), BigDecimal(amount).bigDecimal)
  },
  {
    // from instance to json
    case m: Money =>
      JObject(JField("code", JString(m.getCurrencyUnit.getCode)) ::
              JField("amount", JDecimal(m.getAmount)) ::
              Nil)
  }
))
```

Add the serializer to the list of formatters where you handle json, for example:

```scala
implicit val formats: Formats = org.json4s.DefaultFormats + new JodaMoneySerializer
```


## References

* Ciocîrlan, Daniel. Slick Tutorial. https://blog.rockthejvm.com/slick/ - Tutorial using Play's json library.
* json4s. https://github.com/json4s/json4s
* slick-pg. https://github.com/tminglei/slick-pg
