import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.coustemerRouteing() {

    route("/customer") {
        get {
            if(customerDb.isNotEmpty()) {
                call.respond(customerDb)
            } else {
                call.respondText("No Customer Found")
            }
        }

        get("{id}") {
            val id = call.parameters["id"]?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)

            val customer = customerDb.find { it.id== id}

            customer?: return@get call.respondText("No customer Found for $id", status = HttpStatusCode.NotFound)

            call.respond(customer)
        }

        post {
            val customer = call.receive<Customer>()

            customerDb.add(customer)
            return@post call.respondText("Customer added success", status = HttpStatusCode.Created)
        }
    }

}