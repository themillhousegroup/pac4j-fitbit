package com.themillhousegroup.pac4jfitbit

import org.pac4j.core.exception.HttpCommunicationException
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

case class FitBitScope(name: String)

object FitBitScopes {
  val all = Set(
    "activity",
    "heartrate",
    "location",
    "nutrition",
    "profile",
    "settings",
    "sleep",
    "social",
    "weight"
  ).map(FitBitScope)

  val profileOnly = Set(FitBitScope("profile"))
}
/**
 * Get the key and secret values by registering your app at https://dev.fitbit.com/apps/new
 */
class FitBitClient(fitbitOauthClientKey: String, clientSecret: String, scopes: Set[FitBitScope] = FitBitScopes.profileOnly) extends BaseOAuth20Client[FitBitProfile] {

  protected val scope: String = scopes.mkString(" ")
  setKey(fitbitOauthClientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  protected override def internalInit():Unit = {
			super.internalInit()
			service = new ProxyOAuth20ServiceImpl(
				new FitBitApi(), 
				new OAuthConfig(
					key, secret, callbackUrl, SignatureType.Header, scope, null
				), 
				connectTimeout, 
				readTimeout, 
				proxyHost, 
				proxyPort, 
				false, 
				false
			)
  }

  protected def newClient(): BaseClient[OAuthCredentials, FitBitProfile] = {
    new FitBitClient(key, secret)
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = s"https://api.fitbit.com/1/user/${accessToken}/profile.json"

	protected override def sendRequestForData(accessToken:Token, dataUrl:String):String = {
        logger.debug(s"Overridden sendRequestForData: accessToken : ${accessToken}, ${dataUrl}")
        val t0:Long = System.currentTimeMillis()
        val request = createProxyRequest(dataUrl)

        this.service.signRequest(accessToken, request)
        // Let the client to decide if the token should be in header
        if (this.isTokenAsHeader()) {
						println("adding this: 'Authorization, Bearer"  + accessToken.getToken() + "'")
            request.addHeader("Authorization", "Bearer " + accessToken.getToken())
        }
        val response = request.send()
        val code = response.getCode()
        val body = response.getBody()
        val t1 = System.currentTimeMillis()
        logger.debug("Request took : " + (t1 - t0) + " ms for : " + dataUrl)
        logger.debug("response code : {} / response body : {}", code, body)
        if (code != 200) {
            logger.error("Failed to get data, code : " + code + " / body : " + body)
            throw new HttpCommunicationException(code, body)
        }
        body
    }


  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): FitBitProfile = {
		println(s"Profile build requested from $body")
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
