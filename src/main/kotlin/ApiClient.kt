import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Response

class ApiClient(private val baseUrl: String) {

    fun createSomething(body: String): Response {
        val (_, response, _) = Fuel
            .post("$baseUrl/my-resource")
            .response()

        return response
    }

}