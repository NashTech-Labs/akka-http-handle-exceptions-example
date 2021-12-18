import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler

object ImplicitExceptionHandlerExample extends App {
  implicit val actorSystem: ActorSystem = ActorSystem("HandlingExceptions")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import actorSystem.dispatcher

  // Implicit Exception Handler
  implicit val customExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: RuntimeException =>
      complete(StatusCodes.NotFound, e.getMessage)
    case e: IllegalArgumentException =>
      complete(StatusCodes.BadRequest, e.getMessage)
  }

  val simpleRoute =
    path("api" / "people") {
      get {
        // directive that throws some exception
        throw new RuntimeException("Getting all the people took too long")
      } ~
      post {
        parameter('id) { id =>
          if (id.length > 2)
            throw new NoSuchElementException(s"$id is not a valid id")

          complete(StatusCodes.OK)
        }
      }
    }

  // akka-http server
  Http().bindAndHandle(simpleRoute, "localhost", 8081)
}
