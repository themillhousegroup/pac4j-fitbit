package com.themillhousegroup.pac4jfitbit

import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.core.profile._

/**
 * Example JSON from GET xxxx:
 */
class FitBitProfile extends OAuth20Profile {

  val UNDERARMOUR_BASE_URL = "https://api.ua.com/v7.1"
  val UNDERARMOUR_SELF_PROFILE_URL = s"${UNDERARMOUR_BASE_URL}/user/self/"

  override protected val getAttributesDefinition: AttributesDefinition = FitBitAttributesDefinition

  private def getString(name: String): String = {
    getAttribute(name).asInstanceOf[String]
  }

  private def get[T](name: String): T = {
    getAttribute(name).asInstanceOf[T]
  }

  override def getFirstName: String = {
    getString(FitBitAttributesDefinition.FIRST_NAME)
  }

  override def getFamilyName: String = {
    getString(FitBitAttributesDefinition.LAST_NAME)
  }

  override def getDisplayName: String = {
    getString(FitBitAttributesDefinition.DISPLAY_NAME)
  }

  override def getEmail: String = {
    getString(FitBitAttributesDefinition.EMAIL)
  }

  override def getPictureUrl: String = s"${UNDERARMOUR_BASE_URL}/user_profile_photo/${getId}"

  override def getProfileUrl: String = s"${UNDERARMOUR_BASE_URL}/user/${getId}"

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
    getFullLocation.locality
  }

  def getFullLocation: FitBitLocation = {
    get(FitBitAttributesDefinition.LOCATION)
  }
}
