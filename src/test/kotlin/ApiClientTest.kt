import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.StringBody.exact

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ApiClientTest {

    private lateinit var mockServer: ClientAndServer
    private val port = 1234

    @BeforeAll
    fun startMockServer() {
        mockServer = startClientAndServer(port)
    }

    @AfterAll
    fun stopMockServer() {
        mockServer.stop()
    }

    @Test
    fun `should send body required to create something`() {
        //Given
        mockServer
            .`when`(
                request().withMethod("POST")
            )
            .respond(
                response()
                    .withStatusCode(201)
            )
        //When
        ApiClient(baseUrl = "http://localhost:$port").createSomething("""{ "name" : "a new something"}""")

        //Then
        mockServer.verify(
            request()
                .withBody(exact("clearly not what I've set it as")) //This should cause a failure, but the test passes
                .withPath("/my-resource")
        )
    }
}