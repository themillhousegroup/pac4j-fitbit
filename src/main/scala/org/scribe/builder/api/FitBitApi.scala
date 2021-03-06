package org.scribe.builder.api

import org.scribe.extractors.AccessTokenExtractor
import org.scribe.extractors.StravaJsonExtractor
import org.scribe.model.OAuthConfig
import org.scribe.model.Verb
import org.scribe.utils.OAuthEncoder
import org.scribe.utils.Preconditions

object FitBitApi {
  /**
   * FitBit authorization URL:
   * https://dev.fitbit.com/docs/oauth2/#authorization-page
   */
  private val AUTHORIZE_URL = "https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s"

  private val ACCESS_TOKEN_URL = "https://api.fitbit.com/oauth2/token"

  /**
   * The Json extractor used by Strava is actually applicable here too
   */
  private val ACCESS_TOKEN_EXTRACTOR: AccessTokenExtractor = new StravaJsonExtractor()
}

/**
 * This class represents the OAuth API implementation for FitBit.
 */
class FitBitApi extends DefaultApi20 {
  import FitBitApi._

  override val getAccessTokenEndpoint: String = ACCESS_TOKEN_URL

  override val getAccessTokenVerb: Verb = Verb.POST

  override val getAccessTokenExtractor: AccessTokenExtractor = ACCESS_TOKEN_EXTRACTOR

  override def getAuthorizationUrl(config: OAuthConfig): String = {
    Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid callback url.")

    String.format(AUTHORIZE_URL,
      config.getApiKey,
      OAuthEncoder.encode(config.getCallback),
      config.getScope
    )
  }
}
