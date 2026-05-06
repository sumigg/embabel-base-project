package com.embabel.template.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SuperHeroList(List<SuperHero> superHeroList) {

     @JsonCreator
    public SuperHeroList(@JsonProperty("superHeroList") List<SuperHero> superHeroList){
        this.superHeroList =superHeroList;
    }



}
