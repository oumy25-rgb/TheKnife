
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;

import resources.GestioneFile;
import resources.GestioneMenu;
import resources.Piatto;

public class GestioneRistoranti {
    private ArrayList<Ristorante> ristoranti;
    private GestioneFile gf;

    public GestioneRistoranti() {
        ristoranti = new ArrayList<>();
        gf = new GestioneFile();
       // caricaRistoranti(); // Carica i ristoranti all'inizializzazione
    }

    public void aggiungiRistorante(Ristorante ristorante, String codFiscale) {
    if (ristorante != null) {
        ristoranti.add(ristorante);
        // Scrivi nel file proprietari.csv
        GestioneFile.aggiungiRistoranteProprietario("src/dati/proprietari.csv", codFiscale, ristorante.getName());
        
        // Scrivi nel file ristoranti.csv con percorso corretto
        GestioneFile.scriviRistorante("src/dati/ristoranti.csv", ristorante);
    } else {
        System.out.println("Errore: il ristorante non può essere nullo.");
    }
}
    
    
    public ArrayList<Ristorante> cercaRistoranti(String tipo,String citta) {
    	
    	ArrayList<Ristorante> lista = new ArrayList<Ristorante>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
        	String[] riga;
            while ((riga = reader.readNext()) != null) {
                if (citta.equalsIgnoreCase(riga[2]) && tipo.equalsIgnoreCase(riga[5])) {
                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    lista.add(r);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }
    
    
	public ArrayList<Ristorante> cercaRistoranti(String citta,boolean delivery) {
	    	
	    	ArrayList<Ristorante> lista = new ArrayList<Ristorante>();
	    	
	        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
	            String[] riga;
	
	            while ((riga = reader.readNext()) != null) {
	                if (citta.equalsIgnoreCase(riga[2]) && delivery==Boolean.parseBoolean(riga[8])) {
	                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
	                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
	                    Ristorante r = new Ristorante(
	                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
	                    );
	                    
	                    lista.add(r);
	                }
	            }
	
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        } catch (CsvValidationException e) {
	            e.printStackTrace();
	            return null;
	        }
	
	        return lista;
	    }
	    
	public ArrayList<Ristorante> cercaRistoranti(boolean prenotazione,String citta) {
    	
		ArrayList<Ristorante> lista = new ArrayList<Ristorante>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
                if (citta.equalsIgnoreCase(riga[2]) && prenotazione==Boolean.parseBoolean(riga[9])) {
                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    lista.add(r);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }
	    
	    
	public ArrayList<Ristorante> cercaRistoranti(String citta) {
	    	
	    	ArrayList<Ristorante> lista = new ArrayList<>();
	    	
	        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
	            String[] riga;
	
	            while ((riga = reader.readNext()) != null) {
	                if (citta.equalsIgnoreCase(riga[2])) {
	                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
	                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
	                    Ristorante r = new Ristorante(
	                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
	                    );
	                    
	                    lista.add(r);
	                }
	            }
	
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        } catch (CsvValidationException e) {
	            e.printStackTrace();
	            return null;
	        }
	
	        return lista;
	    }


	public ArrayList<Ristorante> cercaRistoranti(String citta,double fasciaPrezzoMin,double fasciaPrezzoMax) {
	    	
	    	ArrayList<Ristorante> lista = new ArrayList<>();
	    	
	        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
	            String[] riga;
	
	            while ((riga = reader.readNext()) != null) {
	                if (citta.equalsIgnoreCase(riga[2]) && (Double.parseDouble(riga[4]) >= fasciaPrezzoMin && Double.parseDouble(riga[4]) <= fasciaPrezzoMax)) {
	                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
	                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
	                    Ristorante r = new Ristorante(
	                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
	                    );
	                    
	                    lista.add(r);
	                }
	            }
	
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        } catch (CsvValidationException e) {
	            e.printStackTrace();
	            return null;
	        }
	
	        return lista;
	    }
	
	
	public ArrayList<Ristorante> cercaRistoranti(String citta,double media) {
    	
    	ArrayList<Ristorante> lista = new ArrayList<>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
            	
                if (citta.equalsIgnoreCase(riga[2])) {
                	
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    if(r.calcoloMediaStelle(r.getName(),r.getCity(),r.getAddress())==media) {
                    
                    	lista.add(r);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }
	
	
	
	
public ArrayList<Ristorante> cercaRistoranti(String citta,String tipo,double fasciaPrezzoMin,double fasciaPrezzoMax,boolean delivery,boolean prenotazione,double media) {
    	
    	ArrayList<Ristorante> lista = new ArrayList<>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
            	
                if (citta.equalsIgnoreCase(riga[2]) && tipo.equalsIgnoreCase(riga[5]) && (Double.parseDouble(riga[4]) >= fasciaPrezzoMin && Double.parseDouble(riga[4]) <= fasciaPrezzoMax)
                		&& delivery == Boolean.parseBoolean(riga[8]) && prenotazione == Boolean.parseBoolean(riga[9])) {
                	
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    if(r.calcoloMediaStelle(r.getName(),r.getCity(),r.getAddress())==media) {
                    
                    	lista.add(r);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }
    
 public void creaEMenuRistorante(String nomeRistorante) {
    Scanner scanner = new Scanner(System.in);
    String risposta;

    do {
        String nome;
        do {
            System.out.print("Nome del piatto: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("❌ Il nome del piatto non può essere vuoto.");
            }
        } while (nome.isEmpty());

        String descrizione;
        do {
            System.out.print("Descrizione del piatto: ");
            descrizione = scanner.nextLine().trim();
            if (descrizione.isEmpty()) {
                System.out.println("❌ La descrizione non può essere vuota.");
            }
        } while (descrizione.isEmpty());

        double prezzo = -1;
        do {
            try {
                System.out.print("Prezzo del piatto: ");
                prezzo = Double.parseDouble(scanner.nextLine().trim());
                if (prezzo < 0) {
                    System.out.println("❌ Il prezzo non può essere negativo.");
                    prezzo = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Valore inserito non valido, riprova.");
            }
        } while (prezzo < 0);

        // Crea il piatto
        Piatto piatto = new Piatto(nome, descrizione, prezzo);

        // Usa il metodo centralizzato per aggiungere piatto e salvare su file
        aggiungiPiattoAlMenu(nomeRistorante, piatto);

        System.out.print("Vuoi aggiungere un altro piatto? (s/n): ");
        risposta = scanner.nextLine().trim();

    } while (risposta.equalsIgnoreCase("s"));

    System.out.println("✅ Menu completato e salvato con successo.");
}
	
   public Ristorante cercaRistorantePerNome(String nome) {
    try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
        String[] riga;
        while ((riga = reader.readNext()) != null) {
            // Assicurati che il nome sia confrontato correttamente
            if (riga.length > 0 && nome.equalsIgnoreCase(riga[0].trim())) {
                return new Ristorante(
                    riga[0], riga[1], riga[2], riga[4], riga[3],
                    riga[5], Double.parseDouble(riga[6]), 
                    Double.parseDouble(riga[7]),
                    Boolean.parseBoolean(riga[8]),
                    Boolean.parseBoolean(riga[9]),
                    null
                );
            }
        }
    } catch (Exception e) {
        System.err.println("Error searching restaurant: " + e.getMessage());
    }
    return null; 
}

	
    public void aggiungiPiattoAlMenu(String nomeRistorante, Piatto piatto) {
    Ristorante ristorante = cercaRistorantePerNome(nomeRistorante);

    if (ristorante != null) {
        // Controlla se il piatto esiste già
        boolean esiste = false;
        for (Piatto p : ristorante.getMenu()) {
            if (p.getNome().equalsIgnoreCase(piatto.getNome())) {
                esiste = true;
                break;
            }
        }

        if (esiste) {
            System.out.println(" Il piatto \"" + piatto.getNome() + "\" è già presente nel menu di " + nomeRistorante);
            return;
        }

        // Aggiunge in memoria
        ristorante.aggiungiPiatto(piatto);

        // Aggiunge al file CSV (creandolo se non esiste)
        String nomeFile = nomeRistorante + "Menu.csv";
        File file = new File("src/dati/" + nomeFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (!file.exists() || file.length() == 0) {
                writer.write("Nome,Descrizione,Prezzo");
                writer.newLine();
            }
            writer.write(piatto.toCSV());
            writer.newLine();
            System.out.println("✅ Piatto aggiunto e menu salvato in " + nomeFile);
        } catch (IOException e) {
            System.err.println(" Errore durante il salvataggio del menu: " + e.getMessage());
        }
    } else {
        System.out.println(" Ristorante \"" + nomeRistorante + "\" non trovato.");
    }
}

   

    // Nuovo metodo per rimuovere un piatto dal menu
    public void rimuoviPiattoDalMenu(String nomeRistorante, String nomePiatto) {
        Ristorante ristorante = cercaRistorantePerNome(nomeRistorante);
        if (ristorante != null) {
            ristorante.rimuoviPiatto(nomePiatto);
            System.out.println("Piatto rimosso dal menu di " + nomeRistorante);
        } else {
            System.out.println("Ristorante non trovato.");
        }
    }
    
	public void stampaListaRicerca(ArrayList<Ristorante> listaRistorantiTrovati) {
	    	
	    	String miniMenu;
	    	int scegli = 0,i=1;
	    	Scanner scanner = new Scanner(System.in);
	    	boolean controllo;
	    	
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
					System.out.println("c) Visualizza menù del ristorante");
					System.out.println("d) Esci");
				
					miniMenu = scanner.nextLine().toLowerCase();
					
	    			switch(miniMenu) {
	        			case "a":

	        				do {
	        				    controllo = true;
	        				    System.out.print("Di quale ristorante vuoi visualizzare le informazioni? ");
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
	        				listaRistorantiTrovati.get(scegli - 1).visualizzaRistorante();
	        				
	        				break;
	        				
	        			case "b": 
	        				
	        				scegli = 0;
	        			    
	        			    do {
	        			        System.out.print("Di quale ristorante vuoi visualizzare le recensioni? ");
	        			        String scegliStr = scanner.nextLine();
	        			        controllo = true;
	        			        try {
	        			            scegli = Integer.parseInt(scegliStr);
	        			            if (scegli < 1 || scegli > listaRistorantiTrovati.size()) {
	        			                System.out.println("Numero non valido, riprova.");
	        			                controllo = false;
	        			            }
	        			        } catch (NumberFormatException e) {
	        			            System.out.println("Inserisci un numero valido.");
	        			            controllo = false;
	        			        }
	        			    } while (!controllo);

	        			    System.out.println("");
	        			    listaRistorantiTrovati.get(scegli - 1).visualizzaRecensioni();
	        				
	        				break;
	        				
	        			case "c":
	        				
	        				do {
	        			        System.out.print("Di quale ristorante vuoi visualizzare il menù? ");
	        			        String scegliStr = scanner.nextLine();
	        			        controllo = true;
	        			        try {
	        			            scegli = Integer.parseInt(scegliStr);
	        			            if (scegli < 1 || scegli > listaRistorantiTrovati.size()) {
	        			                System.out.println("Numero non valido, riprova.");
	        			                controllo = false;
	        			            }
	        			        } catch (NumberFormatException e) {
	        			            System.out.println("Inserisci un numero valido.");
	        			            controllo = false;
	        			        }
	        			    } while (!controllo);
	        				
	        				
	        				 System.out.println("");
	        				 Ristorante rMenu = listaRistorantiTrovati.get(scegli - 1);
		        			    
	        				if (rMenu != null) {
	                            rMenu.caricaMenuRistorante();
	                            ArrayList<Piatto> menu = rMenu.getMenu();
	                            if (menu.isEmpty()) {
	                                System.out.println("Il menu di questo ristorante è vuoto.");
	                            } else {
	                                System.out.println("----- MENU DI " + rMenu.getName().toUpperCase() + " -----");
	                                for (Piatto p : menu) {
	                                    System.out.println("🍽 " + p.getNome() + " - " + p.getPrezzo() + "€");
	                                    System.out.println("   " + p.getDescrizione());
	                                }
	                                System.out.println("------------------------------------");
	                            }
	                        } else {
	                            System.out.println("Ristorante non trovato.");
	                        }
	        				
	        				break;
	        				
	        			case "d":
	        				break;
	        				
	        			default : 
	        				System.out.println("Opzione inesistente.");
	    			}
				}while(!miniMenu.equals("d"));
				
			}else
				System.out.println("Nessun ristorante trovato con questo criterio.");
	    	
	    }

     
    public void caricaRistoranti() {
    try (BufferedReader br = new BufferedReader(new FileReader("src/dati/ristoranti.csv"))) {
        br.readLine(); // Salta intestazione

        String line;
        while ((line = br.readLine()) != null) {
            String[] dati = line.split(",");

            if (dati.length >= 10) {
                String name = dati[0].replaceAll("^\"|\"$", ""); // Rimuove virgolette se presenti
                String address = dati[1].replaceAll("^\"|\"$", "");
                String city = dati[2].trim();
                String nation = dati[3].trim();
                String price = dati[4].replaceAll("^\"|\"$", "");
                String cuisine = dati[5].replaceAll("^\"|\"$", "");
                double longitude = Double.parseDouble(dati[6]);
                double latitude = Double.parseDouble(dati[7]);
                boolean delivery = Boolean.parseBoolean(dati[8]);
                boolean reservation = Boolean.parseBoolean(dati[9]);

                Ristorante ristorante = new Ristorante(
                    name, address, city, price, nation, cuisine,
                    longitude, latitude, delivery, reservation, new ArrayList<>()
                );

                ristoranti.add(ristorante);
            } else {
                System.out.println("Riga non valida nel file CSV: " + line);
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante il caricamento dei ristoranti: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Errore nel parsing di un numero: " + e.getMessage());
    }
}


public void menuCercaRistoranti(String citta) {
	
	int sceltaCriterio = 0;
	String tipo;
	boolean controllo;
	ArrayList<Ristorante> listaRistorantiTrovati = null;
	Scanner scanner = new Scanner(System.in);
	do {	
		System.out.println("Seleziona i criteri di ricerca dei ristoranti ");
		System.out.println("1) Per tipo di cucina e città");
		System.out.println("2) Per la mia città");
		System.out.println("3) Per fascia di prezzo e città");
		System.out.println("4) Disponibilità Delivery e città");
		System.out.println("5) Disponibilità Prenotazione Online e città");
		System.out.println("6) Per media stelle e città");
		System.out.println("7) Per tutti i criteri");
		System.out.println("8) Esci");
		
		do {
			controllo = true;
			System.out.print("Scegli: ");
			try {
				sceltaCriterio = Integer.parseInt(scanner.nextLine());
			}catch(NumberFormatException e) {
				  System.out.println("Formato non valido, riprova.");
				  controllo = false;
			}
		
	}while(!controllo);
	
	switch(sceltaCriterio) {
	
	case 1:
		do {
			System.out.println("Inserisci il tipo di cucina: ");
			tipo = scanner.nextLine();
		}while(!GestioneUtenti.campoNonVuoto(tipo));
		
		listaRistorantiTrovati = cercaRistoranti(tipo,citta);
		
		stampaListaRicerca(listaRistorantiTrovati);
		
		break;
		
	case 2:
		
		listaRistorantiTrovati = cercaRistoranti(citta);
		stampaListaRicerca(listaRistorantiTrovati);
		
		break;
		
	 case 3:
		 
		 double fasciaPrezzoMin = 0,fasciaPrezzoMax = 0;
 		 
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
 		
        listaRistorantiTrovati = cercaRistoranti(citta,fasciaPrezzoMin,fasciaPrezzoMax);
        stampaListaRicerca(listaRistorantiTrovati);
		 
		 break;
		 
	 case 4:
		 
		 String delivery;

 		do {
        	controllo=false;
            System.out.println("Richiedi servizio di delivery? (true/false):");
                delivery = scanner.nextLine();
                if(GestioneUtenti.campoNonVuoto(delivery)) {
	                if(delivery.equalsIgnoreCase("true") || delivery.equalsIgnoreCase("false"))
	                	controllo = true;
	                else
	                	System.out.println("Hai inserito un valore non valido, riprova.");
                }
        } while (!controllo);
 		
 		listaRistorantiTrovati = cercaRistoranti(citta,Boolean.parseBoolean(delivery));
 		stampaListaRicerca(listaRistorantiTrovati);
		 
		 break;
		 
	 case 5:

		 String prenotazione;
 		 
 		do {
        	controllo=false;
            System.out.println("Richiedi servizio di prenotazione online? (true/false):");
                prenotazione = scanner.nextLine();
                
                if(GestioneUtenti.campoNonVuoto(prenotazione)) {
	                if(prenotazione.equalsIgnoreCase("true") || prenotazione.equalsIgnoreCase("false"))
	                	controllo = true;
	                else
	                	System.out.println("Hai inserito un valore non valido, riprova.");
                }
                
        } while (!controllo);
 		
 		
 		listaRistorantiTrovati = cercaRistoranti(Boolean.parseBoolean(prenotazione),citta);
 		stampaListaRicerca(listaRistorantiTrovati);
		 
		 break;
		 
	 case 6: 
		 
		 double mediaStelle = 0;
 		 
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
 		
 		listaRistorantiTrovati = cercaRistoranti(citta,mediaStelle);
 		stampaListaRicerca(listaRistorantiTrovati);
		 
		 break;
		 
	 case 7: 
		 
		 String del;
		 String pren;
		 mediaStelle = 0; 
		 fasciaPrezzoMin=0;
		 fasciaPrezzoMax=0;
 		
		 do {
			 System.out.println("Inserisci il tipo di cucina: ");
			 tipo = scanner.nextLine();
		 }while(!GestioneUtenti.campoNonVuoto(tipo));
		 
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
                del = scanner.nextLine();
                if(GestioneUtenti.campoNonVuoto(del)) {
                	if(del.equalsIgnoreCase("true") || del.equalsIgnoreCase("false"))
                		controllo = true;
	                else
	                	System.out.println("Hai inserito un valore non valido, riprova.");
                }
                
        } while (!controllo);
 		
		 
        do {
        	controllo=false;
            System.out.println("Richiedi servizio di prenotazione online? (true/false):");
                pren = scanner.nextLine();
                
                if(GestioneUtenti.campoNonVuoto(pren)) {
	                if(pren.equalsIgnoreCase("true") || pren.equalsIgnoreCase("false"))
	                	controllo = true;
	                else
	                	System.out.println("Hai inserito un valore non valido, riprova.");
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
		
		
		listaRistorantiTrovati = cercaRistoranti(citta,tipo,fasciaPrezzoMin,fasciaPrezzoMax,Boolean.parseBoolean(del),Boolean.parseBoolean(pren),mediaStelle);
		stampaListaRicerca(listaRistorantiTrovati);

		 break;
		 
		 default : 
			 System.out.println("Opzione non presente, riprova.\n");
	} 
	}while(sceltaCriterio!=8);
	
}


	public boolean verificaEsistenzaRistorantePerRistoratore(String cf, String nomeRistorante) {
		
		try (CSVReader reader = new CSVReader(new FileReader("src/dati/proprietari.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
               
            		if(riga[0].equalsIgnoreCase(cf)) {
            			if(riga[1].equalsIgnoreCase(nomeRistorante)) {
            				return true; //se esiste già quel ristorante per quel ristoratore
            			}
            		}
                }

        } catch (IOException e) {
            e.printStackTrace();
            
        } catch (CsvValidationException e) {
            e.printStackTrace();
            
        }

		return false; //se non ha trovato corrispondenze non esiste e si può aggiungere
	}
   
}




