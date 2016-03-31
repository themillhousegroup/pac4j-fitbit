package com.themillhousegroup.pac4jfitbit

import org.pac4j.oauth.client._
import org.pac4j.oauth.credentials.OAuthCredentials
import org.pac4j.core.client.BaseClient
import org.scribe.model._
import org.pac4j.core.context.WebContext
import org.scribe.oauth.ProxyOAuth20ServiceImpl
import org.scribe.builder.api.FitBitApi
import org.scribe.services.Base64Encoder

/**
 * Get the key and secret values by registering your app at https://dev.fitbit.com/apps/new
 */
class FitBitClient(fitbitOauthClientKey: String, clientSecret: String, scopes: Set[FitBitScope] = FitBitScopes.profileOnly) extends BaseOAuth20Client[FitBitProfile] {

  protected val scope: String = scopes.map(_.name).mkString(" ")
  setKey(fitbitOauthClientKey)
  setSecret(clientSecret)
  setTokenAsHeader(true)

  private def basicAuthHeader(clientId: String, clientSecret: String): String = {
    val src = s"$clientId:$clientSecret"
    Base64Encoder.getInstance().encode(src.getBytes("UTF-8"))
  }

  protected override def internalInit(): Unit = {
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
    ) {

      /**
       * As documented at:
       * https://dev.fitbit.com/docs/oauth2/#access-token-request
       * we need to include an Authorization header along with the usual fields
       */
      override def getAccessToken(requestToken: Token, verifier: Verifier): Token = {

        val request: OAuthRequest =
          new ProxyOAuthRequest(this.api.getAccessTokenVerb, this.api.getAccessTokenEndpoint, this.connectTimeout, this.readTimeout, this.proxyHost, this.proxyPort)

        request.addBodyParameter(OAuthConstants.CODE, verifier.getValue)
        request.addBodyParameter("grant_type", "authorization_code")
        request.addBodyParameter(OAuthConstants.CLIENT_ID, this.config.getApiKey)
        request.addBodyParameter(OAuthConstants.REDIRECT_URI, this.config.getCallback)

        // request.addBodyParameter(OAuthConstants.CLIENT_SECRET, this.config.getApiSecret)

        request.addHeader("Authorization", "Basic " + basicAuthHeader(this.config.getApiKey, this.config.getApiSecret))

        val response: Response = request.send

        this.api.getAccessTokenExtractor.extract(response.getBody)
      }
    }
  }

  protected def newClient(): BaseClient[OAuthCredentials, FitBitProfile] = {
    new FitBitClient(key, secret)
  }

  protected def requiresStateParameter(): Boolean = false

  protected def getProfileUrl(accessToken: Token): String = s"https://api.fitbit.com/1/user/-/profile.json"

  protected def hasBeenCancelled(context: WebContext): Boolean = false

  protected def extractUserProfile(body: String): FitBitProfile = {
    FitBitProfileBuilder.createFromString(body)
  }

  private[pac4jfitbit] def getService = service
  private[pac4jfitbit] def getScope = scope
}

object FitBitProfileBuilder {
  def createFromString(body: String): FitBitProfile = {
    import org.pac4j.oauth.profile.JsonHelper
    import scala.collection.JavaConverters._

    val profile = new FitBitProfile()
    val maybeJson = Option(JsonHelper.getFirstNode(body))
    maybeJson.flatMap { json =>
      Option(json.get("user")).map { userJson =>
        profile.setId(JsonHelper.get(userJson, FitBitAttributesDefinition.ID))

        FitBitAttributesDefinition.getAllAttributes.asScala.foreach { attribute =>
          profile.addAttribute(attribute, JsonHelper.get(userJson, attribute))
        }
      }
    }

    profile

  }
}
