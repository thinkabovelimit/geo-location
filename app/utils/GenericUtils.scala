package utils

import play.api.libs.json._
import play.api.libs.json.Json
import org.json4s._
import org.json4s.jackson.Serialization.read
import com.typesafe.config._
object GenericUtils {



  def prepareBody(body: String): Body = {
    implicit val formats = DefaultFormats
    read[Body](body)
  }

  def prepareMap(json: JsValue): Map[String, String] = {
    val valid = json.validate[Map[String, String]]
    valid match {
      case s: JsSuccess[Map[String, String]] => s.value
      case e: JsError => Map("error" -> "error")
    }
  }

  def prepareCurrency(json: JsValue): Map[String, Map[String, String]] = {
    val valid = json.validate[Map[String, Map[String, String]]]
    valid match {
      case s: JsSuccess[Map[String, Map[String, String]]] => s.value
    }
  }

  def prepareResponse(jsonBody: Option[Array[String]], jsonList: Seq[(String, JsValue)]): Seq[(String, JsValue)] = {
    var selectList = Seq[(String, JsValue)]()

    if (jsonBody.isEmpty) {
      jsonList
    }
    else {
      for (value <- jsonBody.get) {
        val listVal = jsonList.filter(_._1.contains(value))
        selectList = selectList :+ (listVal.head._1 -> listVal.head._2)

      }
      selectList

    }


  }

  def prepareCountryDetails(json: JsValue, body: Option[Array[String]]): JsArray = {
    val name = json.result.get("name").result.get("official")
    val key = Json.toJson(prepareCurrency(json.result.get("currencies")).keys.head).toString().replace("\"", "")
    val currency = json.result.get("currencies").result.get(key).result.get("name")
    val latLong = json.result.get("latlng")
    val languages = Json.toJson(prepareMap(json.result.get("languages")).values)
    val timeZones = json.result.get("timezones")
    val jsonList = Seq("name" -> name,
      "currency" -> currency,
      "latLong" -> latLong,
      "languages" -> languages,
      "timeZones" -> timeZones)
    val selectList = prepareResponse(body, jsonList)
    JsArray(IndexedSeq(JsObject(selectList)))
  }

}
