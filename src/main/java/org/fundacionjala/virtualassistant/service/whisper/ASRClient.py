import os
import whisper
from fastapi import FastAPI, File, UploadFile
from enum import Enum

app = FastAPI()

class enumModel(str, Enum):
    tiny = "tiny"
    small = "small"
    medium = "medium"
    large = "large"

class enumException(str, Enum):
    not_found = "not_found_audio_file"

@app.post('/record')
async def convert_to_text(audio_file: UploadFile = File(...)):
    is_valid_audio, error = validate_audio_file(audio_file)
    if not is_valid_audio:
        return error
    return transcribe(enumModel.tiny, audio_file.filename)

def validate_audio_file(audio_file):
    if not audio_file:
        return False, enumException.not_found
    with open(audio_file.filename, "wb") as file:
        file.write(audio_file.file.read())
    return True, None

def transcribe(model, audio_file):
    model = whisper.load_model(model)
    result = model.transcribe(audio_file)
    delete_audio(audio_file)
    return result["text"].strip()

def delete_audio(audio_file):
    os.remove(audio_file)
