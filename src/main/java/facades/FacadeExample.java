package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckNorrisDTO;
import dtos.RenameMeDTO;
import entities.RenameMe;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.MediaType;

//import errorhandling.RenameMeNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FacadeExample {

    private static final String CHUCK_URL = "https://api.chucknorris.io/jokes/random";
    private static final String DAD_URL = "https://icanhazdadjoke.com";
    private static FacadeExample instance;
    private static EntityManagerFactory emf;
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    //Private Constructor to ensure Singleton
    private FacadeExample() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FacadeExample getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FacadeExample();
        }
        return instance;
    }

    public String readJoke(String url_string) {
        String jsonStr = "";
        try {
            URL url = new URL(url_string);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", MediaType.APPLICATION_JSON);
            con.setRequestProperty("User-Agent", "MyApp");
            try (Scanner scan = new Scanner(con.getInputStream())) {
                while (scan.hasNext()) {
                    jsonStr += scan.nextLine();
                }
            }
        } catch (IOException ioex) {
            System.out.println("Error: " + ioex.getMessage());
        }
        return jsonStr;
    }



    public ChuckNorrisDTO getChuckJoke() {
        ChuckNorrisDTO chuck = GSON.fromJson(readJoke(CHUCK_URL), ChuckNorrisDTO.class);
        return chuck;
    }

    public static void main(String[] args) {
        FacadeExample facadeExample = getFacadeExample(EMF_Creator.createEntityManagerFactory());

        String output = facadeExample.readJoke(CHUCK_URL);
        System.out.println(output);
        ChuckNorrisDTO chuck = facadeExample.getChuckJoke();
        System.out.println(chuck.getValue());
    }
}
