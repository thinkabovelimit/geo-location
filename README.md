# Geo-information API
API to get information about country information like name,currency,languages etc

## Technology stack
* [play-2.8.18](https://www.playframework.com/)
* [scala-2.13.10](https://www.scala-lang.org/)
## Features
### API method
* POST - `localhost:9000/api/v1/country`
* BODY

  `{
  "fields":["name","languages","latLong","timeZones","currency"],
  "SearchCriteria": "india"
  }`
* RESPONSE

  `[
  {
  "name": "Republic of India",
  "languages": [
  "English",
  "Hindi",
  "Tamil"
  ]
  }
  ]`
## Configuration
* Application configuration file can be found under `conf` folder.
### application.conf
* General application configurations.
## Build
* Application can be build using [sbt-1.3.13](https://www.scala-sbt.org/)
### Steps to run in local
* Execute command ``sbt run`` from root directory to run application.