package rest;

import entities.Movie;
import errorhandling.MovieNotFoundException;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

//Uncomment the line below, to temporarily disable this test
@Disabled

public class MovieDTOResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Movie m1, m2, m3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();

            m1 = new Movie("Titanic", "Christopher Nolan", 1997);
            m2 = new Movie("The Dark Knight", "Christopher Nolan", 2008);
            m3 = new Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001);

            em.getTransaction().begin();
            em.persist(m1);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(m2);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(m3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/movie").then().statusCode(200);
    }

    /*
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/xxx/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }
    */

    @Test
    public void testMovie_Count() throws Exception {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/movie/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }

    @Test
    public void testMovie_GetAll() throws Exception {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/movie/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", containsInAnyOrder(
                        "Titanic",
                        "The Dark Knight",
                        "The Lord of the Rings: The Fellowship of the Ring"
                ));
    }


    /*
    @Test
    public void testMovie_GetById() throws Exception {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/movie/{id}", m1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo(m1.getTitle()))
                .body("director", equalTo(m1.getDirector()))
                .body("releaseYear", equalTo(m1.getReleaseYear()));
    }
     */

    @Test
    public void testMovie_GetByTitle() throws Exception {
        String title = "Titanic";

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/movie/title/" + title).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo("Titanic"));
    }
}
