from flask import Flask, request, jsonify
import requests
import subprocess
import threading

MICROSERVICE_PORT = 8082
RASA_PORT = 3333
HOST_ADDRESS = '0.0.0.0'
RASA_URL = f"http://localhost:{RASA_PORT}/model/parse"
CONSUME_ROUTE = '/consume-rasa'
MODEL_PATH = "models/"

class RasaRunner:
    def __init__(self, model_path, port=3333):
        self.model_path = model_path
        self.port = port

    def start_rasa_server(self):
        rasa_command = [
            "rasa",
            "run",
            "--enable-api",
            "--port",
            str(self.port),
            "-m",
            self.model_path
        ]
        try:
            subprocess.run(rasa_command, check=True)
        except subprocess.CalledProcessError as e:
            print("An error occurred:", e)

class RasaMicroservice:
    def __init__(self, rasa_port, microservice_port):
        self.rasa_port = rasa_port
        self.microservice_port = microservice_port
        self.rasa_runner = RasaRunner(model_path=MODEL_PATH, port=rasa_port)

    def start_services(self):
        rasa_thread = threading.Thread(target=self.rasa_runner.start_rasa_server)
        rasa_thread.start()
        
        self.app.run(host=HOST_ADDRESS, port=self.microservice_port)

    def initialize_flask_app(self):
        self.app = Flask(__name__)
        self.app.route(CONSUME_ROUTE, methods=['POST'])(self.consume_rasa)

    def consume_rasa(self):
        try:
            request_data = request.json
            input_text = request_data.get('text', '')

            if not input_text:
                return jsonify({"error": "Text not provided."}), 400

            payload = {
                "text": input_text
            }

            response = requests.post(RASA_URL, json=payload)

            if response.status_code == 200:
                parsed_data = response.json()
                return jsonify(parsed_data), 200
            else:
                return jsonify({"error": "An error occurred while parsing the text."}), response.status_code

        except Exception as e:
            return jsonify({"error": "An error occurred.", "details": str(e)}), 500

if __name__ == '__main__':
    microservice = RasaMicroservice(rasa_port=RASA_PORT, microservice_port=MICROSERVICE_PORT)
    microservice.initialize_flask_app()
    microservice.start_services()
