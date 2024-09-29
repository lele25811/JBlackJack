package view;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Questa classe gestisce l'audio nel programma.
 * è implementata come un singleton per garantire un'unica istanza di AudioManager nell'applicazione.
 */
public class AudioManager {
	
	// dichiarazioni di variabili di istanza
    private static AudioManager instance;
    private Clip clip;
    private long clipTimePosition;
    private boolean isPaused = false;
    
    /**
     * Restituisce l'istanza unica di AudioManager.
     * Se non esiste ancora un'istanza, ne crea una nuova.
     * @return L'istanza unica di AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

        public void play(String filePath) {
            try {
                File audioFile = new File(filePath);
                if (!audioFile.exists()) {
                    System.err.println("File audio non trovato: " + filePath);
                    return;
                }

                AudioInputStream originalStream = AudioSystem.getAudioInputStream(audioFile);

                // Ottieni il formato originale del file audio
                AudioFormat originalFormat = originalStream.getFormat();

                // Definisci il formato target (ad esempio PCM_SIGNED, 44100 Hz, 16 bit, stereo, little-endian)
                AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    originalFormat.getSampleRate(),
                    16, // 16 bit
                    2,  // Stereo
                    4,  // Frame size (2 bytes per sample x 2 channels)
                    originalFormat.getSampleRate(),
                    false // little-endian
                );

                // Converti lo stream nel nuovo formato target
                AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, originalStream);

                // Controlla se il formato convertito è supportato
                if (!AudioSystem.isLineSupported(new DataLine.Info(Clip.class, targetFormat))) {
                    System.err.println("Il formato audio convertito non è supportato.");
                    return;
                }

                clip = AudioSystem.getClip();
                clip.open(convertedStream);

                // Avvia la riproduzione
                clip.start();

                // Chiudi il clip dopo la riproduzione
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });

            } catch (UnsupportedAudioFileException e) {
                System.err.println("Il formato del file audio non è supportato.");
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                System.err.println("Linea audio non disponibile.");
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("Errore durante la lettura del file audio.");
                e.printStackTrace();
            }
        }

        public void stop() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }
    }

