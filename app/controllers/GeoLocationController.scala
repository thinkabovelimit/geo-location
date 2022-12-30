package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scalaj.http.{Http, Token}
import play.api.libs.json._
import utils.GenericUtils.{prepareCountryDetails, prepareBody}
import utils.Config

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class GeoLocationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  import play.api.libs.json.Json

  /**
   * API to get the country details
   */
  def getCountryDetails: Action[AnyContent] = Action { request =>
    val body = prepareBody(request.body.asJson.get.toString())
    val restCountry: String = Config.apply().
      getString("geo-location.api_url") + body.SearchCriteria.filterNot(_.isWhitespace)
    val output = Http(restCountry).asString
    if (output.code != 200) Ok(JsArray(IndexedSeq(JsObject(Seq()))))
    val json: JsValue = Json.parse(output.body).result.get(0)
    val jsonOut = prepareCountryDetails(json, body.fields)
    Ok(jsonOut)
  }
}
