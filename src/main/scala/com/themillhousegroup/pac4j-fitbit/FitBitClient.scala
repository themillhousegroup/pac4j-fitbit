package com.themillhousegroup.pac4jfitbit

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model.Token
import org.pac4j.core.context.WebContext
import org.scribe.oauth.{ ProxyAuth20WithHeadersServiceImpl, ProxyOAuth20ServiceImpl }
import org.scribe.model.ProxyOAuthRequest
import org.scribe.model.OAuthConfig
import org.scribe.model.SignatureType
import org.scribe.builder.api.{ DefaultApi20, FitBitApi }
import java.net.URL

/**
 * Get the key and secret values by registering your app at https://dev.fitbit.com/apps/new
 */
class FitBitClient(fitbitOauthClientKey: String, clientSecret: String, clientCallbackUrl: String = "/FitBitClient/callback") extends BaseOAuth20Client[FitBitProfile] {

  /**
   * comma delimited string of ‘view_private’ and/or ‘write’, leave blank for read-only permissions. FIXME
   */
  protected val scope: String = null
  setKey(fitbitOauthClientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  protected def newClient(): BaseClient[OAuthCredentials, FitBitProfile] = {
    new FitBitClient(key, secret)
  }

  protected override def internalInit(): Unit = {
    super.internalInit()

    val u = new URL(callbackUrl)
    val modifiedCallbackUrl = s"${u.getProtocol}://${u.getAuthority}${clientCallbackUrl}"
    service =
      new ProxyAuth20WithHeadersServiceImpl(
        new FitBitApi(),
        new OAuthConfig(key, secret, modifiedCallbackUrl, SignatureType.Header, scope, null),
        connectTimeout,
        readTimeout,
        proxyHost,
        proxyPort,
        false,
        true) {
        override def addHeaders(requestToken: Token, api: DefaultApi20, config: OAuthConfig): List[(String, String)] = {
          // As per:
          // https://developer.underarmour.com/docs/v71_OAuth_2_Intro
          // we need to add "Api-Key" with the client ID
          List("Api-Key" -> config.getApiKey)
        }
      }
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = s"https://api.fitbit.com/1/user/${accessToken}/profile.json"

  // All UA requests have to have the "Api-Key" HTTP header...
  protected override def createProxyRequest(url: String): ProxyOAuthRequest = {
    val r = super.createProxyRequest(url)
    r.addHeader("Api-Key", fitbitOauthClientKey)
    r
  }

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): FitBitProfile = {
    FitBitProfileBuilder.createFromString(body)
  }

  private[pac4jfitbit] def getService = service
}

object FitBitProfileBuilder {
  def createFromString(body: String): FitBitProfile = {
    import org.pac4j.oauth.profile.JsonHelper
    import scala.collection.JavaConverters._

    val profile = new FitBitProfile()
    val json = JsonHelper.getFirstNode(body)
    if (json != null) {
      profile.setId(JsonHelper.get(json, FitBitAttributesDefinition.ID))

      FitBitAttributesDefinition.getAllAttributes.asScala.foreach { attribute =>
        profile.addAttribute(attribute, JsonHelper.get(json, attribute))
      }
    }
    profile

  }
}
