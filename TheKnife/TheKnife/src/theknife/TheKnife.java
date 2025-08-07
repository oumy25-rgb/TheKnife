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
                    	String nome="";
                    	
                    	do {
                    		System.out.println("Inserisci il tuo nome:");
                    		nome = scanner.nextLine();
                    	}while(!GestioneUtenti.nominativoValido(nome));
                        
                        String cognome="";
                        do {
                        	System.out.println("Inserisci il tuo cognome:");
                        	cognome = scanner.nextLine();
                        }while(!GestioneUtenti.nominativoValido(cognome));
                        
                        String codFiscale="";
                       
                        do {
                        	System.out.println("Inserisci il tuo codice fiscale:");
                        	codFiscale = scanner.nextLine();
                        }while(!GestioneUtenti.campoNonVuoto(codFiscale) || !GestioneUtenti.formatoValido(codFiscale,"^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$"));  
                        // Regex: 6 lettere, 2 numeri, 1 lettera, 2 numeri, 1 lettera, 3 numeri o lettere
                        
                        String username = "";
                        do {
                        	System.out.println("Inserisci un username:");
                        	username = scanner.nextLine();
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
                        	dataNascita = scanner.nextLine();
                        	 // Controllo formato: due cifre / due cifre / quattro cifre
                        	
                        	if(!dataNascita.isEmpty()) {
                        		if(!GestioneUtenti.formatoValido(dataNascita, "^\\d{2}/\\d{2}/\\d{4}$"))
                        			controllo = false;
                        	}
                        	
                        }while(!controllo);

                        
                        String luogoDomicilio = "";
                        
                        do {
                        	System.out.println("Inserisci il tuo luogo di domicilio:");
                        	luogoDomicilio = scanner.nextLine();
                        }while(!GestioneUtenti.campoNonVuoto(luogoDomicilio));
                        
                        String ruolo ="";
                        
                        do {
                        	controllo = true;
                        	System.out.println("Inserisci il tuo ruolo (cliente/ristoratore):");
                        	ruolo = scanner.nextLine().toLowerCase();
                        	
                        	if(!ruolo.equalsIgnoreCase("cliente") && !ruolo.equalsIgnoreCase("ristoratore")) {
                        		controllo = false;
                        		System.out.println("Ruolo inesistente, riprova");
                        	}
                        }while(!controllo || !GestioneUtenti.campoNonVuoto(ruolo));

                        // Crea l'utente con data di nascita opzionale
                         nuovoUtente = new Utente(
                            nome, cognome, codFiscale, username, password, 
                            dataNascita.isEmpty() ? "N/A" : dataNascita, 
                            luogoDomicilio, ruolo
                        ) {};

                        // Se ruolo ristoratore, crea il ristorante
                        if (ruolo.equals("ristoratore")) {
                            System.out.println("\n--- Inserimento dati ristorante ---");
                            
                            String nomeRistorante="";
                            
							do {
                            	
                            	System.out.println("Inserisci il nome del ristorante:");
                            	nomeRistorante = scanner.nextLine();
                            	
                            }while(!GestioneUtenti.campoNonVuoto(nomeRistorante));
                            
							String indirizzoRistorante ="";
							do {
								System.out.println("Inserisci l'indirizzo del ristorante:");
								indirizzoRistorante = scanner.nextLine();
							 }while(!GestioneUtenti.campoNonVuoto(indirizzoRistorante));
							
							String cittaRistorante="";
							do {
								System.out.println("Inserisci città del ristorante:");
								 cittaRistorante = scanner.nextLine();
							}while(!GestioneUtenti.campoNonVuoto(cittaRistorante));
							
							String nazioneRistorante ="";
							do {
								System.out.println("Inserisci nazione del ristorante:");
								nazioneRistorante = scanner.nextLine();
							}while(!GestioneUtenti.campoNonVuoto(nazioneRistorante));

							String tipoCucina="";
							do {
								System.out.println("Inserisci il tipo di cucina del ristorante:");
								tipoCucina = scanner.nextLine();
							}while(!GestioneUtenti.campoNonVuoto(tipoCucina));
							
							String prezzoRistorante="";
							
							do {
								System.out.println("Inserisci il prezzo medio del ristorante (es. 25.25):");
								prezzoRistorante = scanner.nextLine().replace("€", "").trim();
							}while(!GestioneUtenti.campoNonVuoto(prezzoRistorante));
							
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
                            } while (!GestioneUtenti.campoNonVuoto(delivery) || !controllo );

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
                            } while (!GestioneUtenti.campoNonVuoto(prenotazione) || !controllo);
                            
                            String longi, lati;
                            
                            do {
                                System.out.print("Longitudine: ");
                                longi = scanner.nextLine();
                                longi = longi.replace(",", ".").trim();
                                if (!GestioneUtenti.isLongitudineValida(longi)) {
                                    System.out.println("Valore non valido. Inserisci una longitudine tra -180 e 180.");
                                }
                            } while (!GestioneUtenti.campoNonVuoto(longi) || !GestioneUtenti.isLongitudineValida(longi));

                            do {
                                System.out.print("Latitudine: ");
                                lati = scanner.nextLine();
                                lati = lati.replace(",", ".").trim();
                                if (!GestioneUtenti.isLatitudineValida(lati)) {
                                    System.out.println("Valore non valido. Inserisci una latitudine tra -90 e 90.");
                                }
                            } while (!GestioneUtenti.campoNonVuoto(lati) || !GestioneUtenti.isLatitudineValida(lati));
                            

                            // Creazione e salvataggio ristorante
                            Ristorante nuovoRistorante = new Ristorante(
                                nomeRistorante, indirizzoRistorante, cittaRistorante, 
                                prezzoRistorante, nazioneRistorante, tipoCucina, 
                                Double.parseDouble(longi), Double.parseDouble(lati), Boolean.parseBoolean(delivery), Boolean.parseBoolean(prenotazione), new ArrayList<>()
                            );

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
                    		user = scanner.nextLine();
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
                            ((Ristoratore) loggedUser).mostraMenu(gestioneRistoranti, gestioneRecensioni, scanner);
                        }
                        break;


                    case 3:
                    	
                    	String luogo="";
                    	
                    	do {
                    		System.out.println("Inserire una città per continuare: ");
                    		luogo = scanner.nextLine();
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
    

   
