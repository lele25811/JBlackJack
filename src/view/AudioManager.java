package view;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * La classe AudioManager fornisce un'implementazione del pattern Singleton per la gestione e riproduzione del audio.
 * Permette di caricare e riprodurre file audio.
 * I file audio vengono caricati dal file system utilizzando uno stream di input bufferizzato e riprodotti utilizzando l'interfaccia Clip dell'API Java Sound. 
 */
public class AudioManager {

	/**
	 * Istanza della classe AudioManager
	 */
	private static AudioManager instance;

	/**
	 * Metodo che recupera l'unica istanza possibile della classe.
	 * @return istanza della classe AudioManager
	 */
	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}

	/**
	 * Costruttore della classe
	 */
	private AudioManager() {
	}

	/**
	 * Metodo che riproduce il file audio dato in input.
	 * @param filename nome del file da riprodurre
	 */
	public void play(String filename) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
}
