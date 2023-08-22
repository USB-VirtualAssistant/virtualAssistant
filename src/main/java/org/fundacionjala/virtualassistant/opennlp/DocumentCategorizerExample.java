package org.fundacionjala.virtualassistant.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.tokenize.SimpleTokenizer;

public class DocumentCategorizerExample {

    private DocumentCategorizerExample() {
    }

    public static String getIntent(String userRequest) {
        InputStream modelIn = null;
        DoccatModel model = null;
        try {
            modelIn = new FileInputStream("src/main/resources/nlp-model/en-trained-model.bin");
            model = new DoccatModel(modelIn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(userRequest);
        double[] probabilities = categorizer.categorize(tokens);

        return categorizer.getBestCategory(probabilities);
    }

}
