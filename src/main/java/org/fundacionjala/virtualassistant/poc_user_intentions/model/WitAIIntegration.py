from wit import Wit

access_token = "PYMNGHHB77HJZOX3L2VYXE6I4TXVAYTA"
client = Wit(access_token)

def process_text(message_text):
    try:
        response = client.message(message_text)
        intent = response['intents'][0]['name'] if 'intents' in response else None
        entities = response['entities']
        return intent, entities
    except Exception as e:
        print("Error processing text:", str(e))
        return None, None


text = "What is the temperature right now?"
intent, entities = process_text(text)
print("Intent:", intent)
print("Entities:", entities)