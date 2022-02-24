/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tha
 */
public class MovieDTO {
    private long id;
    private String title;
    private String director;
    private int releaseYear;
    private Set<ActorDTO> actors;

    public static List<MovieDTO> getDtos(List<entities.Movie> rms){
        List<MovieDTO> movdtos = new ArrayList();
        rms.forEach(rm->movdtos.add(new MovieDTO(rm)));
        return movdtos;
    }

    public MovieDTO(entities.Movie mov) {
        if(mov.getId() != null)
            this.id = mov.getId();
        this.title = mov.getTitle();
        this.director = mov.getDirector();
        this.releaseYear = mov.getReleaseYear();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "MovieDTO{" + "id=" + id + ", title=" + title + ", director=" + director + ", releaseYear=" + releaseYear +'}';
    }
    
    
    
    
    
    
}
