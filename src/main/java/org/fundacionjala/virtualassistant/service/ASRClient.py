import os
import whisper
from flask import Flask, request

app = Flask(__name__)

@app.route('/record', methods=['POST'])
def convertToText():
    if 'audioFile' not in request.files:
        return "No audio file found", 400
    file = request.files['audioFile']
    file_path = os.path.join('uploads', file.filename)
    file.save(file_path)
    model = whisper.load_model("tiny")
    result = model.transcribe(file_path)
    os.remove(file_path)
    return result["text"]

if __name__ == '__main__':
    app.run()
