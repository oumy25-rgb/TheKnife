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
    	boolean controllo = false; //variabile usata per fare controlli sugli input
        
        GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
        GestioneUtenti gestioneUtenti = new GestioneUtenti();
        GestioneFile gf = new GestioneFile();
        Utente nuovoUtente = null;
        int scelta = 0;
		// Menu iniziale

        do {
            try {
            	System.out.println("");
                System.out.println("1. Registrati");
                System.out.println("2. Login");
                System.out.println("3. Cerca Ristoranti");
                System.out.println("4. Accedi come Guest");
                System.out.println("5. Esci");

                System.out.print("Scelta: "); // <-- QUI l'input sarà su una nuova riga

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
                        String password = scanner.nextLine(); // In un'applicazione reale, cifrare la password

                        System.out.println("Inserisci la tua data di nascita (opzionale):");
                        String dataNascita = scanner.nextLine();

                        System.out.println("Inserisci il tuo luogo di domicilio:");
                        String luogoDomicilio = scanner.nextLine();

                        System.out.println("Inserisci il tuo ruolo (cliente/ristoratore):");
                        String ruolo = scanner.nextLine();

                        nuovoUtente = new Utente(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, ruolo) {};

                        // Se ruolo ristoratore far inserire i dati del ristorante
                        if (ruolo.equals("ristoratore")) {
                        	
                            System.out.println("Inserisci il nome del ristorante:");
                            String nomeRistorante = scanner.nextLine();
                            
                            System.out.println("Inserisci l'indirizzo del ristorante");
                            String indirizzoRistorante = scanner.nextLine();
                
                            System.out.println("Inserisci città del ristorante");
                            String citta = scanner.nextLine();
                            
                            System.out.println("Inserisci nazione del ristorante");
                            String nazione = scanner.nextLine();
                            
                            System.out.println("Inserisci il tipo di cucina del ristorante:");
                            String tipoCucina = scanner.nextLine();
                            
                            System.out.println("Inserisci il prezzo medio del ristorante (es. €25.25):");
                            String prezzoRistorante = scanner.nextLine().replace("€", "").trim(); // Rimuovi simboli e spazi
                            
                            boolean delivery = false;
                            
                            do {
                            	controllo=false;
                            	System.out.println("Inserisci opzione di servizio delivery (true/false) : ");
	                            
	                            try {
	                            	
	                            	delivery = scanner.nextBoolean();
	                            	controllo = true;
	                            
	                            }catch(InputMismatchException e) { //se l'input non è true o false viene lanciata
	                        		
	                        		System.out.println("Hai inserito un valore non valido, riprova.");
	                        		scanner.nextLine(); // pulisce il buffer
	                        	}
                            
                            }while(!controllo);
                            	
                            boolean prenotazione = false;
                            
                            do {
                            	controllo=false;
                            	
                            	System.out.println("Inserisci opzione di prenotazione online (true/false) : ");
                            	
                            	try {
                            		
                            		prenotazione = scanner.nextBoolean();
                            		controllo = true;
                            		
                            	}catch(InputMismatchException e) {
                            		
                            		System.out.println("Hai inserito un valore non valido, riprova.");
                            		scanner.nextLine(); // pulisce il buffer
                            	}
                            	
                            }while(!controllo);
                            
                            Ristorante nuovoRistorante = new Ristorante(nomeRistorante, indirizzoRistorante, luogoDomicilio, prezzoRistorante, nazione,  tipoCucina, 157.25, 124.36, delivery, prenotazione,new ArrayList<Recensione>());

                            gf.scriviRistorante("ristoranti.csv", nuovoRistorante);
                            Ristorante ristoranteAssociato = new Ristorante(nomeRistorante, "Via Roma 1", "Roma", "$$", "italia","Italiana", 12.345678, 98.765432,true,true,new ArrayList<>());

                            // Salvataggio dell'associazione
                            gf.salvaAssociazioneProprietarioRistorante("proprietari.csv", nuovoUtente.getCodFiscale(), ristoranteAssociato);
                            gf.scriviPreferiti("preferiti.csv", new Preferiti(nuovoUtente.getCodFiscale(), nomeRistorante));
                        }

                        // Scrittura dell'utente su file
                        gf.scriviUtente("utente.csv", nuovoUtente);
                        gestioneUtenti.registraUtente(nuovoUtente);
                        System.out.println("Complimenti " + ruolo + "! Ti sei registrato con successo!");
                        break;

                    case 2:
                    
                        System.out.print("Username: ");
                        String user = scanner.nextLine();
                        System.out.print("Password: ");
                        String pass = scanner.nextLine();
                        GestioneRecensioni gestioneRecensioni = new GestioneRecensioni();

                        ArrayList<String> utenti = gf.leggiDaFile("src/dati/utente.csv");
                        Utente loggedUser = null;

                        for (String line : utenti) {
                            String[] dati = gf.dividereCsv(line);
                            if (dati.length >= 8 && dati[3].equals(user) && dati[4].equals(pass)) {
                                String ruoloUtente = dati[7];
                                if (ruoloUtente.equals("cliente")) {
                                    loggedUser = new Cliente(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6]);
                                } else if (ruoloUtente.equals("ristoratore")) {
                                    loggedUser = new Ristoratore(dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6], null);
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
                    	String sceltaCriterio;
                    	String tipo,citta;
                    	ArrayList<Ristorante> listaRistorantiTrovati = null;
                    	
                    	System.out.println("Seleziona i criteri di ricerca dei ristoranti: ");
                    	System.out.println("1) Per tipo di cucina e città");
                    	System.out.println("2) città");
                    	System.out.println("3) Per fascia di prezzo e città");
                    	System.out.println("4) Disponibilità Delivery e città");
                    	System.out.println("5) Disponibilità Prenotazione Online e città");
                    	System.out.println("6) Per media stelle e città");
                    	System.out.println("7) Per tutti i criteri");
                    	System.out.println("8) Esci");
                    	
                    	sceltaCriterio = scanner.nextLine();
                    	
                    	
                    	switch(Integer.parseInt(sceltaCriterio)) {
                    	
                    	case 1:
                    		
                    		System.out.println("Inserisci il tipo di cucina: ");
                    		tipo = scanner.nextLine();
                    		
                    		System.out.println("Inserisci la città: ");
                    		citta = scanner.nextLine();
                    		
                    		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(tipo,citta);
                    		
                    		stampaListaRicerca(listaRistorantiTrovati);
                    		
                    		break;
                    		
                    	case 2:
                    		
                    		System.out.println("Inserisci la città: ");
                    		citta = scanner.nextLine();
                    		
                    		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(citta);
                    		stampaListaRicerca(listaRistorantiTrovati);
                    		
                    		break;
                    		
                    	 case 3:
                    		 
                    		 double fasciaPrezzoMin = 0,fasciaPrezzoMax = 0;
                    		 
                    		 System.out.println("Inserisci la città: ");
                     		 citta = scanner.nextLine();
                     		 
                     		do {
                            	controllo=false;
                                System.out.println("Inserisci fascia di prezzo minima:");

                                try {
                                    fasciaPrezzoMin = Double.parseDouble(scanner.nextLine());
                                    controllo = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }

                            } while (!controllo);
                            
                            do {
                                controllo = false;
                                System.out.println("Inserisci fascia di prezzo massima:");
                                try {
                                    fasciaPrezzoMax = Double.parseDouble(scanner.nextLine());
                                    controllo = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                                
                            } while (!controllo);
                     		
                            listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(citta,fasciaPrezzoMin,fasciaPrezzoMax);
                            stampaListaRicerca(listaRistorantiTrovati);
                    		 
                    		 break;
                    		 
                    	 case 4:
                    		 
                    		 boolean delivery = false;
                    		 System.out.println("Inserisci la città: ");
                     		 citta = scanner.nextLine();

                     		do {
                            	controllo=false;
                                System.out.println("Richiedi servizio di delivery? (true/false):");
                                try {
                                    delivery = scanner.nextBoolean();
                                    scanner.nextLine();
                                    controllo = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                    scanner.nextLine(); // pulisce il buffer
                                }
                            } while (!controllo);
                     		
                     		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(citta,delivery);
                     		stampaListaRicerca(listaRistorantiTrovati);
                    		 
                    		 break;
                    		 
                    	 case 5:
                  
                    		 boolean prenotazione = false;
                    		 System.out.println("Inserisci la città: ");
                     		 citta = scanner.nextLine();
                     		 
                     		do {
                            	controllo=false;
                                System.out.println("Richiedi prenotazione online? (true/false):");
                                try {
                                    prenotazione = scanner.nextBoolean();
                                    scanner.nextLine();
                                    controllo = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                    scanner.nextLine(); // pulisce il buffer
                                }
                            } while (!controllo);
                     		
                     		
                     		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(prenotazione,citta);
                     		stampaListaRicerca(listaRistorantiTrovati);
                    		 
                    		 break;
                    		 
                    	 case 6: 
                    		 
                    		 double mediaStelle = 0;
                    		 System.out.println("Inserisci la città: ");
                     		 citta = scanner.nextLine();
                     		 
                     		do {
                            	controllo = false;
                                System.out.println("Inserisci media delle stelle:");
                                try {
                                    mediaStelle = Double.parseDouble(scanner.nextLine());
                                    if (mediaStelle >= 1 && mediaStelle <= 5) {
                                        controllo = true;
                                    } else {
                                        System.out.println("Numero fuori dal range 1-5, riprova.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                            } while (!controllo);
                     		
                     		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(citta,mediaStelle);
                     		stampaListaRicerca(listaRistorantiTrovati);
                    		 
                    		 break;
                    		 
                    	 case 7: 
                    		 
                    		 delivery = false;
                    		 prenotazione = false;
                    		 mediaStelle = 0; 
                    		 fasciaPrezzoMin=0;
                    		 fasciaPrezzoMax=0;
                    		 
                    		 
                     		System.out.println("Inserisci la città: ");
                     		citta = scanner.nextLine();
                     		
                     		System.out.println("Inserisci il tipo di cucina: ");
                      		tipo = scanner.nextLine();
                      		
                      		do {
                            	controllo=false;
                                System.out.println("Inserisci fascia di prezzo minima:");

                                try {
                                    fasciaPrezzoMin = Double.parseDouble(scanner.nextLine());
                                    controllo = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }

                            } while (!controllo);
                            
                            do {
                                controllo = false;
                                System.out.println("Inserisci fascia di prezzo massima:");
                                try {
                                    fasciaPrezzoMax = Double.parseDouble(scanner.nextLine());
                                    controllo = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                                
                            } while (!controllo);
                            
                            do {
                            	controllo=false;
                                System.out.println("Richiedi servizio di delivery? (true/false):");
                                try {
                                    delivery = scanner.nextBoolean();
                                    controllo = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                    scanner.nextLine(); // pulisce il buffer
                                }
                            } while (!controllo);
                     		
                    		 
                    		do {
                            	controllo=false;
                                System.out.println("Richiedi prenotazione online? (true/false):");
                                try {
                                    prenotazione = scanner.nextBoolean();
                                    controllo = true;
                                } catch (InputMismatchException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                    scanner.nextLine(); // pulisce il buffer
                                }
                            } while (!controllo);
                    		
                    		do {
                            	controllo = false;
                                System.out.println("Inserisci media delle stelle:");
                                try {
                                    mediaStelle = Double.parseDouble(scanner.nextLine());
                                    if (mediaStelle >= 1 && mediaStelle <= 5) {
                                        controllo = true;
                                    } else {
                                        System.out.println("Numero fuori dal range 1-5, riprova.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Hai inserito un valore non valido, riprova.");
                                }
                            } while (!controllo);
                    		
                    		
                    		listaRistorantiTrovati = gestioneRistoranti.cercaRistoranti(citta,tipo,fasciaPrezzoMin,fasciaPrezzoMax,delivery,prenotazione,mediaStelle);
                    		stampaListaRicerca(listaRistorantiTrovati);

                    		 break;
                    		 
                    	 case 8:
                    		 break;
                    		 
                    		 default : 
                    			 System.out.println("Opzione non presente.");
                    		 
                    	}
                    	
                   break;	
           
                    case 4:
                        // Accesso come guest
                    	
                        System.out.println("Inserisci il nome di una città (lascia vuoto per ignorare):");
                        String luogo = scanner.nextLine();
                        if (!luogo.isEmpty()) {
                            System.out.println("Hai scelto di cercare ristoranti a: " + luogo);
                            ArrayList<Ristorante> risultatiGuest = gestioneRistoranti.cercaRistoranti("", luogo, 0.0, Double.MAX_VALUE, false, false, 0);
                            if (risultatiGuest.isEmpty()) {
                                System.out.println("Nessun ristorante trovato nella località: " + luogo);
                            } else {
                                System.out.println("Ristoranti trovati a " + luogo + ":");
                                for (Ristorante r : risultatiGuest) {
                                    System.out.printf("- %s | Cucina: %s | Prezzo: %s | Stelle: %.2f%n",
                                            r.getName(), r.getCuisine(), r.getPrice(), r.getMediaStelle());
                                }
                            }
                        } else {
                            System.out.println("Nessun luogo inserito.");
                        }
                        break;

                    case 5:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Scelta non valida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero valido. " + e + " " +e.getMessage());
            	
            } catch (Exception e) {
                System.out.println("Si è verificato un errore: " + e.getMessage());
            }
        }while(scelta!=5);
    }
    
    static void stampaListaRicerca(ArrayList<Ristorante> listaRistorantiTrovati) {
    	
    	String miniMenu;
    	int scegli,i=1;
    	
    	if(!listaRistorantiTrovati.isEmpty()) {
			System.out.println("Lista ristoranti trovati: \n");
			System.out.println("----------------------------------------------------------------------");
			for(Ristorante r : listaRistorantiTrovati) {
				System.out.print((i++)+") "+r.getName()+"\n");
				System.out.println("----------------------------------------------------------------------");
			}

			do {
				System.out.print("\nCosa vuoi fare ora? \n");
				System.out.println("a) Visualizza le informazioni del ristorante");
				System.out.println("b) Visualizza le recensioni del ristorante");
				System.out.println("c) Esci");
				
				miniMenu = scanner.nextLine();
				
    			switch(miniMenu) {
        			case "a":
        				
        				System.out.print ("Di quale ristorante vuoi visualizzare le informazioni? ");
        				scegli = Integer.parseInt(scanner.nextLine());
        				System.out.println("");
        				listaRistorantiTrovati.get(scegli-1).visualizzaRistorante();
        				
        				break;
        				
        			case "b": 
        				System.out.print("Di quale ristorante vuoi visualizzare le recensioni? ");
        				scegli = Integer.parseInt(scanner.nextLine());
        				System.out.println("");
        				listaRistorantiTrovati.get(scegli-1).visualizzaRecensioni();
        				
        				break;
        				
        			case "c":
        				break;
        				
        			default : 
        				System.out.println("Opzione inesistente.");
    			}
			}while(!miniMenu.equals("c"));
			
		}else
			System.out.println("Nessun ristorante trovato con questo criterio.");
    	
    }

}
    

   