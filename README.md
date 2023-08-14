# Virtual Assistant

Whisper is a general-purpose speech recognition model. It is trained on a large dataset of diverse audio and is also a multitasking model that can perform multilingual speech recognition, speech translation, and language identification.
![image](https://raw.githubusercontent.com/openai/whisper/main/approach.png)
### Requirements
- Python 3.0+
- Whisper
- ffmpeg
- fastapi
- python-multipart
### Required installations for ASRClient
The correct implementation of ASRClient from this repository requires the prior installation of some essential tools for its smooth operation.

The following are the commands needed to perform the installation. You can use any of the following: pip, pip3 or apt.

If you choose to use the apt package manager, run the following commands in your terminal:

```bash
sudo apt install python3-pip
```
or 
```bash
sudo apt install python-pip
```
Now we will install whisper

```bash
pip3 install -U openai-whisper
```
or
```bash
pip install -U openai-whisper
```
Now we will install ffmpeg which is a fundamental library for whisper to work. 

```bash
sudo apt update && sudo apt install ffmpeg
```
Now we will install fastapi which will help us to set up the whisper service with python.

```bash
pip3 install fastapi
```
```bash
pip install fastapi
```
Now we will install uvicorn which will run the python ASR server. 

```bash
sudo apt install uvicorn
```
Now we will install python-multipart which is a support library for uvicorn.
```bash
pip3 install python-multipart
```
```bash
pip install python-multipart
```
### Run ASR Whisper Client
```bash
cd src/main/java/org/fundacionjala/virtualassistant/whisper/service
```
```bash
uvicorn ASRClient:app --reload
```
These steps will ensure that all necessary tools are available and ready to facilitate the successful installation of ASRClient.

Note: If you want to run the gradle build command and have it compile without errors you will have to run the server with ASRClient so that it can run and pass its tests.

![image](https://raw.githubusercontent.com/openai/whisper/main/approach.png)