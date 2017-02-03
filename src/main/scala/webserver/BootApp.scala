package webserver

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get, _}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory


object BootApp extends App with Boot {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  private val config = ConfigFactory.load()
  lazy val routes =
    path("hello") {
      get {
        complete("Hola2")
      }
    }

//  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
  Http().bindAndHandle(routes, "localhost",3000)
}