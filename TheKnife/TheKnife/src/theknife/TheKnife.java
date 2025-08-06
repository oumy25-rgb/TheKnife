/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package theknife;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class TheKnife {

    /**
     * @param args the command line arguments
     */
    // Dichiarazione globale
    static GestioneFile gestioneFile = new GestioneFile();// Creazione dell'istanza di GestioneFile
    static GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO code application logic here
    	boolean controllo; //variabile usata per fare controlli sugli input
        
        GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        GestioneFile gf = new GestioneFile();
        Utente nuovoUtente = null;
        int scelta = 0;
		// Menu iniziale
    	System.out.println("");
        System.out.println("Benvenuto su TheKnife!");
        do {
            try { //l'utente che non effettua il login può:
                System.out.println("1. Registrati"); //registrarsi
                System.out.println("2. Login"); //fare l'accesso
                System.out.println("3. Login come guest"); 
                System.out.println("4. Esci");

                System.out.print("Cosa vuoi fare? : "); // <-- QUI l'input sarà su una nuova riga

                // Gestione dell'input dell'utente
                
               scelta = Integer.parseInt(scanner.nextLine());
               System.out.println();
                
                switch (scelta) {
                    case 1:
                        
                        // Registrazione utente
                        System.out.println("Inserisci il tuo nome:");
                        String nome = scanner.nextLine();

                        System.out.println("Inserisci il tuo cognome:");
                        String cognome = scanner.nextLine();

                        System.out.println("Inserisci il tuo codice fiscale:");
                        String codFiscale = scanner.nextLine();

                        System.out.println("Inserisci un username:");
                        String username = scanner.nextLine();

                        System.out.println("Inserisci una password:");
                        String password = scanner.nextLine();
                        password = Utente.cifraPassword(password);
                        
                        String dataNascita;
                        
                        do {
                        	controllo = false;
                        	System.out.println("Inserisci la tua data di nascita (opzionale - premi invio per saltare) (formato gg/mm/aaaa) :");
                        	dataNascita = scanner.nextLine();
                        	
                        	 if (dataNascita.matches("^\\d{2}/\\d{2}/\\d{4}$")) {   // Controllo formato: due cifre / due cifre / quattro cifre
                        		 controllo = true;
                        	 }else {
                        		 System.out.println("Formato non valido, riprova.");
                        	 }
                        	
                        }while(!controllo);

                        System.out.println("Inserisci il tuo luogo di domicilio:");
                        String luogoDomicilio = scanner.nextLine();

                        System.out.println("Inserisci il tuo ruolo (cliente/ristoratore):");
                        String ruolo = scanner.nextLine().toLowerCase();

                        // Crea l'utente con data di nascita opzionale
                         nuovoUtente = new Utente(
                            nome, cognome, codFiscale, username, password, 
                            dataNascita.isEmpty() ? "N/A" : dataNascita, 
                            luogoDomicilio, ruolo
                        ) {};

                        // Se ruolo ristoratore, crea il ristorante
                        if (ruolo.equals("ristoratore")) {
                            System.out.println("\n--- Inserimento dati ristorante ---");
                            System.out.println("Inserisci il nome del ristorante:");
                            String nomeRistorante = scanner.nextLine();

                            System.out.println("Inserisci l'indirizzo del ristorante:");
                            String indirizzoRistorante = scanner.nextLine();

                            System.out.println("Inserisci città del ristorante:");
                            String cittaRistorante = scanner.nextLine();

                            System.out.println("Inserisci nazione del ristorante:");
                            String nazioneRistorante = scanner.nextLine();

                            System.out.println("Inserisci il tipo di cucina del ristorante:");
                            String tipoCucina = scanner.nextLine();

                            System.out.println("Inserisci il prezzo medio del ristorante (es. 25.25):");
                            String prezzoRistorante = scanner.nextLine().replace("€", "").trim();

                            // Controllo per il servizio delivery
                            String delivery;

                            do {
                                controllo = false;
                                System.out.println("Inserisci opzione di servizio delivery (true/false): ");
                                delivery = scanner.nextLine();
                                if (delivery.equalsIgnoreCase("true") || delivery.equalsIgnoreCase("false")) {
                                    controllo = true;
                                } else {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                            } while (!controllo);

                            // Controllo per la prenotazione online
                            String prenotazione;
                            do {
                                controllo = false;
                                System.out.println("Inserisci opzione di prenotazione online (true/false): ");
                                prenotazione = scanner.nextLine();
                                if (prenotazione.equalsIgnoreCase("true") || prenotazione.equalsIgnoreCase("false")) {
                                    controllo = true;
                                } else {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                            } while (!controllo);
                            
                            System.out.print("Longitudine: ");
                            String longi = scanner.nextLine();
                            longi = longi.replace(",", ".").trim(); // se per caso mettono ',' al posto del '.' viene sostituita e tolti spazi
                            
                            System.out.print("Latitudine: ");
                            String lati = scanner.nextLine();
                            lati = lati.replace(",", ".").trim();
                            

                            // Creazione e salvataggio ristorante
                            Ristorante nuovoRistorante = new Ristorante(
                                nomeRistorante, indirizzoRistorante, cittaRistorante, 
                                prezzoRistorante, nazioneRistorante, tipoCucina, 
                                Double.parseDouble(longi), Double.parseDouble(lati), Boolean.parseBoolean(delivery), Boolean.parseBoolean(prenotazione), new ArrayList<>()
                            );

                            // Salva ristorante e associazione
                            gf.scriviRistorante("ristoranti.csv", nuovoRistorante);
                            gf.salvaAssociazioneProprietarioRistorante("proprietari.csv", nuovoUtente.getCodFiscale(), nuovoRistorante);
                        }

                        // Scrittura dell'utente su file
                        gf.scriviUtente("utente.csv", nuovoUtente);
                        gestioneUtenti.registraUtente(nuovoUtente);
                        System.out.println("\nComplimenti " + ruolo + "! Registrazione completata con successo!");
                        break;
                    case 2:
                    
                        System.out.print("Username: ");
                        String user = scanner.nextLine();
                        System.out.print("Password: ");
                        String pass = scanner.nextLine();
                        pass = Utente.cifraPassword(pass);
                        GestioneRecensioni gestioneRecensioni = new GestioneRecensioni();

                        ArrayList<String> utenti = gf.leggiDaFile("src/dati/utente.csv");
                        Utente loggedUser = null;

                        for (String line : utenti) {
                            String[] dati = gf.dividereCsv(line);
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
                            ((Ristoratore) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni, scanner);
                        }
                        break;


                    case 3:
                    	
                    	String luogo;
                    	System.out.println("Inserire una città per continuare: ");
                    	luogo = scanner.nextLine();
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
    

   
