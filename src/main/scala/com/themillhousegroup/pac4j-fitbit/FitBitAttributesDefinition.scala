package com.themillhousegroup.pac4jfitbit

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition
import org.pac4j.oauth.profile.JsonHelper
import org.pac4j.oauth.profile.JsonObject
import com.fasterxml.jackson.databind.JsonNode

object FitBitAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "id"
  val RESOURCE_STATE = "resource_state"
  val FIRST_NAME = "first_name"
  val LAST_NAME = "last_name"
  val DISPLAY_NAME = "display_name"
  val EMAIL = "email"
  val USERNAME = "username"

  val LOCATION = "location"
  val LOCALITY = "locality"
  val REGION = "region"
  val COUNTRY = "country"
  val GENDER = "gender"

  val LINKS = "_links"

  addAttribute(FIRST_NAME, Converters.stringConverter)
  addAttribute(LAST_NAME, Converters.stringConverter)
  addAttribute(DISPLAY_NAME, Converters.stringConverter)
  addAttribute(EMAIL, Converters.stringConverter)
  addAttribute(USERNAME, Converters.stringConverter)

  addAttribute(LOCATION, FitBitConverters.locationConverter)
  addAttribute(GENDER, Converters.stringConverter)
}

class FitBitLink extends JsonObject {
  var href: String = ""
  var id: String = ""
  var name: String = ""
  override def buildFromJson(json: JsonNode): Unit = {
    this.href = JsonHelper.convert(Converters.stringConverter, json, "href").asInstanceOf[String]
    this.id = JsonHelper.convert(Converters.stringConverter, json, "id").asInstanceOf[String]
    this.name = JsonHelper.convert(Converters.stringConverter, json, "name").asInstanceOf[String]
  }
}

class FitBitLocation extends JsonObject {
  var country: String = ""
  var region: String = ""
  var locality: String = ""
  var address: String = ""

  override def buildFromJson(json: JsonNode): Unit = {
    this.country = JsonHelper.convert(Converters.stringConverter, json, "country").asInstanceOf[String]
    this.region = JsonHelper.convert(Converters.stringConverter, json, "region").asInstanceOf[String]
    this.locality = JsonHelper.convert(Converters.stringConverter, json, "locality").asInstanceOf[String]
    this.address = JsonHelper.convert(Converters.stringConverter, json, "address").asInstanceOf[String]
  }

  override def toString = s"$country | $region | $locality | $address"

  override def equals(that: Any): Boolean = {
    that match {
      case o: FitBitLocation => this.toString == o.toString
      case _ => false
    }
  }

  override def hashCode: Int = {
    this.toString.hashCode
  }
}

object FitBitConverters {
  import org.pac4j.oauth.profile.converter.JsonObjectConverter
  val locationConverter: JsonObjectConverter = new JsonObjectConverter(classOf[FitBitLocation])
  val linkConverter: JsonObjectConverter = new JsonObjectConverter(classOf[FitBitLink])
}
