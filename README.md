# Virtual Assistant

### Requirements

- Python 3.0+
- Whisper
- ffmpeg
- fastapi
- python-multipart
### Required installations for ASRClient

```bash
$ sudo apt install python3-pip
$ pip3 install -U openai-whisper
$ sudo apt update && sudo apt install ffmpeg
$ pip3 install fastapi
$ sudo apt install uvicorn
$ pip3 install python-multipart
```
### Run ASR Whisper Client
```bash
$ cd src/main/java/org/fundacionjala/virtualassistant/service/whisper
$ uvicorn ASRClient:app --reload
```



