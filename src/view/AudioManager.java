package view;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Questa classe gestisce l'audio nel programma.
 * è implementata come un singleton per garantire un'unica istanza di AudioManager nell'applicazione.
 */
public class AudioManager {

	private static AudioManager instance;
	private Clip clip;

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

	/**
	 * Riproduce un file audio specificato dal percorso fornito, effettuando una conversione 
	 * del formato audio originale, se necessario, per assicurare la compatibilità con i 
	 * formati supportati dal sistema audio.
	 * Eccezioni gestite all'interno del blocco try-catch:
	 * throws UnsupportedAudioFileException se il formato del file audio non è supportato
	 * throws LineUnavailableException se la linea audio non è disponibile per la riproduzione
	 * throws IOException se si verifica un errore durante la lettura del file audio
	 * @param filePath il percorso del file audio da riprodurre
	 */
	public void play(String filePath) {
		try {
			File audioFile = new File(filePath);
			if (!audioFile.exists()) {
				System.err.println("File audio non trovato: " + filePath);
				return;
			}

			AudioInputStream originalStream = AudioSystem.getAudioInputStream(audioFile);

			AudioFormat originalFormat = originalStream.getFormat();

			AudioFormat targetFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					originalFormat.getSampleRate(),
					16, // 16 bit
					2,  // Stereo
					4,  // Frame size (2 bytes per sample x 2 channels)
					originalFormat.getSampleRate(),
					false // little-endian
					);

			AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, originalStream);

			if (!AudioSystem.isLineSupported(new DataLine.Info(Clip.class, targetFormat))) {
				System.err.println("Il formato audio convertito non è supportato.");
				return;
			}

			clip = AudioSystem.getClip();
			clip.open(convertedStream);

			clip.start();

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
}