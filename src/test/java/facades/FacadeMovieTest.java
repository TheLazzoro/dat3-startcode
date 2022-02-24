package facades;

import dtos.MovieDTO;
import entities.Movie;
import errorhandling.MovieNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeMovieTest {

    private static EntityManagerFactory emf;
    private static FacadeMovie facade;

    public FacadeMovieTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = FacadeMovie.getFacadeExample(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.getTransaction().commit();

            // In order to get the correct id every time, we make a new transaction per object we persist.
            em.getTransaction().begin();
            em.persist(new Movie("Titanic", "Christopher Nolan", 1997));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Movie("The Dark Knight", "Christopher Nolan", 2008));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001));
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void FacadeMovie_GetCount() throws Exception {
        assertEquals(3, facade.getMovieCount(), "Expects three rows in the database");
    }

    @Test
    public void FacadeMovie_GetAll() throws Exception {
        List<MovieDTO> movies = facade.getAll();

        assertEquals(3, movies.size());
    }

    @Test
    public void FacadeMovie_GetById() throws MovieNotFoundException {
        MovieDTO movie = facade.getById(2);

        assertEquals("The Dark Knight", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals(2008, movie.getReleaseYear());
    }

    @Test
    public void FacadeMovie_GetByTitle() throws MovieNotFoundException {
        MovieDTO movie = facade.getByTitle("Titanic");

        assertEquals("Titanic", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals(1997, movie.getReleaseYear());
    }

    @Test
    public void FacadeMovie_Create() {
        Movie movie = new Movie("Godfather", "Francis Ford Coppola", 1972);
        MovieDTO movieDTO = new MovieDTO(movie);
        MovieDTO returnedMovie = facade.create(movieDTO);

        assertEquals("Godfather", returnedMovie.getTitle());
        assertEquals("Francis Ford Coppola", returnedMovie.getDirector());
        assertEquals(1972, returnedMovie.getReleaseYear());
    }
}

