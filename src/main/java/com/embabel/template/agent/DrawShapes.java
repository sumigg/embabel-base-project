package com.embabel.template.agent;

import java.util.List;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.prompt.persona.Persona;
import com.embabel.common.ai.model.LlmOptions;


abstract class Personas {
        static final Persona PROMPT_CREATER = new Persona(
            "Create Prompts",
            "Experienced Prompt Engineer",
            "Technical and insightful",
            "Create efficient prompts"
    );

            static final Persona DRAW_SHAPE = new Persona(
            "Create Shape",
            "Experienced Shape Drawer",
            "Beutiful and accurate",
            "Create exact shapes"
    );
}

record GeneratedPromptList(List<String> promptList){}

record DetailMasterPrompt(String prompt){}

record ExecutePrompt(String shape){}



@Agent(description = "create a shape for given object")
public class DrawShapes {

    // @AchievesGoal(
    //         description = "List of Prompts has been created")
    @Action
    public GeneratedPromptList CreatePrompts(UserInput userInput, Ai ai){
        return ai
                // Higher temperature for more creative output
                .withLlm(LlmOptions
                        .withAutoLlm() // You can also choose a specific model or role here
                        .withTemperature(.7)
                )
                .withPromptContributor(Personas.PROMPT_CREATER)
                .creating(GeneratedPromptList.class)
                .fromPrompt(String.format("""
                              Read the input text below and generate a list of 5-10 'How-to' prompts based on its content.
                              Ensure each prompt is clear, provides necessary context from the text,
                              and is formatted for use with a LLM (Large Language Model).
  
                                # User input Text
                                %s
                                """,
                        userInput.getContent()
                ).trim());
    }


    // @AchievesGoal(
    //         description = "Master Prompt has been created")
        @Action
    public DetailMasterPrompt CreateDetailPrompt(GeneratedPromptList promptList, Ai ai){
        return ai
                // Higher temperature for more creative output
                .withLlm(LlmOptions
                        .withAutoLlm() // You can also choose a specific model or role here
                        .withTemperature(.7)
                )
                .withPromptContributor(Personas.PROMPT_CREATER)
                .creating(DetailMasterPrompt.class)
                .fromPrompt(String.format("""
                                Role: You are an expert Prompt Engineer specializing in clarity, brevity, and high-performance output.
                                Task: I am going to provide a list of different prompts intended to achieve the same goal. Your job is to analyze them and merge them into a single, highly efficient "Master Prompt."
                                Requirements for the Master Prompt:

                                Clarity: Use precise language to remove ambiguity.
                                Context: Include necessary background info from the list but keep it concise.
                                Structure: Use headers, delimiters (like ###), or bullet points to organize instructions.
                                Constraints: Clearly define any "do’s" and "don'ts."
                                Efficiency: Remove redundant words or filler phrases.

                                of prompts to merge
                                %s
                                """,
                        appendPrompts(promptList.promptList())
                ).trim());
    }


    @AchievesGoal(
            description = "shape has been created")
    @Action
    public ExecutePrompt executePrompt(DetailMasterPrompt masterPrompt, Ai ai){
        return ai
                // Higher temperature for more creative output
                .withLlm(LlmOptions
                        .withAutoLlm() // You can also choose a specific model or role here
                        .withTemperature(.7)
                )
                .withPromptContributor(Personas.DRAW_SHAPE)
                .creating(ExecutePrompt.class)
                .fromPrompt(String.format("""
                               Read the input text below and draw a simple, 
                               recognizable pixel art representation of the object described using only asterisks (*) and spaces. Use a grid format for clarity, with roughly 50x50 characters, ensuring the object is centered.

                                Input Text:  %s
                                """,masterPrompt.prompt()));
    }


    private String appendPrompts(List<String> prompts){
         int count = 0;
        StringBuilder sb = new StringBuilder("");
        for(String prompt : prompts){
            count++;
            sb.append(count + ". ").append(prompt).append("\n\n");
        }
        return sb.toString();


    }
    

}
