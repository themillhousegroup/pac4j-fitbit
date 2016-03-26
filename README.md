pac4j-fitbit
============================

[Pac4j](https://github.com/pac4j/pac4j) integration for the FitBit API.


### Installation

Bring in the library by adding the following to your ```build.sbt```. 

  - The release repository: 

```
   resolvers ++= Seq(
     "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
   )
```
  - The dependency itself: 

```
   libraryDependencies ++= Seq(
     "com.themillhousegroup" %% "pac4j-fitbit" % "0.1.xx"
   )

```

Please note - this library is not ready for production use! Once it is, the version number will be in the `1.x` range.


### Usage

Once you have __pac4j-fitbit__ added to your project, you can start using it like this:

##### Add it to your list of clients in your setup code:
```
import com.themillhousegroup.pac4j-fitbit

...
val facebookClient = new FacebookClient("fbId", "fbSecret")
...
val fitbitClient = new FitBitClient("fitBitId", "fitBitSecret")
...

new Clients(baseUrl + "/callback", facebookClient, fitbitClient)

```


### Credits

- [Pac4j](https://github.com/pac4j/pac4j)
- [FitBit developer documentation](https://dev.fitbit.com/docs/)

