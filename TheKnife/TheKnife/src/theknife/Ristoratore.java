/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.util.Scanner;
import java.util.ArrayList;
import resources.Piatto;
import resources.GestioneFile;
import static theknife.Recensione.riscriviRecensioni;

public class Ristoratore extends Utente {
    private Ristorante ristorante;
    public static Scanner scanner = new Scanner(System.in);
    public Ristoratore(String nome, String cognome, String codFiscale, String username, String password,
                       String dataNascita, String luogoDomicilio, Ristorante ristorante) {
        super(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, "ristoratore");
        this.ristorante = ristorante;
    }

    public void caricaRistoranteAssociato(GestioneRistoranti gestioneRistoranti) {
    if (this.ristorante != null) {
        return; // Già associato
    }
    
    String nomeRistorante = GestioneFile.cercaRistoranteDaProprietario("src/dati/proprietari.csv", this.getCodFiscale());
    if (nomeRistorante != null) {
        Ristorante r = gestioneRistoranti.cercaRistorantePerNome(nomeRistorante);
        if (r != null) {
            this.ristorante = r;
            System.out.println("Ristorante associato caricato: " + ristorante.getName());
        }
    }
}


    public void mostraMenu(GestioneRistoranti gestioneRistoranti, GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        int scelta;
        do {
            System.out.println("===== MENU RISTORATORE =====");
            System.out.println("1. Aggiungi un ristorante");
            System.out.println("2. Visualizza riepilogo delle recensioni");
            System.out.println("3. Visualizza recensioni");
            System.out.println("4. Rispondi a una recensione");
            System.out.println("5. Crea il menu del ristorante");
            System.out.println("6. Aggiungi un piatto al menu");
            System.out.println("7. Rimuovi un piatto dal menu");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    aggiungiRistorante(gestioneRistoranti);
                    break;
                case 2:
                    visualizzaRiepilogo();
                    break;
                case 3:
                    visualizzaRecensioni();
                    break;
                case 4:
                    rispondiARecensione(gestioneRecensioni, scanner);
                    break;
                case 5:  
                	String nomeRistorante="";
                    do {
                    	System.out.print("Inserisci il nome del ristorante per cui vuoi creare il menu: ");
                    	nomeRistorante = scanner.nextLine().trim();
                    }while(!GestioneUtenti.campoNonVuoto(nomeRistorante));
                    gestioneRistoranti.creaEMenuRistorante(nomeRistorante);
                    break;
                case 6:
                	String nomePiatto="";
                	do {
                		System.out.print("Nome del piatto: ");
                		nomePiatto = scanner.nextLine();
                	}while(!GestioneUtenti.campoNonVuoto(nomePiatto));
                	
                	String descrizionePiatto="";
                	do {
	                    System.out.print("Descrizione del piatto: ");
	                    descrizionePiatto = scanner.nextLine();
                	}while(!GestioneUtenti.campoNonVuoto(descrizionePiatto));
                	
				boolean controllo;
				double prezzoPiatto = 0.0;
				
					do {
						try {
	                		controllo = true;
	                		System.out.print("Prezzo del piatto: ");
	                		prezzoPiatto = Double.parseDouble(scanner.nextLine());
						}catch(NumberFormatException e) {
							System.out.println("Valore inserito non valido, riprova.");
							controllo=false;
						}
						
                	}while(!controllo);
                	
                    Piatto nuovoPiatto = new Piatto(nomePiatto, descrizionePiatto, prezzoPiatto);
                    ristorante.aggiungiPiatto(nuovoPiatto);
                    break;
                case 7:
                	String piattoDaRimuovere="";
                	do {
                		System.out.print("Nome del piatto da rimuovere: ");
                		piattoDaRimuovere = scanner.nextLine();
                	}while(!GestioneUtenti.campoNonVuoto(piattoDaRimuovere));
                	
                    ristorante.rimuoviPiatto(piattoDaRimuovere);
                    break;
                case 0:
                    System.out.println("Logout effettuato.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (scelta != 0);
    }

   private void aggiungiRistorante(GestioneRistoranti gestioneRistoranti) {
    // Aggiungi il ristorante al sistema
    gestioneRistoranti.aggiungiRistorante(menuAggiuntaRistorante(gestioneRistoranti,this.getCodFiscale()), this.getCodFiscale());
    System.out.println("Ristorante aggiunto e associato con successo!");
}
   
   
	public static Ristorante menuAggiuntaRistorante(GestioneRistoranti gestioneRistoranti,String cf) {
		
		Ristorante nuovoRistorante;
		System.out.println("Inserisci i dettagli del ristorante:");
	    boolean controllo;
	    String nome="";
	    
	    do {
	    	controllo = true;
	    	
	    	System.out.println("Inserisci il nome del ristorante:");
	    	 nome = scanner.nextLine();
	    	if(GestioneUtenti.campoNonVuoto(nome)) {
		    	if(gestioneRistoranti.verificaEsistenzaRistorantePerRistoratore(cf, nome)) {
		    		controllo=false;
		    		System.out.println("il ristorante '"+nome+"' esiste già per questo ristoratore! riprova.");
		    	}
	    	}else
	    		controllo=false;
	    }while(!controllo);
	    
	    String address="";
	    do {
	    	System.out.print("Inserisci l'indirizzo del ristorante:");
	    	address = scanner.nextLine();
	    }while(!GestioneUtenti.campoNonVuoto(address));
	    
	    String city="";
	    
	    do {
		    System.out.print("Inserisci città del ristorante:");
		    city = scanner.nextLine();
	    }while(!GestioneUtenti.campoNonVuoto(address));
	    
	    String nation="";
	    
	    do {
	    	System.out.print("Inserisci nazione del ristorante:");
	    	nation = scanner.nextLine();
	    }while(!GestioneUtenti.campoNonVuoto(nation));
	    
	    String price="";
	    do {
			controllo = true;
			System.out.println("Inserisci il prezzo medio del ristorante (es. 25.25):");
			price = scanner.nextLine().replace("€", "").trim();
			price = price.replace(",", ".");
			
			if(GestioneUtenti.campoNonVuoto(price)) {
				try {
			        Double.parseDouble(price);
			    } catch (NumberFormatException e) {
			        System.out.println("Devi inserire un numero valido, riprova.");
			        controllo = false;
			    }
			}else
				controllo = false;
			
		}while(!controllo);
	    
	    String cuisine="";
	    do {
	    	System.out.print("Inserisci il tipo di cucina del ristorante: ");
	    	cuisine = scanner.nextLine();
	    }while(!GestioneUtenti.campoNonVuoto(cuisine));
	    
	    String delivery;

	    do {
	        controllo = false;
	        System.out.println("Inserisci opzione di servizio delivery (true/false): ");
	        delivery = scanner.nextLine();
	        
	        if(GestioneUtenti.campoNonVuoto(delivery)) {
	            if (delivery.equalsIgnoreCase("true") || delivery.equalsIgnoreCase("false")) {
	                controllo = true;
	            } else {
	                System.out.println("Hai inserito un valore non valido, riprova.");
	            }
	        }
	        
	    } while (!controllo );

	    String prenotazione;
	    do {
	        controllo = false;
	        System.out.println("Inserisci opzione di prenotazione online (true/false): ");
	        prenotazione = scanner.nextLine();
	        
	        if(GestioneUtenti.campoNonVuoto(prenotazione)) {
	            if (prenotazione.equalsIgnoreCase("true") || prenotazione.equalsIgnoreCase("false")) {
	                controllo = true;
	            } else {
	                System.out.println("Hai inserito un valore non valido, riprova.");
	            }
	        }
	        
	    } while (!controllo);
	    
	    String longi ="";
	    do {
	        System.out.print("Inserisci Longitudine : ");
	        longi = scanner.nextLine();
	        longi = longi.replace(",", ".").trim();
	        if (!GestioneUtenti.isLongitudineValida(longi)) {
	            System.out.println("Valore non valido. Inserisci una longitudine tra -180 e 180.");
	        }
	    } while (!GestioneUtenti.isLongitudineValida(longi)); //il metodo controlla già che non sia vuota 

	    String lati="";
	    do {
	        System.out.print("Inserisci Latitudine: ");
	        lati = scanner.nextLine();
	        lati = lati.replace(",", ".").trim();
	        if (!GestioneUtenti.isLatitudineValida(lati)) {
	            System.out.println("Valore non valido. Inserisci una latitudine tra -90 e 90.");
	        }
	    } while (!GestioneUtenti.isLatitudineValida(lati)); //il metodo controlla già che non sia vuota
	    
	    return  nuovoRistorante = new Ristorante(
	            nome, address, city, 
	            price, nation, cuisine, 
	            Double.parseDouble(longi), Double.parseDouble(lati), Boolean.parseBoolean(delivery), Boolean.parseBoolean(prenotazione), new ArrayList<>()
	        );
	}
	
	
    private void visualizzaRiepilogo() {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        ristorante.visualizzaRiepilogo();
    }

    private void visualizzaRecensioni() {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        ristorante.visualizzaRecensioni();
    }

    private void rispondiARecensione(GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }

        ArrayList<Recensione> recensioni = Recensione.cercaPerRistorante(ristorante.getName());

        if (recensioni.isEmpty()) {
            System.out.println("Nessuna recensione trovata per questo ristorante.");
            return;
        }

        for (int i = 0; i < recensioni.size(); i++) {
            Recensione rec = recensioni.get(i);
            String nomeCliente = GestioneFile.getNomeDaCodFiscale("src/dati/utente.csv", rec.getCliente());
            System.out.println("[" + (i + 1) + "] " + nomeCliente + ": " + rec.getTestoRecensione());
            if (rec.getRisposta() != null && !rec.getRisposta().isEmpty()) {
                System.out.println("Risposta proprietario: " + rec.getRisposta());
            }
        }

        System.out.print("A quale recensione vuoi rispondere? ");
        int scelta = Integer.parseInt(scanner.nextLine()) - 1;

        if (scelta >= 0 && scelta < recensioni.size()) {
            Recensione selezionata = recensioni.get(scelta);
            String nomeCliente = GestioneFile.getNomeDaCodFiscale("src/dati/utente.csv", selezionata.getCliente());
            System.out.println("Rispondi al commento di " + nomeCliente + ":");
            String risposta = scanner.nextLine();

            gestioneRecensioni.rispondiARisposta(ristorante.getName(), selezionata.getCliente(), risposta);
            System.out.println("Risposta inviata correttamente.");
        } else {
            System.out.println("Scelta non valida.");
        }
    }
    
    public void modificaRecensione(Cliente cliente, Scanner scanner) {
    ArrayList<Recensione> tutteRecensioni = Recensione.leggiTutteLeRecensioni();
    
    System.out.print("Nome ristorante: ");
    String nomeRistorante = scanner.nextLine();

    for (Recensione rec : tutteRecensioni) {
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(nomeRistorante)) {
            System.out.print("Nuovo testo: ");
            String nuovoTesto = scanner.nextLine();
            System.out.print("Nuovo voto (1-5): ");
            int nuoveStelle = Integer.parseInt(scanner.nextLine());

            rec.setTestoRecensione(nuovoTesto);
            rec.setStelle(nuoveStelle);
            riscriviRecensioni(tutteRecensioni);
            System.out.println("Recensione aggiornata.");
            return; // Esci dopo aver trovato e modificato la recensione
        }
    }
    System.out.println("Recensione non trovata.");
}


    private void aggiungiPiatto(Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        System.out.print("Nome del piatto: ");
        String nomePiatto = scanner.nextLine();
        System.out.print("Descrizione del piatto: ");
        String descrizionePiatto = scanner.nextLine();
        System.out.print("Prezzo del piatto: ");
        double prezzoPiatto = Double.parseDouble(scanner.nextLine());

        Piatto nuovoPiatto = new Piatto(nomePiatto, descrizionePiatto, prezzoPiatto);
        ristorante.aggiungiPiatto(nuovoPiatto);
    }

    private void rimuoviPiatto(Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        System.out.print("Nome del piatto da rimuovere: ");
        String nomePiatto = scanner.nextLine();
        ristorante.rimuoviPiatto(nomePiatto);
    }
}
