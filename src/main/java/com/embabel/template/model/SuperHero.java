package com.embabel.template.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonClassDescription("Superhero details")
@JsonDeserialize(as = SuperHero.class)
public record SuperHero(
        @JsonPropertyDescription("Superhero name ") String name,
        @JsonPropertyDescription("Superhero descrition") String description,
        @JsonPropertyDescription("Superhero power strength") int powerIndex,
        @JsonPropertyDescription("List of superhero weapons") List<Weapons> weapons) {

    @JsonCreator
    public SuperHero(@JsonProperty("name") String name, @JsonProperty("description") String description,
            @JsonProperty("powerIndex") int powerIndex, @JsonProperty("weapons") List<Weapons> weapons) {
        this.name = name;
        this.description = description;
        this.powerIndex = powerIndex;
        this.weapons = weapons;

    }

}
