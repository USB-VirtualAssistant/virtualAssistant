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
    not_valid = "not_valid_format_audio"

class AudioValidator:
    @staticmethod
    def validate_audio_file(audio_file):
        if audio_file is None or audio_file.filename == "":
            return False, enumException.not_found
        if not audio_file.content_type.startswith("audio/"):
            return False, enumException.not_valid
        return True, None

class FileHandler:
    @staticmethod
    def delete_audio(audio_file):
        try:
            os.remove(audio_file)
        except FileNotFoundError:
            pass

class Transcriber:
    @staticmethod
    def transcribe(model, audio_file):
        model = whisper.load_model(model)
        result = model.transcribe(audio_file)
        FileHandler.delete_audio(audio_file)
        return result["text"].strip()

@app.post('/record')
async def convert_to_text(audio_file: UploadFile = File(None)):
    is_valid, error = AudioValidator.validate_audio_file(audio_file)
    if not is_valid:
        return error
    with open(audio_file.filename, "wb") as file:
        file.write(audio_file.file.read())
    return Transcriber.transcribe(enumModel.tiny, audio_file.filename)
