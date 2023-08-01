package org.fundacionjala.virtualassistant.repository;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import io.github.givimad.whisperjni.WhisperContext;
import io.github.givimad.whisperjni.WhisperFullParams;
import io.github.givimad.whisperjni.WhisperJNI;
import io.github.givimad.whisperjni.WhisperSamplingStrategy;
import lombok.Data;

@Data
public class WhisperClient implements ASRClient {

    private static final Path MODEL_WHISPER = Path.of("ggml-tiny.bin");

    private WhisperJNI whisper;

    private String transcription;

    public WhisperClient() throws IOException{
        WhisperJNI.loadLibrary();
        whisper = new WhisperJNI();
    }

    @Override
    public void convertToText(Path audioFile) throws IOException, UnsupportedAudioFileException {
        validateModelAndAudioFilesExist(audioFile);
        float[] audioFrequency = regulateFrequency(audioFile);
        List<String> transcriptions = transcribeSegments(audioFrequency);
        transcription = String.join(" ", transcriptions);
    }

    private List<String> transcribeSegments(float[] samples) {
        List<String> transcriptions = new ArrayList<>();

        try (WhisperContext ctx = whisper.init(MODEL_WHISPER)) {
            WhisperFullParams params = new WhisperFullParams(WhisperSamplingStrategy.BEAN_SEARCH);
            params.printTimestamps = false;

            int result = whisper.full(ctx, params, samples, samples.length);
            if (result != 0) {
                throw new RuntimeException("Transcription failed with code " + result);
            }

            int numSegments = whisper.fullNSegments(ctx);
            for (int i = 0; i < numSegments; i++) {
                String transcription = whisper.fullGetSegmentText(ctx, i);
                transcriptions.add(transcription);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while transcribing segments", e);
        }

        return transcriptions;
    }

    private void validateModelAndAudioFilesExist(Path audioFile) {
        var audioPath = audioFile.toFile();
        var modelFile = MODEL_WHISPER.toFile();
        if (!modelFile.exists() || !modelFile.isFile()) {
            throw new RuntimeException("Missing model file: " + MODEL_WHISPER.toAbsolutePath());
        }
        if (!audioPath.exists() || !audioPath.isFile()) {
            throw new RuntimeException("Missing file");
        }
    }

    private float[] regulateFrequency(Path audioPath) {
        float[] audio = null;

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioPath.toFile())) {
            ByteBuffer audioBuffer = ByteBuffer.allocate(audioInputStream.available());
            audioBuffer.order(ByteOrder.LITTLE_ENDIAN);

            int bytesRead = audioInputStream.read(audioBuffer.array());
            if (bytesRead == -1) {
                throw new IOException("File is empty");
            }

            ShortBuffer shortBuffer = audioBuffer.asShortBuffer();
            audio = new float[audioBuffer.capacity() / 2];

            int index = 0;
            while (shortBuffer.hasRemaining()) {
                short sampleValue = shortBuffer.get();
                float normalizedValue = Math.max(-1f, Math.min((float) sampleValue / (float) Short.MAX_VALUE, 1f));
                audio[index++] = normalizedValue;
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        return audio;
    }

    @Override
    public String getText() {
        return transcription;
    }
}
