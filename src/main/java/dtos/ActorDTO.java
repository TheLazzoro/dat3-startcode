package dtos;

import entities.Actor;

public class ActorDTO {
    private long id;
    private String firstName;
    private String lastName;

    public ActorDTO(Actor actor) {
        if(actor.getId() != null)
            this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
    }
}
