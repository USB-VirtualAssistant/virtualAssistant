package org.fundacionjala.virtualassistant.npl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameEntityRecognition {
    public static String[] recognizeEntity(String userRequest) {
        try {
            InputStream inputStream = new FileInputStream("src/main/resources/nlp-model/en-ner-person.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
            inputStream.close();

            NameFinderME nameFinder = new NameFinderME(model);
            String[] sentence = userRequest.split(" ");
            Span[] spans = nameFinder.find(sentence);

            String[] entities = Span.spansToStrings(spans, sentence);
            nameFinder.clearAdaptiveData();
            return entities;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String[]{};
    }
}