import os
import whisper
from fastapi import FastAPI, File, UploadFile

app = FastAPI()

@app.post('/record')
async def convert_to_text(audio_file: UploadFile = File(...)):
    if not audio_file:
        return "No audio file found", 400
    
    with open(audio_file.filename, "wb") as file:
        file.write(audio_file.file.read())

    model = whisper.load_model("tiny")
    result = model.transcribe(audio_file.filename)
    return result["text"].strip()
