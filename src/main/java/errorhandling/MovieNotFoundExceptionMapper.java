package errorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class MovieNotFoundExceptionMapper implements ExceptionMapper<MovieNotFoundException> {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(MovieNotFoundException ex) {
        Logger.getLogger(MovieNotFoundExceptionMapper.class.getName())
                .log(Level.SEVERE, null, ex);
        ExceptionDTO err = new ExceptionDTO(404, ex.getMessage());

        return Response
                .status(404)
                .encoding(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
