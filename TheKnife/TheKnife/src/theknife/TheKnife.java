
package theknife;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe principale dell'applicazione <strong>TheKnife</strong>.
 * <p>
 * Gestisce il menu iniziale e coordina le funzionalità principali:
 * registrazione, login, login come guest e uscita dal programma.
 * </p>
 * 
 * @author Giuseppina Salvati
 * @author Omema Gharsellaoui
 */

public class TheKnife {

    /**
     * <code>gestioneFile</code> è l'istanza della classe GestioneFile
     * utilizzata per leggere e scrivere dati su file.
     */
  
    static GestioneFile gestioneFile = new GestioneFile();
    
    /**
     * <code>gestioneRistoranti</code> è l'istanza della classe GestioneRistoranti
     * utilizzata per gestire i ristoranti registrati nel sistema.
     */
    
    static GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
    
    /**
     * <code>scanner</code> è lo scanner globale usato per leggere l'input da console.
     */
    
    public static Scanner scanner = new Scanner(System.in);
    /**
     * Metodo principale che avvia l'applicazione.
     * 
     * Mostra il menu iniziale con le seguenti opzioni:
     * <ul>
     *     <li>Registrazione utente</li>
     *     <li>Login</li>
     *     <li>Login come guest</li>
     *     <li>Uscita dall'applicazione</li>
     * </ul>
     * Per ogni scelta viene gestita la logica corrispondente.
     * 
     * 
     * @param args Argomenti della riga di comando (non utilizzati).
     * @throws NumberFormatException Se l'input numerico dell'utente non è valido.
     */
    
    public static void main(String[] args) {
     
    	/**
         * <code>controllo</code> variabile booleana usata per verificare
         * la validità degli input inseriti dall'utente nei cicli.
         */
    	
    	boolean controllo; 
        
    	/**
         * <code>gestioneRistoranti</code> istanza per la gestione dei ristoranti.
         */
        GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
        
        /**
         * <code>gestioneUtenti</code> istanza per la gestione degli utenti.
         */
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        
        
        
        /**
         * <code>nuovoUtente</code> variabile temporanea che mantiene l'utente appena creato.
         */
        
        Utente nuovoUtente = null;
        
        /**
         * <code>scelta</code> variabile intera per memorizzare l'opzione scelta
         * dall'utente nel menu principale.
         */
        
        int scelta = 0;
		
    	System.out.println("");
        System.out.println("Benvenuto su TheKnife!");
        
        /**
         * Stampa il menu principale e gestisce le scelte dell'utente.
         */
        
        do {
        	
            try { //l'utente che non effettua il login può:
                System.out.println("1. Registrati"); //registrarsi
                System.out.println("2. Login"); //fare l'accesso
                System.out.println("3. Login come guest"); 
                System.out.println("4. Esci");

                System.out.print("Cosa vuoi fare? : "); // <-- QUI l'input sarà su una nuova riga

                
                do {
                	controllo = true;
                	try {
                		scelta = Integer.parseInt(scanner.nextLine());
                		System.out.println();
                	}catch(NumberFormatException e) {
                		System.out.println("Valore inserito non valido, riprova.");
                		controllo = false;
                	}
                }while(!controllo);
                
                /**
                 * Gestione delle azioni in base alla scelta dell'utente.
                 * <p>
                 * I case gestiscono la registrazione, il login, l'accesso come guest e l'uscita.
                 * </p>
                 * <code>scelta</code>
                 */
                
                switch (scelta) {
                
                /**
                 * Registrazione di un nuovo utente.
                 * <p>
                 * Vengono richiesti i seguenti dati:
                 * <code>nome</code>, <code>cognome</code>, <code>codFiscale</code>,
                 * <code>username</code>, <code>password</code>, <code>dataNascita</code> (opzionale),
                 * <code>luogoDomicilio</code> e <code>ruolo</code> (cliente/ristoratore).
                 * </p>
                 */
                    case 1:
                        
                        // Registrazione utente
                    	String nome="";
                    	
                    	do {
                    		System.out.println("Inserisci il tuo nome:");
                    		nome = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.nominativoValido(nome));
                        
                        String cognome="";
                        do {
                        	System.out.println("Inserisci il tuo cognome:");
                        	cognome = scanner.nextLine().trim();
                        }while(!GestioneUtenti.nominativoValido(cognome));
                        
                        String codFiscale="";
                       
                        do {
                        	System.out.println("Inserisci il tuo codice fiscale:");
                        	codFiscale = scanner.nextLine().trim();
                        }while(!GestioneUtenti.campoNonVuoto(codFiscale) || !GestioneUtenti.formatoValido(codFiscale,"^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")
                        		|| GestioneUtenti.cfEsiste(codFiscale));  
                        // Regex: 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri o lettere
                        
                        String username = "";
                        do {
                        	controllo = true;
                        	System.out.println("Inserisci un username:");
                        	username = scanner.nextLine().trim();
                        	
                        	if(username.contains(",")) {
	                			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
	                			controllo = false;
	                		}

                        }while(!GestioneUtenti.campoNonVuoto(username) || GestioneUtenti.userEsiste(username) || !controllo);
                        
                        
                        String password="";
                        
                        do {
                        	controllo = true;
                        	System.out.println("Inserisci una password:");
                        	 password = scanner.nextLine().trim();
                        	 if(password.contains(",")) {
 	                			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
 	                			controllo = false;
 	                		}else 
 	                			password = GestioneUtenti.cifraPassword(password);
                        }while(!GestioneUtenti.campoNonVuoto(password) || !controllo);	
                        
                        String dataNascita="";
                        
                        do {
                        	controllo = true;
                        	
                        	System.out.println("Inserisci la tua data di nascita (opzionale - premi invio per saltare) (formato gg/mm/aaaa) :");
                        	dataNascita = scanner.nextLine().trim();
                        	 // Controllo formato: due cifre / due cifre / quattro cifre
                        	
                        	if(!dataNascita.isEmpty()) {
                        		if(!GestioneUtenti.formatoValido(dataNascita, "^\\d{2}/\\d{2}/\\d{4}$"))
                        			controllo = false;
                        	}
                        	
                        }while(!controllo);

                        
                        String luogoDomicilio = "";
                        
                        do {
                        	System.out.println("Inserisci il tuo luogo di domicilio:");
                        	luogoDomicilio = scanner.nextLine().trim();
                        }while(!GestioneUtenti.nominativoValido(luogoDomicilio));
                        
                        String ruolo ="";
                        
                        do {
                        	controllo = true;
                        	System.out.println("Inserisci il tuo ruolo (cliente/ristoratore):");
                        	ruolo = scanner.nextLine().toLowerCase().trim();
                        	if(GestioneUtenti.campoNonVuoto(ruolo)) {
	                        	if(!ruolo.equals("cliente") && !ruolo.equals("ristoratore")) {
	                        		controllo = false;
	                        		System.out.println("Ruolo inesistente, riprova");
	                        	}
                        	}else
                        		controllo = false;
                        	
                        }while(!controllo);

                        // Crea l'utente con data di nascita opzionale
                         nuovoUtente = new Utente(
                            nome, cognome, codFiscale, username, password, 
                            dataNascita.isEmpty() ? "N/A" : dataNascita, 
                            luogoDomicilio, ruolo
                        ) {};

                        // Se ruolo ristoratore, crea il ristorante
                        if (ruolo.equals("ristoratore")) {
                            Ristorante nuovoRistorante;
                            nuovoRistorante = Ristoratore.menuAggiuntaRistorante(gestioneRistoranti);

                            // Salva ristorante e associazione
                            GestioneFile.scriviRistorante("src/dati/ristoranti.csv", nuovoRistorante);
                            GestioneFile.salvaAssociazioneProprietarioRistorante("proprietari.csv", nuovoUtente.getCodFiscale(), nuovoRistorante);
                        }

                        // Scrittura dell'utente su file
                        GestioneFile.scriviUtente("utente.csv", nuovoUtente);
                        gestioneUtenti.registraUtente(nuovoUtente);
                        System.out.println("\nComplimenti " + ruolo + "! Registrazione completata con successo!");
                        break;
                        
                        /**
                         * Login di un utente esistente.
                         * <p>
                         * Vengono richiesti: <code>user</code> e <code>pass</code>.
                         * Se le credenziali sono corrette, si mostra il menu relativo al ruolo
                         * dell'utente (Cliente o Ristoratore).
                         * </p>
                         */
                        
                    case 2:
                    	
                    	String user="";
                    	do {
                    		System.out.print("Username: ");
                    		user = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.campoNonVuoto(user));
                    	
                    	String pass="";
                    	
                    	do {
                    		System.out.print("Password: ");
                    		pass = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.campoNonVuoto(pass));
                    	
                        pass = GestioneUtenti.cifraPassword(pass);
                        GestioneRecensioni gestioneRecensioni = new GestioneRecensioni();

                        ArrayList<String> utenti = GestioneFile.leggiDaFile("src/dati/utente.csv");
                        Utente loggedUser = null;

                        for (String line : utenti) {
                            String[] dati = GestioneFile.dividereCsv(line);
                            if (dati.length >= 8 && dati[3].equals(user) && dati[4].equals(pass)) {
                                String ruoloUtente = dati[7];
                                if (ruoloUtente.equals("cliente")) {
                                    loggedUser  = new Cliente(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6]);
                                } else if (ruoloUtente.equals("ristoratore")) {
                                    loggedUser  = new Ristoratore(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6], null);
                                }
                                break;
                            }
                        }

                        if (loggedUser == null) {
                            System.out.println("Credenziali errate.");
                        } else if (loggedUser instanceof Cliente) {
                            ((Cliente) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni, scanner);
                        } else if (loggedUser instanceof Ristoratore) {
                            ((Ristoratore) loggedUser).caricaRistoranteAssociato(gestioneRistoranti);
                            ((Ristoratore) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni);
                        }
                        break;

                        /**
                         * Login come guest.
                         * <p>
                         * Viene richiesto di inserire un <code>luogo</code> (città), quindi
                         * si mostrano i ristoranti presenti nella città indicata.
                         * </p>
                         */
                    case 3:
                    	
                    	
                    	String luogo="";
                    	
                    	do {
                    		System.out.println("Inserire una città per continuare: ");
                    		luogo = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.nominativoValido(luogo));
                    	
                    	gestioneRistoranti.menuCercaRistoranti(luogo);
                    	
                   break;	

                    case 4:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Opzione non presente, riprova.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero valido. " + e + " " +e.getMessage());
            	
            } catch (Exception e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }
           
        }while(scelta!=4);
    }
    
    
    
}
    

   
