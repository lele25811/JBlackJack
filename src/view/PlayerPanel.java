package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import controller.GameController;
import model.BlackJackPlayer;
import model.Carta;
import model.TavoloDaGioco;
import model.UpdateEvent;

/**
 * PlayerPanel è una classe che estende JPanel e implementa l'interfaccia Observer per gestire la visualizzazione
 * delle carte e dei punteggi di un giocatore nel gioco di BlackJack. La classe si occupa di aggiornare la UI
 * quando il giocatore riceve nuove carte, calcola il punteggio attuale, gestisce, se necessario, lo split delle mani, e 
 * fornisce interazioni con l'utente per scegliere il valore dell'asso. 
 * Inoltre, la classe gestisce la visualizzazione dei risultati del giocatore come vittoria, sconfitta o sballo.
 */
@SuppressWarnings("deprecation")
public class PlayerPanel extends JPanel implements Observer{

	private TitledBorder titledBorder;	/** Un bordo con titolo utilizzato per visualizzare il nome del giocatore */
	private BlackJackPlayer player;	/** L'istanza del giocatore associato a questo pannello. */
	private ArrayList<Carta> mano;	/** Una lista che contiene le carte attualmente in mano al giocatore. */
	private ArrayList<Image> carteImages;	/** Una lista di immagini delle carte che rappresenta graficamente le carte nella mano del giocatore. */
	private JLabel punti;	/** Etichetta che visualizza il punteggio corrente del giocatore nel pannello. */
	private Integer puntiAttuali = 0;	/** Il punteggio attuale del giocatore basato sulle carte in mano. */
	private Integer puntiMano1 = 0;		/** Il punteggio associato alla prima mano quando il giocatore effettua uno split. */
	private Integer puntiMano2 = 0;		/** Il punteggio associato alla seconda mano quando il giocatore effettua uno split. */
	private Integer indexMano = 0;		/** Un indice che rappresenta quale mano il giocatore sta giocando in caso di split. */
	private boolean isPrimeDueCarte = true;		/** Indica se il giocatore ha ricevuto solo le prime due carte. */
	private ActionPlayerPanel actionPlayerPanel;	/** Il pannello associato alle azioni del giocatore. */
	private boolean isSplit = false;		/** Indica se il giocatore ha diviso la mano (split). Se vero, il giocatore sta giocando due mani separate. */
	private GameController controller;		/** Controller principale per gestire la logica di gioco. */
	private AudioManager audioManager;		/** Gestore dell'audio per riprodurre suoni durante l'interazione con l'interfaccia utente. */

	/**
	 * Costruttore della classe PlayerPanel.
	 * Inizializza il pannello del giocatore con il nome e l'oggetto BlackJackPlayer associato.
	 * Imposta il bordo, l'audio manager e il controller.
	 * @param name  Il nome del pannello (non utilizzato direttamente).
	 * @param player L'istanza di BlackJackPlayer associata al pannello.
	 */
	public PlayerPanel(String name, BlackJackPlayer player) {
		this.player = player;
		audioManager = AudioManager.getInstance();
		controller = GameController.getIstance();

		carteImages = new ArrayList<>();
		punti = new JLabel("", SwingConstants.CENTER);

		setPreferredSize(new Dimension(200, 200));

		titledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.RED),
				player.getNickname(),                     
				TitledBorder.CENTER,                      
				TitledBorder.TOP,                         
				getFont(),                                
				Color.WHITE                     
				);

		setBorder(titledBorder);
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);
		setLayout(new BorderLayout());

		add(punti, BorderLayout.NORTH);
	}

	/**
	 * Imposta il titolo del pannello utilizzando il nickname del giocatore.
	 * Aggiorna il titolo e ridisegna il pannello.
	 */
	public void setPanelTitle() {
		titledBorder.setTitle(player.getNickname());
		repaint();
	}

	/**
	 * Metodo che gestisce gli aggiornamenti provenienti dall'oggetto osservato (TavoloDaGioco).
	 * Riceve notifiche su eventi come la distribuzione delle carte, nuove carte, raddoppio, split, vittoria o sconfitta,
	 * e aggiorna di conseguenza il pannello del giocatore.
	 * @param o   L'oggetto osservato (in questo caso, TavoloDaGioco).
	 * @param arg L'argomento passato dall'oggetto osservato (in questo caso, un UpdateEvent).
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof TavoloDaGioco && arg instanceof UpdateEvent) {
			UpdateEvent event = (UpdateEvent) arg;

			String action = event.getAction();
			Object data = event.getData();
			if (data instanceof BlackJackPlayer) {
				if(action.equals("DistribuisciCarteIniziali")) {
					drawCard();
				}else if(action.equals("DistribuzioneTerminata")) {
					calcolaPunteggioIniziale();
				}else if(action.equals("NuovaCarta")) {
					if(!isSplit) {
						drawCard();
						calcolaPunteggioNuovaCarta(false);
					}else {
						if (indexMano == 0) {
							drawCard();
							calcolaPunteggioNuovaCarta(false);
						} else if (indexMano == 1) {
							drawCard();
							calcolaPunteggioNuovaCarta(false);
						}
					}
				}else if(action.equals("Raddoppio")) {
					if(!isSplit) {
						drawCard();
						calcolaPunteggioNuovaCarta(true);
					}else {
						if (indexMano == 0) {
							drawCard();
							calcolaPunteggioNuovaCarta(true);
						} else if (indexMano == 1) {
							drawCard();
							calcolaPunteggioNuovaCarta(true);
						}
					}
				}else if(action.equals("Dividi")) {
					isSplit = true;
					calcolaPunteggioIniziale();
					puntiMano1 = controller.getPlayer().getLastCard().getValore();
					puntiMano2 = controller.getPlayer().getLastCard().getValore();
					puntiAttuali = puntiMano1;
					drawCard();
				}
				updatePunteggio();
				if(action.equals("Vittoria")) {
					popUpRisultato(action, "vinto");
				}else if(action.equals("Sconfitta")) {
					popUpRisultato(action, "perso");
				}
			}
		}
	}

	/**
	 * Aggiunge il pannello delle azioni del giocatore (ActionPlayerPanel) al PlayerPanel.
	 * @param actionPlayerMenu Il pannello che gestisce le azioni del giocatore.
	 */
	public void addActionPlayer(ActionPlayerPanel actionPlayerMenu) {
		this.actionPlayerPanel = actionPlayerMenu;
	}

	/**
	 * Aggiorna il punteggio del giocatore e lo visualizza nell'etichetta punti.
	 * Se il giocatore ha effettuato uno split, visualizza i punteggi delle due mani.
	 */
	private void updatePunteggio() {
		if(!isSplit) {
			punti.setText("Punti attuali: " + puntiAttuali);
		}else {
			punti.setText("Punti attuali: " + puntiMano1 +" / "+puntiMano2);
		}
		punti.revalidate();
		punti.repaint();
	}

	/**
	 * Disegna una nuova carta nel pannello, caricando l'immagine associata e ridimensionandola per essere mostrata.
	 * Gestisce sia il caso di una mano singola che di due mani (in caso di split).
	 */
	public void drawCard() {
		carteImages.clear();
		if (isSplit) {
			ArrayList<Carta> mano1 = player.getMano1();
			ArrayList<Carta> mano2 = player.getMano2();

			for (Carta carta : mano1) {
				String imagePath = carta.getPath();
				ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
				Image img = cartaIcon.getImage(); 
				Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				carteImages.add(imgScaled);
			}
			for (Carta carta : mano2) {
				String imagePath = carta.getPath();
				ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
				Image img = cartaIcon.getImage();
				Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				carteImages.add(imgScaled);
			}
		} else {
			mano = player.getMano();
			for (Carta carta : mano) {
				String imagePath = carta.getPath();
				ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
				Image img = cartaIcon.getImage(); 
				Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				carteImages.add(imgScaled);
			}
		}
		audioManager.play("./src/sounds/card.wav");
		revalidate();
		repaint();
	}

	/**
	 * Calcola il punteggio iniziale del giocatore con le prime due carte.
	 * Se è presente un Asso, consente di scegliere se contarlo come 1 o 11.
	 */
	public void calcolaPunteggioIniziale() {
		SwingUtilities.invokeLater(() -> {
			int[] punteggi = player.getPuntiMano();
			if (isPrimeDueCarte) {
				if (player.haveAsso() && punteggi.length == 2) {
					puntiAttuali = scegliPunteggioAsso(punteggi);
				} else {
					puntiAttuali = punteggi[0];
				}
				isPrimeDueCarte = false;
			}
			updatePunteggio();
		});
	}

	/**
	 * Calcola il punteggio del giocatore quando riceve una nuova carta.
	 * Se viene eseguito il raddoppio, il turno termina dopo l'aggiunta della carta.
	 * Gestisce anche il calcolo del punteggio in caso di split.
	 * @param raddoppio True se il giocatore ha effettuato un raddoppio, false altrimenti.
	 */
	public void calcolaPunteggioNuovaCarta(boolean raddoppio) {		
		SwingUtilities.invokeLater(() -> {
			if(!isSplit) {
				Carta ultimaCarta = mano.get(mano.size() - 1);
				int valoreCarta = ultimaCarta.getValore();
				if ("Asso".equals(ultimaCarta.getStringValore())) {
					valoreCarta = scegliPunteggioAsso(new int[]{valoreCarta, valoreCarta + 10});
				}
				puntiAttuali += valoreCarta;
			}else {

				if ("Asso".equals(player.getLastCartaSplit(indexMano))) {
					if(indexMano == 0) {
						int valoreMano = player.getLastCartaValoreSplit(indexMano);
						puntiMano1 += scegliPunteggioAsso(new int[]{valoreMano, valoreMano + 10});
					}else if(indexMano == 1) {
						int valoreMano = player.getLastCartaValoreSplit(indexMano);
						puntiMano2 += scegliPunteggioAsso(new int[]{valoreMano, valoreMano + 10});
					}
				}else {
					if(indexMano == 0) {
						puntiMano1 += player.getLastCartaValoreSplit(indexMano);
						puntiAttuali = puntiMano1;
					}else {
						puntiMano2 += player.getLastCartaValoreSplit(indexMano);
						puntiAttuali = puntiMano2;
					}
				}
			}
			updatePunteggio();
			if (!isSplit) {
				if (puntiAttuali > 21) {
					passaTurno(true);
				} else if (raddoppio) {
					passaTurno(false);
				}
			} else if (isSplit) {
				if (puntiAttuali > 21) {
					if(indexMano >= 1) {
						passaTurno(true);
					}else {
						updateIndexManoPlayerPanel(false);						
						controller.updateIndexMano();
						controller.cambioTastiSplit();
					}
				} else if (raddoppio) {
					if(indexMano >= 1) {
						if(puntiAttuali > 21) {
							passaTurno(true);
						}else {
							passaTurno(false);
						}
					}else {
						controller.updateIndexMano();
						passaTurno(false);
						updateIndexManoPlayerPanel(true);
						controller.cambioTastiSplit();
					}
				}
			}
		});
	}

	/**
	 * Mostra un popup con il risultato della mano corrente. Se il giocatore non ha sballato,
	 * viene mostrato un messaggio di passaggio mano, altrimenti un messaggio dove dice che ha sballato.
	 * @param sballato Indica se il giocatore ha sballato (true) o meno (false).
	 */
	private void passaMano(boolean sballato) {
		int nMano = indexMano+1;
		if(!sballato) {
			MyPopup myPopup = new MyPopup("Passa mano!", "Hai passato la "+ nMano +" mano con "+puntiAttuali);
			myPopup.showMessage();
		}else {
			MyPopup myPopup = new MyPopup("Sballato mano!", "Hai Sballato la "+ nMano +" mano con "+puntiAttuali);
			myPopup.showMessage();
		}
	}

	/**
	 * Permette al giocatore di scegliere il valore dell'Asso tra 1 o 11 tramite un popup.
	 * Mostra un messaggio che visualizza il punteggio aggiornato in base alla scelta.
	 * @param punteggi Array di due valori contenenti le opzioni di punteggio per l'Asso (1 e 11).
	 * @return Il valore scelto dall'utente (1 o 11).
	 */
	public int scegliPunteggioAsso(int[] punteggi) {
		if (punteggi.length == 2) { // Se ci sono due opzioni di punteggio (con Asso)
			int puntiAggiornatiUno = puntiAttuali+punteggi[0];
			int puntiAggiornatiUndici = puntiAttuali+punteggi[1];
			String message = "Vuoi contare l'Asso come 1 ("+puntiAggiornatiUno+") o 11 ("+puntiAggiornatiUndici+")?";
			String[] options = {"Asso = 1 ("+puntiAggiornatiUno+")", "Asso = 11 ("+puntiAggiornatiUndici+")"};

			int scelta = JOptionPane.showOptionDialog(
					null, 
					message,
					"Scegli il valore dell'Asso",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0] 
					);
			if (scelta == 0) { 
				return punteggi[0];
			} else if (scelta == 1) { 
				return punteggi[1];
			}
		}
		return punteggi[0];
	}


	/**
	 * Disegna le carte del giocatore sul pannello, gestendo il caso di split.
	 * Se lo split è attivo, le carte vengono separate graficamente in due mani.
	 * @param g L'oggetto Graphics utilizzato per disegnare le carte sul pannello.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int xOffset = 10;
		int yOffset = getHeight() - 210;

		if (isSplit) {
			for (int i = 0; i < player.getMano1().size(); i++) {
				Image img = carteImages.get(i);
				g.drawImage(img, xOffset, yOffset, this); 
				xOffset += 35; 
			}
			int xOffsetSecondHand = 210;
			int xOffeset = 1;
			for (int i = 0; i < player.getMano2().size(); i++) {
				xOffeset = 30 * player.getMano1().size(); 
				Image img = carteImages.get(player.getMano1().size() + i); 
				g.drawImage(img, xOffsetSecondHand+xOffeset, yOffset, this); 
				xOffsetSecondHand += 35;
			}
		} else {
			for (Image carta : carteImages) {
				g.drawImage(carta, xOffset, yOffset, this);
				xOffset += 40;
			}
		}
	}

	/**
	 * Mostra un popup alla fine del turno o della mano per informare se il giocatore ha sballato o
	 * ha passato il turno con i punti attuali. Se lo split è attivo, gestisce la visualizzazione per ogni mano.
	 * @param sballato Indica se il giocatore ha sballato (true) o meno (false).
	 */
	public void passaTurno(boolean sballato) {
		if(!isSplit) {
			if(sballato) {
				MyPopup myPopup = new MyPopup("Sballato!", "Hai sballato con "+puntiAttuali);
				myPopup.showMessage();
			}else {
				MyPopup myPopup = new MyPopup("Passa turno!", "Hai passato il turno con "+puntiAttuali);
				myPopup.showMessage();
			}
			actionPlayerPanel.passaTurno(puntiAttuali);
		}else {
			int numeroMano = indexMano +1;
			if(sballato) {
				MyPopup myPopup = new MyPopup("Sballato!", "Hai sballato la "+ numeroMano +" mano con "+puntiAttuali);
				myPopup.showMessage();
			}else {
				MyPopup myPopup = new MyPopup("Passa turno!", "Hai passato la "+ numeroMano +" mano con "+puntiAttuali);
				myPopup.showMessage();
			}if(indexMano >= 1) {
				actionPlayerPanel.passaTurno(puntiAttuali);
			}
		}
		controller.setPuntiPlayerSplit(puntiMano1, puntiMano2);
	}

	/**
	 * Mostra un popup con il risultato della partita. Il popup contiene il nickname del giocatore e
	 * il risultato (vittoria, sconfitta, pareggio). Riproduce anche un suono in base al risultato.
	 * @param title Titolo del popup e nome del file audio da riprodurre.
	 * @param parola Parola che descrive il risultato del giocatore (ad esempio, "vinto" o "perso").
	 */
	private void popUpRisultato(String title , String parola) {
		audioManager.play("./src/sounds/"+title+".wav");
		String testo = player.getNickname()+" ha "+parola+"!!"; 
		MyPopup myPopup = new MyPopup(title, testo);
		myPopup.showMessage();
		actionPlayerPanel.chiediNuovaPartita();
	}

	/**
	 * Resetta il punteggio attuale del giocatore a 0 per iniziare una nuova partita.
	 */
	public void resetPartita() {
		puntiAttuali = 0;
	}

	/**
	 * Aggiorna l'indice della mano corrente e passa automaticamente alla mano successiva
	 * se la prima mano è conclusa.
	 * @param raddoppio Indica se il giocatore ha raddoppiato la puntata.
	 */
	public void updateIndexManoPlayerPanel(boolean raddoppio) {
		if(indexMano == 0 && puntiAttuali < 21 && !raddoppio) {
			passaMano(false);
		}else if (indexMano == 0 && puntiAttuali > 21 && !raddoppio) {
			passaMano(true);
		}
		indexMano +=1;
	}
}