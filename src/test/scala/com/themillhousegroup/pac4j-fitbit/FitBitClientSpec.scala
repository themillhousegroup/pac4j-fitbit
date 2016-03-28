package com.themillhousegroup.pac4jfitbit

import org.specs2.mutable.Specification
import org.scribe.oauth._

class FitBitClientSpec extends Specification {
  "FitBit client" should {
    "be instantiable" in {
      val uac = new FitBitClient("key", "secret")
      uac must not beNull
    }

    "be initializable by pac4j calling init()" in {
      val uac = new FitBitClient("key", "secret")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right
      uac must not beNull
    }

    "Support providing custom scopes" in {
      val uac = new FitBitClient("key", "secret", Set())
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right

      uac must not beNull
    }
  }
}
