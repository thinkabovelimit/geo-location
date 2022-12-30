package utils
import com.typesafe.config._

object Config {
  val conf = ConfigFactory.load()
  def apply() = conf.getConfig("Application")
}
