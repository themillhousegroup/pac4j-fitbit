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

    "Support providing a custom callback URL" in {
      val uac = new FitBitClient("key", "secret", "/custom-callback-url")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right

      uac must not beNull

      val svc = uac.getService.asInstanceOf[ProxyAuth20WithHeadersServiceImpl]

      val cfg = svc.getConfig

      cfg must not beNull

      cfg.getCallback must beEqualTo("http://callbackUrl/custom-callback-url")
    }

    "Providing a reasonable callback URL by default" in {
      val uac = new FitBitClient("key", "secret")
      uac.setCallbackUrl("http://callbackUrl")
      uac.init // will throw if things are not right

      uac must not beNull

      val svc = uac.getService.asInstanceOf[ProxyAuth20WithHeadersServiceImpl]

      val cfg = svc.getConfig

      cfg must not beNull

      cfg.getCallback must beEqualTo("http://callbackUrl/FitBitClient/callback")
    }
  }
}
