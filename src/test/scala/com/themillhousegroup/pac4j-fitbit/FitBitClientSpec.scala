package com.themillhousegroup.pac4jfitbit

import org.specs2.mutable.Specification

class FitBitClientSpec extends Specification {
  "FitBit client" should {
    "be instantiable" in {
      val fbc = new FitBitClient("key", "secret")
      fbc must not beNull
    }

    "be initializable by pac4j calling init()" in {
      val fbc = new FitBitClient("key", "secret")
      fbc.setCallbackUrl("http://callbackUrl")
      fbc.init // will throw if things are not right
      fbc must not beNull
    }

    "always set a default scope of 'profile'" in {
      val fbc = new FitBitClient("key", "secret")

      fbc.getScope must beEqualTo("profile")
    }

    "Support providing custom scopes - empty scope" in {
      val fbc = new FitBitClient("key", "secret", Set())
      fbc.getScope must beEqualTo("")
    }

    "Support providing custom scopes - Set of valid scopes" in {
      import FitBitScopes._
      val fbc = new FitBitClient("key", "secret", Set(profile, location, activity, weight))
      fbc.getScope must beEqualTo("profile location activity weight")
    }
  }
}
