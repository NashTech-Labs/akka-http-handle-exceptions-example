import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler

object ExplicitlyHandlingExceptionsExample extends App {
  implicit val actorSystem: ActorSystem = ActorSystem("HandlingExceptions")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import actorSystem.dispatcher

  /** Explicitly Defined Exception Handlers
   * RuntimeException Handler
   * NoSuchElementException Handler
   */
  val runtimeExceptionHandler: ExceptionHandler = ExceptionHandler {
    case runtimeException: RuntimeException =>
      complete(StatusCodes.NotFound, runtimeException.getMessage)
  }

  val noSuchElementExceptionHandler: ExceptionHandler = ExceptionHandler {
    case noSuchElementException: NoSuchElementException =>
      complete(StatusCodes.BadRequest, noSuchElementException.getMessage)
  }

  val routeWithExplicitExceptionHandlers =
    handleExceptions(runtimeExceptionHandler) { // handle exceptions from the top level
      path("api" / "people") {
        get {
          // directive that throws some exception
          throw new RuntimeException("Getting all the people took too long")
        } ~
        post {
          handleExceptions(noSuchElementExceptionHandler) { // handle exceptions within
            parameter('id) { id =>
              if (id.length > 2)
                throw new NoSuchElementException(s"$id is not a valid id")

              complete(StatusCodes.OK)
            }
          }
        }
      }
    }

  // akka-http server
  Http().bindAndHandle(routeWithExplicitExceptionHandlers, "localhost", 8080)
}
