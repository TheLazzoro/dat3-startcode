/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.MovieDTO;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeMovie fm = FacadeMovie.getFacadeExample(emf);
        fm.create(new MovieDTO(new entities.Movie("Titanic", "Christopher Nolan", 1997)));
        fm.create(new MovieDTO(new entities.Movie("The Dark Knight", "Christopher Nolan", 2008)));
        fm.create(new MovieDTO(new entities.Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001)));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
