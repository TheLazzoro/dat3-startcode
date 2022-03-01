package facades;

import dtos.MovieDTO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import entities.Movie;
import errorhandling.MovieNotFoundException;
import utils.EMF_Creator;

/**
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeMovie {

    private static FacadeMovie instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private FacadeMovie() {
    }


    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeMovie getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeMovie();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public MovieDTO create(MovieDTO mov) {
        entities.Movie movEntity = new entities.Movie(mov.getTitle(), mov.getDirector(), mov.getReleaseYear());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(movEntity);
    }

    /*
    public MovieDTO getById(long id) throws MovieNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            entities.Movie movEntity = em.find(entities.Movie.class, id);
            if (movEntity == null)
                throw new MovieNotFoundException("ID: " + id + " Movie entity was not found");
            return new MovieDTO(movEntity);
        } finally {
            em.close();
        }
    }
     */

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = getEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<MovieDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<entities.Movie> query = em.createNamedQuery("Movie.getAll", entities.Movie.class);
        List<entities.Movie> movies = query.getResultList();
        return MovieDTO.getDtos(movies);
    }

    public MovieDTO getByTitle(String title) throws MovieNotFoundException {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery tq = em.createNamedQuery("Movie.getByTitle", Movie.class);
            tq.setParameter("title", title); // found in the named query on Movie entity
            Movie movie = (Movie) tq.getSingleResult();
            if (movie == null)
                throw new MovieNotFoundException("The Movie entity with title: " + title + " Was not found");

            MovieDTO movieDTO = new MovieDTO((Movie) tq.getSingleResult());
            return movieDTO;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        FacadeMovie fm = getFacadeExample(emf);
        fm.getAll().forEach(dto -> System.out.println(dto));
    }
}
