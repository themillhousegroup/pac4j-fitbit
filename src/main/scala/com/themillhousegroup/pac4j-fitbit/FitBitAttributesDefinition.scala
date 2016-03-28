package com.themillhousegroup.pac4jfitbit

import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuthAttributesDefinition
import org.pac4j.oauth.profile.JsonHelper
import org.pac4j.oauth.profile.JsonObject
import com.fasterxml.jackson.databind.JsonNode

object FitBitAttributesDefinition extends OAuthAttributesDefinition {

  val ID = "encodedId"
  val FULL_NAME = "fullName"
  val DISPLAY_NAME = "displayName"
  val NICKNAME = "nickname"

  val CITY = "city"
  val STATE = "state"
  val COUNTRY = "country"
  val GENDER = "gender"

  val AVATAR_150 = "avatar150"

  addAttribute(FULL_NAME, Converters.stringConverter)
  addAttribute(DISPLAY_NAME, Converters.stringConverter)
  addAttribute(AVATAR_150, Converters.stringConverter)
}

