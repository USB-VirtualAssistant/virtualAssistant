package org.fundacionjala.virtualassistant.npl;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.tokenize.SimpleTokenizer;

public class DocumentCategorizerExample {
    public static void main(String[] args) throws Exception {
        InputStream modelIn = new FileInputStream("src/main/resources/nlp-model/en-trained-model.bin");
        DoccatModel model = new DoccatModel(modelIn);

        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

        String document = "Replay this song, please";

        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(document);

        double[] probabilities = categorizer.categorize(tokens);
        String category = categorizer.getBestCategory(probabilities);

        System.out.println("Category: " + category);

    }

}
