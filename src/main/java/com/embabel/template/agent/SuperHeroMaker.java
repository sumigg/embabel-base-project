package com.embabel.template.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Export;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.template.model.SuperHero;

@Agent(
        name = "JavaSuperHeroMaker",
        description = "Generate a superhero profile provide a detailed physical description explain their power including its limits. List signature weapons,and describe how these weapons interact.",
        beanName = "javaSuperHeroCreator")
public class SuperHeroMaker {

    // private final List<SuperHero> superHerosList = new ArrayList<>(); 



    @AchievesGoal(
            description = "Generate superheros profile from input text",
            export = @Export(name = "superheros", remote = true, startingInputTypes = {UserInput.class}))
    @Action
    public SuperHero GenerateSuperHero(UserInput userInput, Ai ai) {
        return ai
                .withAutoLlm()
                .createObjectIfPossible(
                        """
                                Create superheros descriptions provide there name ,desription about them their powerindex and weapons:
                               from input text  %s""".formatted(userInput.getContent()),
                        
SuperHero.class);
    }

    // @AchievesGoal(
    //         description = "Generate 5 superheros profile from input text",
    //         export = @Export(name = "superheros", remote = true, startingInputTypes = {UserInput.class}))
    // @Action
    // public SuperHero extractSuperHero(SuperHeroList superHeroList,  Ai ai) {
    //     return ai
    //             .withAutoLlm()
    //             .createObjectIfPossible(
    //                     """
    //                           Create five superheros provide name , desription their powerindex and weapons:
    //                            from  input text  %s""".formatted(superHeroList.toString()),
    //                     SuperHero.class
    //             );
    // }


    // @AchievesGoal(
    //         description = "Generate 5 superheros profile from input text",
    //         export = @Export(name = "superheros", remote = true, startingInputTypes = {UserInput.class}))
    // @Action
    // public List<SuperHero> AllSuperHeroProfiles( Ai ai){
    //    return superHerosList;
    // }


    // @Action
    // public SuperHeroList addAnotherSuperhero(SuperHero superHeros, Ai ai ){
    //     List<SuperHero> list = new ArrayList<>();
    //     list.add(superHeros);
    //     return new SuperHeroList(list); 
    // }

}
