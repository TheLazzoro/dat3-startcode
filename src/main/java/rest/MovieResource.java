package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import errorhandling.MovieNotFoundException;
import utils.EMF_Creator;
import facades.FacadeMovie;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("/movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final FacadeMovie FACADE =  FacadeMovie.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("/count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
       
        long count = FACADE.getMovieCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesAll() {
        List<MovieDTO> movies = FACADE.getAll();

        return Response
                .ok()
                .entity(GSON.toJson(movies))
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") long id) throws MovieNotFoundException {
        MovieDTO movie = FACADE.getById(id);

        System.out.println("Title: " + movie.getTitle());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Release year: " + movie.getReleaseYear());

        return Response
                .ok()
                .entity(GSON.toJson(movie))
                .build();
    }

    @Path("title/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieByTitle(@PathParam("title") String title) throws MovieNotFoundException {
        MovieDTO movie = FACADE.getByTitle(title);

        return Response
                .ok()
                .entity(GSON.toJson(movie))
                .build();
    }
}
