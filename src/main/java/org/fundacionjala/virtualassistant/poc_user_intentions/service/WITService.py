from jproperties import Properties
from fastapi import FastAPI, HTTPException, Query
import httpx
import os

root_conf_dir = os.path.dirname(os.path.dirname(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.dirname(
        os.path.abspath(__file__))))))))
properties_file_data = os.path.join(root_conf_dir, "resources", "application.properties")
properties_config = Properties()

with open(properties_file_data, "rb") as file:
    properties_config.load(file)

class WitAiResponseHandler:
    @staticmethod
    def create_response(input_text: str, intent, entities):
        response = {
            "input_text": input_text,
            "intent": intent,
            "entities": entities
        }
        return response

class WitAiTextProcessor:
    def __init__(self, wit_token, target_entity=None, target_property=None):
        self.wit_token = wit_token
        self.target_entity = target_entity
        self.target_property = target_property
        self.http_client = httpx.AsyncClient()
        self.response_data = {}

    async def process_given_text(self, input_text: str):
        try:
            response = await self.get_response(input_text)
            self.response_data = response.json()
            intent, entities = self.process_text()
            response = WitAiResponseHandler.create_response(input_text, intent, entities)
            return response
        except httpx.RequestError as e:
            raise HTTPException(status_code=500, detail=f"Failed processing: {str(e)}")

    async def get_response(self, input_text: str):
        url = properties_config.get("wit.url").data
        act_given_params = {"q": input_text}
        act_headers = {"Authorization": f"Bearer {self.wit_token}"}
        return await self.http_client.get(url, params=act_given_params, headers=act_headers)

    def process_text(self):
        try:
            intent = self.response_data['intents'][0]['name'] if 'intents' in self.response_data else None
            entities = self.response_data['entities']
            return intent, entities
        except Exception as e:
            print("Error processing text:", str(e))
            return None, None

    async def close(self):
        await self.http_client.aclose()

app = FastAPI()
config_wit_token = properties_config.get("wit.token")
text_processor = WitAiTextProcessor(wit_token=config_wit_token.data)

@app.get("/handle_text/")
async def process_text_endpoint(input_data: str = Query(...)):
    response = await text_processor.process_given_text(input_data)
    return response

@app.on_event("shutdown")
async def shutdown_event():
    await text_processor.close()
