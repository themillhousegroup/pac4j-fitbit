package com.themillhousegroup.pac4jfitbit

import org.specs2.mutable.Specification
import com.themillhousegroup.pac4jfitbit.test.ProfileFixtures._

class FitBitProfileSpec extends Specification {
  "FitBit profile builder" should {
    "be able to create a non-null FitBitProfile from a String" in {
      val p = FitBitProfileBuilder.createFromString(fullProfile)
      p must not beNull
    }

    "be able to populate the basic CommonProfile fields of a FitBitProfile from a String" in {
      val p = FitBitProfileBuilder.createFromString(basicProfile)

      p.getId must beEqualTo("3G75NZ")
      p.getDisplayName must beEqualTo("DisplayName")
      p.getEmail must beEqualTo("")
      p.getFamilyName must beEqualTo("Namesmith")
      p.getFirstName must beEqualTo("Fully")
      p.getGender must beEqualTo(org.pac4j.core.profile.Gender.FEMALE)
      p.getLocale must beEqualTo(new java.util.Locale("EN", "AU"))
      p.getLocation must beEqualTo("")
      p.getPictureUrl must beEqualTo("https://static0.fitbit.com/images/profile/defaultProfile_150_male.gif")
      p.getProfileUrl must beEqualTo("https://api.fitbit.com/1/user/3G75NZ/profile.json")
      p.getUsername must beEqualTo("3G75NZ")
    }

    "be able to populate the full CommonProfile fields of a FitBitProfile from a String" in {
      val p = FitBitProfileBuilder.createFromString(fullProfile)

      p.getId must beEqualTo("3G75NZ")
      p.getDisplayName must beEqualTo("DisplayName")
      p.getEmail must beEqualTo("")
      p.getFamilyName must beEqualTo("Namesmith")
      p.getFirstName must beEqualTo("Fully")
      p.getGender must beEqualTo(org.pac4j.core.profile.Gender.FEMALE)
      p.getLocale must beEqualTo(new java.util.Locale("EN", "AU"))
      p.getLocation must beEqualTo("Melbourne AU")
      p.getPictureUrl must beEqualTo("https://static0.fitbit.com/images/profile/defaultProfile_150_male.gif")
      p.getProfileUrl must beEqualTo("https://api.fitbit.com/1/user/3G75NZ/profile.json")
      p.getUsername must beEqualTo("3G75NZ")
    }
  }
}
