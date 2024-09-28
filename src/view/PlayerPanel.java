package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.ModuleLayer.Controller;
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

/*
 * Ambiente di gioco grafico dove vengono messe le carte per il player
 */
@SuppressWarnings("deprecation")
public class PlayerPanel extends JPanel implements Observer{
	
	private TitledBorder titledBorder;
	private BlackJackPlayer player;
	private ArrayList<Carta> mano;
	private ArrayList<Image> carteImages;
	private JLabel punti;
	private Integer puntiAttuali = 0;
	private Integer puntiMano1 = 0;
	private Integer puntiMano2 = 0;
	private Integer indexMano = 0;
	private boolean isPrimeDueCarte = true;
	private ActionPlayerPanel actionPlayerPanel;
	private boolean isSplit = false;
	private GameController controller;
	
	public PlayerPanel(String name, BlackJackPlayer player) {
		this.player = player;
		controller = GameController.getIstance();
		
		carteImages = new ArrayList<>();
		punti = new JLabel("", SwingConstants.CENTER);
		
		setPreferredSize(new Dimension(200, 200));
		
		titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.RED),  // Bordo rosso
                player.getNickname(),                      // Titolo con il nome del giocatore
                TitledBorder.CENTER,                       // Posiziona il titolo al centro
                TitledBorder.TOP,                          // Posiziona il titolo in alto
                getFont(),                                 // Font del titolo
                Color.WHITE                               // Colore del titolo (bianco)
        );

        setBorder(titledBorder);
		setBackground(new Color(120, 0, 0, 0));
		setOpaque(false);
		setLayout(new BorderLayout());
		
		add(punti, BorderLayout.NORTH);
	}
	
	public void setPanelTitle() {
		titledBorder.setTitle(player.getNickname());
		repaint();
	}

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
						// chiedo carta mentre sono nello split
						if (indexMano == 0) {
				            // Prima mano
				            drawCard();  // Disegna la carta nella prima mano
				            calcolaPunteggioNuovaCarta(false);
				        } else if (indexMano == 1) {
				            // Seconda mano
				            drawCard();  // Disegna la carta nella seconda mano
				            calcolaPunteggioNuovaCarta(false);
				        }
					}
				}else if(action.equals("Raddoppio")) {
					if(!isSplit) {
						drawCard();
						calcolaPunteggioNuovaCarta(true);
					}else {
						// chiedo raddoppio mentre sono nello split
						if (indexMano == 0) {
				            // Prima mano
				            drawCard();  // Disegna la carta nella prima mano
				            calcolaPunteggioNuovaCarta(true);
				        } else if (indexMano == 1) {
				            // Seconda mano
				            drawCard();  // Disegna la carta nella seconda mano
				            calcolaPunteggioNuovaCarta(true);
				        }
					}
				}else if(action.equals("Dividi")) {
					isSplit = true;
					calcolaPunteggioIniziale();
					puntiMano1 = puntiAttuali /2;
					puntiMano2 = puntiAttuali /2;
					puntiAttuali = puntiAttuali /2;
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

	public void addActionPlayer(ActionPlayerPanel actionPlayerMenu) {
		this.actionPlayerPanel = actionPlayerMenu;
	}

	private void updatePunteggio() {
		if(!isSplit) {
			punti.setText("Punti attuali: " + puntiAttuali);
		}else {
			punti.setText("Punti attuali: " + puntiMano1 +" / "+puntiMano2);
		}
		punti.revalidate();
		punti.repaint();
	}
	
	public void drawCard() {
	    carteImages.clear(); // Pulisce la lista delle immagini precedenti
	    if (isSplit) {
			player.StampaManiSplit();

	        // Gestione dello split: due mani
	        ArrayList<Carta> mano1 = player.getMano1();
	        ArrayList<Carta> mano2 = player.getMano2();
	        
	        // Aggiungi le carte della mano1
	        for (Carta carta : mano1) {
	            String imagePath = carta.getPath(); // Ottieni il percorso dell'immagine
	            ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
	            Image img = cartaIcon.getImage(); 
	            Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
	            carteImages.add(imgScaled); // Aggiungi immagine ridimensionata alla lista
	        }
	        
	        // Aggiungi le carte della mano2
	        for (Carta carta : mano2) {
	            String imagePath = carta.getPath(); // Ottieni il percorso dell'immagine
	            ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
	            Image img = cartaIcon.getImage();
	            Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
	            carteImages.add(imgScaled); // Aggiungi immagine ridimensionata alla lista
	        }
	    } else {
	        // Gestione normale: una sola mano
	        mano = player.getMano(); // Ottieni la mano del giocatore
	        for (Carta carta : mano) {
	            String imagePath = carta.getPath(); // Ottieni il percorso dell'immagine
	            ImageIcon cartaIcon = new ImageIcon("./src/graphics/" + imagePath);
	            Image img = cartaIcon.getImage(); 
	            Image imgScaled = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Ridimensiona l'immagine
	            carteImages.add(imgScaled); // Aggiungi immagine ridimensionata alla lista
	        }
	    }
	    
	    revalidate(); // Forza la rilettura del layout
	    repaint(); // Ridisegna il pannello
	}
	
	public void calcolaPunteggioIniziale() {
		SwingUtilities.invokeLater(() -> {
			int[] punteggi = player.getPuntiMano();
			if (isPrimeDueCarte) {
				// Se sono le prime due carte, calcola il punteggio iniziale
				if (player.haveAsso() && punteggi.length == 2) {
					// Se c'è un asso, chiedi se contarlo come 1 o 11
					puntiAttuali = scegliPunteggioAsso(punteggi);
				} else {
					// Altrimenti prendi il punteggio normale
					puntiAttuali = punteggi[0];
				}
				isPrimeDueCarte = false; // Da ora in poi, considera le nuove carte separatamente
			}
			updatePunteggio();
		});
	}

	public void calcolaPunteggioNuovaCarta(boolean raddoppio) {		
		SwingUtilities.invokeLater(() -> {
			if(!isSplit) {
				// Per ogni nuova carta, aggiungi il suo valore al punteggio attuale
				Carta ultimaCarta = mano.get(mano.size() - 1);
				int valoreCarta = ultimaCarta.getValore();

				if ("Asso".equals(ultimaCarta.getStringValore())) {
					// Se l'ultima carta è un asso, chiedi se contarlo come 1 o 11
					valoreCarta = scegliPunteggioAsso(new int[]{valoreCarta, valoreCarta + 10});
				}
				// Aggiungi il valore della nuova carta al punteggio attuale
				puntiAttuali += valoreCarta;
			}else {
				
				if ("Asso".equals(player.getLastCartaSplit(indexMano))) {
					if(indexMano == 0) {
						int valoreMano = player.getLastCartaValoreSplit(indexMano);
						System.out.println("Ho trovato un asso puntiAttuali: "+puntiMano1);
						System.out.println("sommando l'asso "+valoreMano+" / "+valoreMano+10);
						puntiMano1 += scegliPunteggioAsso(new int[]{valoreMano, valoreMano + 10});
					}else if(indexMano == 1) {
						int valoreMano = player.getLastCartaValoreSplit(indexMano);
						System.out.println("Ho trovato un asso puntiAttuali: "+puntiMano2);
						System.out.println("sommando l'asso "+valoreMano+" / "+valoreMano+10);
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
						indexMano +=1;
						controller.updateIndexMano();
						passaMano();
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
						indexMano +=1;
					}
				}
			}
		});
	}

	private void passaMano() {
		MyPopup myPopup = new MyPopup("Passa turno!", "Hai passato la "+ indexMano +" mano con "+puntiAttuali);
		myPopup.showMessage();
	}

	public int scegliPunteggioAsso(int[] punteggi) {
	    if (punteggi.length == 2) { // Se ci sono due opzioni di punteggio (con Asso)
	    	System.out.println("Da dentro punteggi asso "+punteggi[0]+ " / "+punteggi[1]);
	        // Crea il messaggio e le opzioni per il popup
	    	int puntiAggiornatiUno = puntiAttuali+punteggi[0];
	    	int puntiAggiornatiUndici = puntiAttuali+punteggi[1];
	    	System.out.println("1: "+puntiAggiornatiUno + " 11: " + puntiAggiornatiUndici);
	        String message = "Vuoi contare l'Asso come 1 ("+puntiAggiornatiUno+") o 11 ("+puntiAggiornatiUndici+")?";
	        String[] options = {"Asso = 1 ("+puntiAggiornatiUno+")", "Asso = 11 ("+puntiAggiornatiUndici+")"};
	        
	        // Mostra il menu popup con le opzioni
	        int scelta = JOptionPane.showOptionDialog(
	            null, //componente padre della finestra 
	            message, // testo da visualizzare nella finestra
	            "Scegli il valore dell'Asso", // titolo finestra
	            JOptionPane.DEFAULT_OPTION, // tipo di operazioni che verranno mostrate
	            JOptionPane.QUESTION_MESSAGE, // tipo di messaggio che viene mostrato
	            null, // icona
	            options, // risposta 1
	            options[0] //risposta 2
	        );
	        if (scelta == 0) { // Asso = 1
	            return punteggi[0];
	        } else if (scelta == 1) { // Asso = 11
	            return punteggi[1];
	        }
	    }
	    return punteggi[0];
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    int xOffset = 10; // Offset iniziale per la prima mano
	    int yOffset = getHeight() - 210; // Posiziona le carte alla base del pannello

	    if (isSplit) {
	        // Disegna le carte della prima mano (mano 1)
	        for (int i = 0; i < player.getMano1().size(); i++) {
	            Image img = carteImages.get(i); // Ottieni l'immagine della carta dalla lista
	            g.drawImage(img, xOffset, yOffset, this); // Disegna la carta
	            xOffset += 35; // Sposta per la prossima carta nella prima mano
	        }
	        
	        // Offset per disegnare la seconda mano
	        int xOffsetSecondHand = 210; // Larghezza separazione tra le due mani
	        int xOffeset = 1;
	        for (int i = 0; i < player.getMano2().size(); i++) {
	        	xOffeset = 30 * player.getMano1().size(); 
	            Image img = carteImages.get(player.getMano1().size() + i); // Le carte della seconda mano sono dopo quelle della prima
	            g.drawImage(img, xOffsetSecondHand+xOffeset, yOffset, this); // Disegna la carta
	            xOffsetSecondHand += 35; // Sposta per la prossima carta nella seconda mano
	        }
	    } else {
	        // Disegna le carte normalmente (senza split)
	        for (Image carta : carteImages) {
	            g.drawImage(carta, xOffset, yOffset, this); // Disegna la carta
	            xOffset += 40; // Sposta la prossima carta di 40px a destra
	        }
	    }
	}
	
	
	public void passaTurno(boolean sballato) {
		System.out.println("VADO DI POPUP: sballato???" +sballato);
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
			System.out.println("Siamo nello split");
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
	}
	
	private void popUpRisultato(String title , String parola) {
		String testo = player.getNickname()+" ha "+parola+"!!"; 
		MyPopup myPopup = new MyPopup(title, testo);
		myPopup.showMessage();
		actionPlayerPanel.chiediNuovaPartita();
	}

	public void resetPartita() {
		puntiAttuali = 0;
	}
}
