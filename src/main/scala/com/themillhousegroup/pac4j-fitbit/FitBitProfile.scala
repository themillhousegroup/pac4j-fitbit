package com.themillhousegroup.pac4jfitbit

import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.core.profile._

class FitBitProfile extends OAuth20Profile {

  val FITBIT_BASE_URL = "https://api.fitbit.com/1"

  override protected val getAttributesDefinition: AttributesDefinition = FitBitAttributesDefinition

  private def getString(name: String): String = {
    getAttribute(name).asInstanceOf[String]
  }

  private def get[T](name: String): T = {
    getAttribute(name).asInstanceOf[T]
  }

  override def getFirstName: String = {
    getString(FitBitAttributesDefinition.FULL_NAME).split(" ")(0)
  }

  override def getFamilyName: String = {
    getString(FitBitAttributesDefinition.FULL_NAME).split(" ")(1)
  }

  override def getDisplayName: String = {
    getString(FitBitAttributesDefinition.DISPLAY_NAME)
  }

  override def getEmail: String = {
    ""
  }

  override def getPictureUrl: String = getString(FitBitAttributesDefinition.AVATAR_150)

  override def getProfileUrl: String = s"${FITBIT_BASE_URL}/user/${getId}/profile.json"

  override def getGender: Gender = {
    val gender = getString(FitBitAttributesDefinition.GENDER)
    if ("M".equals(gender)) {
      Gender.MALE
    } else if ("F".equals(gender)) {
      Gender.FEMALE
    } else {
      Gender.UNSPECIFIED
    }
  }

  override def getLocation: String = {
    getString(FitBitAttributesDefinition.CITY)
  }
}
