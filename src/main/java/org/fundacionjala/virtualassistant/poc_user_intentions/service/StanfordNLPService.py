import stanza
from fastapi import FastAPI

class StanFordNLPProcessor:
    def __init__(self):
        try:
            self.nlp_pipeline = stanza.Pipeline('en', processors='tokenize,pos,lemma,depparse,ner')
        except:
            stanza.download('en')
            self.nlp_pipeline = stanza.Pipeline('en', processors='tokenize,pos,lemma,depparse,ner')

    def analyze_text(self, input_text):
        processed_docs = []
        for sentence in self.nlp_pipeline(input_text).sentences:
            processed_doc = {
                "Tokens": [token.text for token in sentence.tokens],
                "Part-of-Speech Tags": [word.xpos for word in sentence.words],
                "NamedEntities": [entity.text for entity in sentence.ents],
                "DependencyTree": [f'{word.text} ({word.deprel})' for word in sentence.words]
            }
            processed_docs.append(processed_doc)
        return processed_docs

app = FastAPI()
language_processor = StanFordNLPProcessor()

@app.post("/analyze-text")
def analyze_text(input_text):
    processed_docs = language_processor.analyze_text(input_text)
    return processed_docs
