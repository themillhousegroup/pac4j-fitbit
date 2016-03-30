package com.themillhousegroup.pac4jfitbit.test

object ProfileFixtures {

  // "city" and "country" are supplied if "location" scope is requested

  val fullProfile = """
    {
    "user": {
      "age":39,
      "avatar":"https://static0.fitbit.com/images/profile/defaultProfile_100_male.gif",
      "avatar150":"https://static0.fitbit.com/images/profile/defaultProfile_150_male.gif",
      "averageDailySteps":0,
      "city":"Melbourne",
      "corporate":false,
      "country":"AU",
      "dateOfBirth":"1977-03-27",
      "displayName":"DisplayName",
      "distanceUnit":"METRIC",
      "encodedId":"3G75NZ",
      "features":{"exerciseGoal":true},
      "fullName":"Fully Namesmith",
      "gender":"FEMALE",
      "glucoseUnit":"METRIC",
      "height":181,
      "heightUnit":"METRIC",
      "locale":"en_AU",
      "memberSince":"2016-03-26",
      "offsetFromUTCMillis":-25200000,
      "startDayOfWeek":"SUNDAY",
      "strideLengthRunning":94.2,
      "strideLengthRunningType":"default",
      "strideLengthWalking":75.10000000000001,
      "strideLengthWalkingType":"default",
      "timezone":"America/Los_Angeles",
      "topBadges":[],
      "weight":0,
      "weightUnit":"METRIC"
    }
  }
                    """

  // "city" and "country" are supplied if "location" scope is requested
  val basicProfile = """
    {
    "user": {
      "age":39,
      "avatar":"https://static0.fitbit.com/images/profile/defaultProfile_100_male.gif",
      "avatar150":"https://static0.fitbit.com/images/profile/defaultProfile_150_male.gif",
      "averageDailySteps":0,
      "corporate":false,
      "dateOfBirth":"1977-03-27",
      "displayName":"DisplayName",
      "distanceUnit":"METRIC",
      "encodedId":"3G75NZ",
      "features":{"exerciseGoal":true},
      "fullName":"Fully Namesmith",
      "gender":"FEMALE",
      "glucoseUnit":"METRIC",
      "height":181,
      "heightUnit":"METRIC",
      "locale":"en_AU",
      "memberSince":"2016-03-26",
      "offsetFromUTCMillis":-25200000,
      "startDayOfWeek":"SUNDAY",
      "strideLengthRunning":94.2,
      "strideLengthRunningType":"default",
      "strideLengthWalking":75.10000000000001,
      "strideLengthWalkingType":"default",
      "timezone":"America/Los_Angeles",
      "topBadges":[],
      "weight":0,
      "weightUnit":"METRIC"
    }
  }
                    """

}
