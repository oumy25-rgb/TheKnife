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
                
                
                switch (scelta) {
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
                        	System.out.println("Inserisci un username:");
                        	username = scanner.nextLine().trim();
                        }while(!GestioneUtenti.campoNonVuoto(username));
                        
                        
                        String password="";
                        
                        do {
                        	System.out.println("Inserisci una password:");
                        	 password = scanner.nextLine();
                        	password = GestioneUtenti.cifraPassword(password);
                        }while(!GestioneUtenti.campoNonVuoto(password));	
                        
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
                        }while(!GestioneUtenti.campoNonVuoto(luogoDomicilio));
                        
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
                            nuovoRistorante = Ristoratore.menuAggiuntaRistorante(gestioneRistoranti,nuovoUtente.getCodFiscale());

                            // Salva ristorante e associazione
                            GestioneFile.scriviRistorante("src/dati/ristoranti.csv", nuovoRistorante);
                            gf.salvaAssociazioneProprietarioRistorante("proprietari.csv", nuovoUtente.getCodFiscale(), nuovoRistorante);
                        }

                        // Scrittura dell'utente su file
                        gf.scriviUtente("utente.csv", nuovoUtente);
                        gestioneUtenti.registraUtente(nuovoUtente);
                        System.out.println("\nComplimenti " + ruolo + "! Registrazione completata con successo!");
                        break;
                    case 2:
                    	
                    	String user="";
                    	do {
                    		System.out.print("Username: ");
                    		user = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.campoNonVuoto(user));
                    	
                    	String pass="";
                    	
                    	do {
                    		System.out.print("Password: ");
                    		pass = scanner.nextLine();
                    	}while(!GestioneUtenti.campoNonVuoto(pass));
                    	
                        pass = GestioneUtenti.cifraPassword(pass);
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
                            ((Ristoratore) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni);
                        }
                        break;


                    case 3:
                    	
                    	String luogo="";
                    	
                    	do {
                    		System.out.println("Inserire una città per continuare: ");
                    		luogo = scanner.nextLine().trim();
                    	}while(!GestioneUtenti.campoNonVuoto(luogo));
                    	
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
    

   
