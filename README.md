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
     "com.themillhousegroup" %% "pac4j-fitbit" % "0.1.9"
   )

```


### Usage

Once you have __pac4j-fitbit__ added to your project, you can start using it like this:

##### Add it to your list of clients in your setup code:
```
import com.themillhousegroup.pac4jfitbit

...
val facebookClient = new FacebookClient("fbId", "fbSecret")
...
val fitbitClient = new FitBitClient("fitBitId", "fitBitSecret")
...

new Clients(baseUrl + "/callback", facebookClient, fitbitClient)

```

##### Customizing scope:
By default, the only `scope` argument provided to the FitBit API will be `profile`.
If you need more access, supply a `Set[FitBitScope]` as the third argument to the `FitBitClient` constructor, e.g.:

```
   import com.themillhousegroup.pac4jfitbit
   import com.themillhousegroup.pac4jfitbit.FitBitScopes._


   val fitbitClient = new FitBitClient(
   	"fitBitId", 
   	"fitBitSecret",
   	Set(profile, location, activity, weight)
  )
```
The names of the `FitBitScope` instances are exactly as per the [FitBit Scope documentation](https://dev.fitbit.com/docs/oauth2/#scope).  


### Credits

- [Pac4j](https://github.com/pac4j/pac4j)
- [FitBit developer documentation](https://dev.fitbit.com/docs/)

