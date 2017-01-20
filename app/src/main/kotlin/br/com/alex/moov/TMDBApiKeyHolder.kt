package br.com.alex.moov

class TMDBApiKeyHolder {

  companion object {
    init {
      System.loadLibrary("native-lib")
    }
  }

  /**
   * TMDB V3 auth
   */
  external fun getApiKey(): String

  /**
   * TMDB v4 auth
   */
  external fun getApiApiReadAccessToken(): String
}