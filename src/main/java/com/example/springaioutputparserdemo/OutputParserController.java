package com.example.springaioutputparserdemo;

import com.example.springaioutputparserdemo.entity.ActorsFilms;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OutputParserController {

    @Autowired
    private ChatClient     chatClient;

    @Autowired
    private ImageClient openAImageClient;

    @GetMapping("/ai/output")
    public ActorsFilms generate(@RequestParam(value = "actor", defaultValue = "Jeff Bridges") String actor) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class);

        String userMessage =
                """
                Generate the filmography for the actor {actor}.
                {format}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(userMessage, Map.of("actor", actor, "format", outputParser.getFormat() ));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();

        ActorsFilms actorsFilms = outputParser.parse(generation.getOutput().getContent());
        return actorsFilms;
    }
 @GetMapping("/image")
  public Image getImage(){
      ImageResponse response = openAImageClient.call(
              new ImagePrompt("Give me clear and HD image of a" +
                      " football player on ground",
                      OpenAiImageOptions.builder()
                              .withQuality("hd")
                              .withN(4)
                              .withHeight(1024)
                              .withWidth(1024).build())

      );
      //return response.getResult().getOutput().toString();
     return response.getResult().getOutput();
  }

}
