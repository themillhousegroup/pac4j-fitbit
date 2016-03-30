package com.themillhousegroup.pac4jfitbit

case class FitBitScope(name: String)

object FitBitScopes {

  val activity = FitBitScope("activity")
  val heartrate = FitBitScope("heartrate")
  val location = FitBitScope("location")
  val nutrition = FitBitScope("nutrition")
  val profile = FitBitScope("profile")
  val settings = FitBitScope("settings")
  val sleep = FitBitScope("sleep")
  val social = FitBitScope("social")
  val weight = FitBitScope("weight")

  val all = Set(
    activity,
    heartrate,
    location,
    nutrition,
    profile,
    settings,
    sleep,
    social,
    weight
  )

  val profileOnly = Set(profile)
}