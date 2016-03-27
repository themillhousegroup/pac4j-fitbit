package com.themillhousegroup.pac4junderarmour

import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.core.profile._

/**
 * Example JSON from GET xxxx:
 */
class FitBitProfile extends OAuth20Profile {

  val UNDERARMOUR_BASE_URL = "https://api.ua.com/v7.1"
  val UNDERARMOUR_SELF_PROFILE_URL = s"${UNDERARMOUR_BASE_URL}/user/self/"

  override protected val getAttributesDefinition: AttributesDefinition = UnderArmourAttributesDefinition

  private def getString(name: String): String = {
    getAttribute(name).asInstanceOf[String]
  }

  private def get[T](name: String): T = {
    getAttribute(name).asInstanceOf[T]
  }

  override def getFirstName: String = {
    getString(UnderArmourAttributesDefinition.FIRST_NAME)
  }

  override def getFamilyName: String = {
    getString(UnderArmourAttributesDefinition.LAST_NAME)
  }

  override def getDisplayName: String = {
    getString(UnderArmourAttributesDefinition.DISPLAY_NAME)
  }

  override def getEmail: String = {
    getString(UnderArmourAttributesDefinition.EMAIL)
  }

  override def getPictureUrl: String = s"${UNDERARMOUR_BASE_URL}/user_profile_photo/${getId}"

  override def getProfileUrl: String = s"${UNDERARMOUR_BASE_URL}/user/${getId}"

  override def getGender: Gender = {
    val gender = getString(UnderArmourAttributesDefinition.GENDER)
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

  def getFullLocation: UnderArmourLocation = {
    get(UnderArmourAttributesDefinition.LOCATION)
  }
}
