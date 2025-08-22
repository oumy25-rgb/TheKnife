
package theknife;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

/**
 * La classe <strong>Cliente</strong> rappresenta un utente registrato della piattaforma
 * <em>TheKnife</em> con ruolo di cliente.
 * <p>
 * Un cliente può:
 * <ul>
 *   <li>Gestire una lista di ristoranti preferiti (aggiungere, rimuovere, visualizzare)</li>
 *   <li>Inserire, modificare ed eliminare le proprie recensioni</li>
 *   <li>Visualizzare le proprie recensioni personali e quelle dei ristoranti</li>
 *   <li>Cercare ristoranti per località o vicino al proprio domicilio</li>
 * </ul>
 * La persistenza dei dati relativi ai preferiti viene gestita tramite file CSV,
 * identificato dal codice fiscale del cliente.
 * </p>
 * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */

public class Cliente extends Utente {
	/* Lista dei nomi dei ristoranti preferiti del cliente.*/
    private ArrayList<String> preferiti;
	/*Lista delle recensioni scritte dal cliente.*/
    private ArrayList<Recensione> recensioni;
	/* Percorso del file CSV in cui vengono salvati i ristoranti preferiti
      dell'utente (associato al suo codice fiscale).*/
    private final String filePreferiti;

	/*Costruttore della classe <code>Cliente</code>.
     Inizializza i dati dell'utente, carica i ristoranti preferiti da file
     e recupera le recensioni già scritte dal cliente.*/

    /**
     * Costruisce un oggetto {@link Cliente} inizializzando i dati personali,
     * il file dei preferiti e le recensioni associate.
     * <p>
     * Richiama il costruttore della superclasse {@link Utente} impostando il ruolo come "cliente".
     * Viene creato il percorso del file dei preferiti specifico per il cliente 
     * e vengono caricati i ristoranti preferiti e le recensioni precedenti del cliente.
     * </p>
     *
     * @param nome il nome del cliente
     * @param cognome il cognome del cliente
     * @param codFiscale il codice fiscale del cliente
     * @param username lo username del cliente
     * @param password la password del cliente
     * @param dataNascita la data di nascita del cliente
     * @param luogoDomicilio il luogo di domicilio del cliente
     */
    
    public Cliente(String nome, String cognome, String codFiscale, String username, String password,
                   String dataNascita, String luogoDomicilio) {
        super(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, "cliente");
        this.filePreferiti = "src/dati/" + codFiscale + "_preferiti.csv";
        this.preferiti = caricaPreferiti();
        this.recensioni = Recensione.cercaPerCliente(getCodFiscale());
    }

    /**
     * Mostra il menu principale per un cliente e gestisce le interazioni con l'utente.
     * <p>
     * Il menu permette di:
     * <ul>
     *   <li>Visualizzare i ristoranti preferiti</li>
     *   <li>Aggiungere o rimuovere ristoranti dai preferiti</li>
     *   <li>Visualizzare, aggiungere, modificare o eliminare recensioni personali</li>
     *   <li>Cercare ristoranti in base alla città o al luogo di domicilio</li>
     *   <li>Effettuare il logout</li>
     * </ul>
     * Il metodo gestisce l'input dell'utente tramite {@link Scanner} e convalida le scelte, 
     * ripetendo la richiesta in caso di input non valido.
     * </p>
     *
     * @param gestioneRistoranti l'oggetto {@link GestioneRistoranti} utilizzato per le operazioni sui ristoranti
     * @param gestioneRecensioni l'oggetto {@link GestioneRecensioni} utilizzato per le operazioni sulle recensioni
     * @param scanner lo {@link Scanner} utilizzato per leggere l'input dell'utente da console
     */
    public void mostraMenu(GestioneRistoranti gestioneRistoranti, GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        int scelta = 0;
        boolean controllo;
        do {
        	
            System.out.println("\n===== MENU CLIENTE =====");
            System.out.println("1. Visualizza preferiti");
            System.out.println("2. Aggiungi ristorante ai preferiti");
            System.out.println("3. Rimuovi ristorante dai preferiti");
            System.out.println("4. Visualizza recensioni personali");
            System.out.println("5. Aggiungi recensione");
            System.out.println("6. Modifica recensione");
            System.out.println("7. Elimina recensione");
            System.out.println("8. Cerca ristoranti");
            System.out.println("0. Logout");
            
            do {
            	controllo=true;
            	try {
	            	System.out.print("Scelta: ");
	            	scelta = Integer.parseInt(scanner.nextLine());
            	}catch(NumberFormatException e) {
            		System.out.println("Valore inserito non valido, riprova.");
            		controllo = false;
            	}
            	
            }while(!controllo);
            
            switch (scelta) {
                case 1:
                    visualizzaPreferiti();
                    break;
                case 2:
                	ArrayList<Ristorante> listaRistorantiTrovati;
                	int i=1; int scegli=-1;
                	
                	String luogo=""; String risp="";
                	do {
                		controllo=true;
                		System.out.println("Vuoi cercare ristoranti vicini a te? (nel tuo luogo di domicilio) (s = si /n = no) ");
                		risp = scanner.nextLine();
                		
                		if(!risp.equalsIgnoreCase("s") && !risp.equalsIgnoreCase("n")) {
                			controllo = false;
                			System.out.println("Scelta non valida, inserire (s = si) (n = no)");
                		}
                		
                	}while(!controllo);
                	
                	if(risp.equalsIgnoreCase("n")) {
                	
	                	do {

	                		System.out.println("Inserire una città per continuare: ");
	                		luogo = scanner.nextLine().trim();
	                		
	                	}while(!GestioneUtenti.nominativoValido(luogo));
	                	
	                	listaRistorantiTrovati = GestioneRistoranti.cercaRistoranti(luogo);
	                	
                	}else
                		listaRistorantiTrovati = GestioneRistoranti.cercaRistoranti(this.getLuogoDomicilio());
                	
                	
                	if(!listaRistorantiTrovati.isEmpty()) {
                		System.out.println("Ristoranti trovati: \n");
        				System.out.println("----------------------------------------------------------------------");
        				for(Ristorante r : listaRistorantiTrovati) {
        					System.out.print((i++)+") "+r.getName()+"\n");
        					System.out.println("----------------------------------------------------------------------");
        				}
        				
        				do {
        				    controllo = true;
        				    System.out.print("Quale ristorante vuoi aggiungere ai preferiti? ");
        				    try {
        				        scegli = Integer.parseInt(scanner.nextLine());
        				        if (scegli < 1 || scegli > listaRistorantiTrovati.size()) {
        				            System.out.println("Scelta non presente, riprova.");
        				            controllo = false;
        				        }
        				    } catch (NumberFormatException e) {
        				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
        				        controllo = false;
        				    }
        				} while (!controllo);
        		
        				System.out.println("");
                        aggiungiPreferito(listaRistorantiTrovati.get(scegli - 1));
        				
        			}else {
        				System.out.println("Nessun ristorante trovato vicino a me.");
        			}

                    break;
                case 3:
                	
                	scegli=-1;
                	System.out.println(" \nRistoranti trovati nei miei preferiti:");
                	if(GestioneRistoranti.visualizzaLista(preferiti)) {
                	
        				do {
        				    controllo = true;
        				    System.out.print("Quale ristorante preferito vuoi rimuovere? ");
        				    try {
        				        scegli = Integer.parseInt(scanner.nextLine());
        				        if (scegli < 1 || scegli > preferiti.size()) {
        				            System.out.println("Scelta non presente, riprova.");
        				            controllo = false;
        				        }
        				    } catch (NumberFormatException e) {
        				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
        				        controllo = false;
        				    }
        				} while (!controllo);
        		
        				System.out.println("");
        				
        				rimuoviPreferito(preferiti.get(scegli - 1));
                	}
                    
                    break;
                case 4:
                    aggiornaRecensioniPersonali();
                    visualizzaRecensioniPersonali();
                    break;
                case 5:
                	scegli = -1;i=1;
                	
                	luogo=""; risp="";
                	do {
                		controllo=true;
                		System.out.println("Vuoi cercare ristoranti vicini a te? (nel tuo luogo di domicilio) (s = si /n = no) ");
                		risp = scanner.nextLine();
                		
                		if(!risp.equalsIgnoreCase("s") && !risp.equalsIgnoreCase("n")) {
                			controllo = false;
                			System.out.println("Scelta non valida, inserire (s = si) (n = no)");
                		}
                		
                	}while(!controllo);
                	
                	if(risp.equalsIgnoreCase("n")) {
                	
	                	do {

	                		System.out.println("Inserire una città per continuare: ");
	                		luogo = scanner.nextLine().trim();
	                		
	                	}while(!GestioneUtenti.nominativoValido(luogo));
	                	
	                	listaRistorantiTrovati = GestioneRistoranti.cercaRistoranti(luogo);
	                	
                	}else
                		listaRistorantiTrovati = GestioneRistoranti.cercaRistoranti(this.getLuogoDomicilio());
                	
                	
                	if(!listaRistorantiTrovati.isEmpty()) {
                		System.out.println(" \nRistoranti trovati:");
        				System.out.println("----------------------------------------------------------------------");
        				for(Ristorante r : listaRistorantiTrovati) {
        					System.out.print((i++)+") "+r.getName()+"\n");
        					System.out.println("----------------------------------------------------------------------");
        				}
                	
        				do {
        				    controllo = true;
        				    System.out.print("A quale ristorante vuoi dare una recensione? ");
        				    try {
        				        scegli = Integer.parseInt(scanner.nextLine());
        				        if (scegli < 1 || scegli > listaRistorantiTrovati.size()) {
        				            System.out.println("Scelta non presente, riprova.");
        				            controllo = false;
        				        }
        				    } catch (NumberFormatException e) {
        				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
        				        controllo = false;
        				    }
        				} while (!controllo);
        		
        				Ristorante r = listaRistorantiTrovati.get(scegli - 1);
        				
                        if (GestioneRecensioni.recensioneEsistente(r.getName(), getCodFiscale())) {
                            System.out.println("Hai già recensito questo ristorante. Usa l'opzione 6 per modificarla.");
                            break;
                        }
                        
                        String testo;
                        do {
                        	controllo = true;
                        	System.out.print("Testo recensione (opzionale) : "); 
                        	testo = scanner.nextLine().trim();
                        	
                        	if(testo.contains(",")) {
	                			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
	                			controllo = false;
	                		}

                        }while(!controllo);
                        
                        String s ="";
                        
                        do {
                        	controllo = true;
                        
                        	System.out.print("Stelle (1-5): ");
                        	s = scanner.nextLine().trim();
                        	
                        	if(GestioneUtenti.campoNonVuoto(s)) {
                        		
		                        	if(!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4") && !s.equals("5")) {
		                        		controllo = false;
		                        		System.out.println("Numero di stelle inserite non valido, riprova.");
		                        	}
    	        
                        	}else
                        		controllo = false;
                        	
                        }while(!controllo);
                        
                        double stelle = Double.parseDouble(s);
                        String data = java.time.LocalDate.now().toString();

                        Recensione rec = new Recensione(
                            r.getName(), 
                            getCodFiscale(), 
                            testo, 
                            stelle, 
                            data, 
                            null
                        );
                        gestioneRecensioni.aggiungiRecensione(rec);
                        System.out.println("Recensione aggiunta con successo.");
                	}else {
        				System.out.println("Nessun ristorante trovato vicino a me.");
        			}
                    break;
                case 6:
                    gestioneRecensioni.modificaRecensione(this, scanner);
                    aggiornaRecensioniPersonali();
                    break;
                case 7:
                    gestioneRecensioni.eliminaRecensione(this, scanner);
                    aggiornaRecensioniPersonali();
                    break;

                case 8:
                	
                	luogo=""; risp="";
                	do {
                		controllo=true;
                		System.out.println("Vuoi cercare ristoranti vicini a te? (nel tuo luogo di domicilio) (s = si /n = no) ");
                		risp = scanner.nextLine();
                		
                		if(!risp.equalsIgnoreCase("s") && !risp.equalsIgnoreCase("n")) {
                			controllo = false;
                			System.out.println("Scelta non valida, inserire (s = si) (n = no)");
                		}
                		
                	}while(!controllo);
                	
                	if(risp.equalsIgnoreCase("n")) {
                	
	                	do {
	                		System.out.println("Inserire una città per continuare: ");
	                		luogo = scanner.nextLine().trim();            	
	                		
	                	}while(!GestioneUtenti.nominativoValido(luogo));
	                	
	                	gestioneRistoranti.menuCercaRistoranti(luogo);
	                	
                	}else
                		gestioneRistoranti.menuCercaRistoranti(this.getLuogoDomicilio());
                    
                    break;
                case 0:
                    System.out.println("Logout effettuato.\n\n");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        
        } while (scelta != 0);
    }

	 /**
     * Aggiunge un ristorante alla lista dei preferiti, se non già presente.
     * Aggiorna anche il file dei preferiti.
     *
     * @param ristorante ristorante da aggiungere
     */

    private void aggiungiPreferito(Ristorante ristorante) {
        if (!preferiti.contains(ristorante.getName())) {
            preferiti.add(ristorante.getName());
            salvaPreferiti();
            System.out.println("Aggiunto ai preferiti.");
        } else {
            System.out.println("Il ristorante è già nei preferiti.");
        }
    }

	/**
     * Rimuove un ristorante dai preferiti.
     * Aggiorna anche il file dei preferiti.
     *
     * @param nome nome del ristorante da rimuovere
     */

    private void rimuoviPreferito(String nome) {
        if (preferiti.remove(nome)) {
            salvaPreferiti();
            System.out.println("Rimosso dai preferiti.");
        } else {
            System.out.println("Ristorante non nei preferiti.");
        }
    }

    /**
     * Visualizza la lista dei ristoranti preferiti dell'utente.
     * <p>
     * Se la lista {@code preferiti} è vuota, stampa un messaggio informativo
     * indicando che non sono presenti ristoranti preferiti. 
     * Altrimenti, stampa ciascun ristorante presente nella lista.
     * </p>
     */
    private void visualizzaPreferiti() {
        if (preferiti.isEmpty()) {
            System.out.println("Nessun preferito.");
        } else {
            System.out.println("Preferiti:");
            for (String r : preferiti) {
                System.out.println("- " + r);
            }
        }
    }

	/**
     * Visualizza tutte le recensioni personali scritte dal cliente,
     * mostrando anche eventuali risposte dei ristoratori.
     */

private void visualizzaRecensioniPersonali() {
    this.recensioni = Recensione.cercaPerCliente(getCodFiscale());

    if (recensioni.isEmpty()) {
        System.out.println("Non hai ancora scritto nessuna recensione.");
    } else {
        System.out.println("\n----- LE TUE RECENSIONI -----");
        for (Recensione r : recensioni) {
            System.out.println("\nRistorante: " + r.getNomeRistorante());
            
            // Stampa il voto
            System.out.print("Voto: ");
            int stelleIntere = (int) r.getStelle();
            for (int i = 0; i < stelleIntere; i++) {
                System.out.print("*");
            }
            System.out.println(" (" + r.getStelle() + "/5)");
            
            // Stampa DATA correttamente
            System.out.println("Data: " + r.getData());
            
            // Stampa TESTO RECENSIONE se non è vuoto 
            if(!r.getTestoRecensione().isEmpty())
            	System.out.println("Recensione: " + r.getTestoRecensione());
            
            // Stampa RISPOSTA solo se esiste ed è valida
            if (r.getRisposta() != null && !r.getRisposta().isEmpty()) {
                System.out.print("\nRisposta del ristoratore: ");
                System.out.println(r.getRisposta());
            }
        }
    }
}

/**
 * Aggiorna la lista delle recensioni personali dell'utente.
 * <p>
 * Recupera tutte le recensioni associate al codice fiscale dell'utente
 * tramite il metodo {@link Recensione#cercaPerCliente(String)} e aggiorna
 * l'attributo {@code recensioni}.
 * </p>
 */
    private void aggiornaRecensioniPersonali() {
        this.recensioni = Recensione.cercaPerCliente(getCodFiscale());
    }

	/**
     * Carica da file CSV i ristoranti preferiti del cliente.
     *
     * @return lista di stringhe contenente i nomi dei ristoranti preferiti
     */

    private ArrayList<String> caricaPreferiti() {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePreferiti))) {
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine().trim());
            }
        } catch (IOException e) {
            // File non esistente o errore, lista vuota.
        }
        return lista;
    }
    /**
     * Salva la lista dei ristoranti preferiti nel file CSV associato al cliente.
     */
    private void salvaPreferiti() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePreferiti))) {
            for (String ristorante : preferiti) {
                writer.write(ristorante);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei preferiti: " + e.getMessage());
        }
    }

}


