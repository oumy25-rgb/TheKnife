
package theknife;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedWriter;
import java.io.File;

import resources.GestioneFile;
import resources.Piatto;

/**
 * La classe <strong>GestioneRistoranti</strong> gestisce tutte le operazioni relative ai ristoranti 
 * della piattaforma <em>TheKnife</em>.
 * <p>
 * Le principali funzionalità sono:
 * <ul>
 *   <li>Registrazione e memorizzazione dei ristoranti</li>
 *   <li>Ricerca dei ristoranti secondo diversi criteri (città, cucina, prezzo, delivery, prenotazione, recensioni)</li>
 *   <li>Gestione dei menù associati ai ristoranti (creazione, aggiunta e rimozione piatti)</li>
 *   <li>Caricamento e visualizzazione dei dati dai file CSV</li>
 * </ul>
 * I dati vengono salvati in file CSV per garantire persistenza.
 * </p>
 * 
 * @see Ristorante
 * @see Piatto
 * @see resources.GestioneFile
 * 
 * @author Giuseppina Salvati
 * @author omema gharsellaoui
 */

public class GestioneRistoranti {
	/** Lista di ristoranti caricati o registrati nel sistema. */
    private ArrayList<Ristorante> ristoranti;
    Scanner scanner = new Scanner(System.in);
    /**
     * Costruttore: inizializza la lista dei ristoranti.
     */
    public GestioneRistoranti() {
        ristoranti = new ArrayList<>();
        new GestioneFile();
    }
    /**
     * Aggiunge un ristorante al sistema e aggiorna i file CSV dei proprietari e dei ristoranti.
     *
     * @param ristorante ristorante da aggiungere
     * @param codFiscale codice fiscale del proprietario
     */
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
     /**
     * Visualizza una lista generica di stringhe con indice numerato.
     *
     * @param lista lista di stringhe da visualizzare
     * @return true se la lista contiene elementi, false se vuota
     */
    
    public static boolean visualizzaLista(ArrayList<String> lista) {
    	
    	int i=1;
    	if(!lista.isEmpty()) {
			System.out.println("----------------------------------------------------------------------");
			for(String s : lista) {
				System.out.print((i++)+") "+s+"\n");
				System.out.println("----------------------------------------------------------------------");
			}
    	}else {
    		System.out.println("Nessun elemento trovato.");
    		return false;
    	}
    	return true;
    }

	/**
     * Cerca ristoranti in base a città e tipo di cucina.
     *
     * @param tipo tipo di cucina (es. Italiano, Giapponese)
     * @param citta città in cui cercare
     * @return lista di ristoranti che corrispondono ai criteri
     */ 
    private static ArrayList<Ristorante> cercaRistoranti(String tipo,String citta) {
    	
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
    
    /**
     * Cerca ristoranti in base a città e disponibilità di servizio delivery.
     *
     * @param citta città in cui cercare
     * @param delivery true se richiede delivery, false altrimenti
     * @return lista dei ristoranti che rispettano i criteri
     */
	private static ArrayList<Ristorante> cercaRistoranti(String citta,boolean delivery) {
	    	
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
	/**
     * Cerca ristoranti in base a città e disponibilità di prenotazione online.
     *
     * @param prenotazione true se richiede prenotazione, false altrimenti
     * @param citta città in cui cercare
     * @return lista di ristoranti che rispettano i criteri
     */  
	private static ArrayList<Ristorante> cercaRistoranti(boolean prenotazione,String citta) {
    	
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
	    
	 /**
     * Cerca ristoranti in base alla sola città.
     *
     * @param citta città in cui cercare
     * @return lista di ristoranti trovati
     */    
	public static ArrayList<Ristorante> cercaRistoranti(String citta) {
	    	
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

     /**
     * Cerca ristoranti in base a città e fascia di prezzo.
     *
     * @param citta città in cui cercare
     * @param fasciaPrezzoMin prezzo minimo
     * @param fasciaPrezzoMax prezzo massimo
     * @return lista dei ristoranti trovati
     */
	private static ArrayList<Ristorante> cercaRistoranti(String citta,double fasciaPrezzoMin,double fasciaPrezzoMax) {
	    	
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
	
	 /**
     * Cerca ristoranti in base a città e media delle recensioni.
     *
     * @param citta città in cui cercare
     * @param media media stelle richiesta
     * @return lista dei ristoranti trovati
     */
	private static ArrayList<Ristorante> cercaRistoranti(String citta,double media) {
    	
    	ArrayList<Ristorante> lista = new ArrayList<>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
            	
                if (citta.equalsIgnoreCase(riga[2])) {
                	
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    if(Ristorante.calcoloMediaStelle(r.getName(),r.getCity(),r.getAddress())==media) {
                    
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

/**
     * Cerca ristoranti combinando tutti i criteri di ricerca.
     *
     * @param citta città in cui cercare
     * @param tipo tipo di cucina
     * @param fasciaPrezzoMin prezzo minimo
     * @param fasciaPrezzoMax prezzo massimo
     * @param delivery true se richiede delivery
     * @param prenotazione true se richiede prenotazione
     * @param media media stelle richiesta
     * @return lista dei ristoranti trovati
     */	
	
private static ArrayList<Ristorante> cercaRistoranti(String citta,String tipo,double fasciaPrezzoMin,double fasciaPrezzoMax,boolean delivery,boolean prenotazione,double media) {
    	
    	ArrayList<Ristorante> lista = new ArrayList<>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
            	
                if (citta.equalsIgnoreCase(riga[2]) && tipo.equalsIgnoreCase(riga[5]) && (Double.parseDouble(riga[4]) >= fasciaPrezzoMin && Double.parseDouble(riga[4]) <= fasciaPrezzoMax)
                		&& delivery == Boolean.parseBoolean(riga[8]) && prenotazione == Boolean.parseBoolean(riga[9])) {
                	
                    Ristorante r = new Ristorante(
                        riga[0], riga[1], riga[2], riga[4], riga[3],riga[5], Double.parseDouble(riga[6]), Double.parseDouble(riga[7]), Boolean.parseBoolean(riga[8]),Boolean.parseBoolean(riga[9]), null
                    );
                    
                    if(Ristorante.calcoloMediaStelle(r.getName(),r.getCity(),r.getAddress())==media) {
                    
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
 /**
     * Crea un nuovo file CSV per il menù di un ristorante e consente l'inserimento dei piatti.
     *
     * @param nomeRistorante nome del ristorante
     */  
 public void creaEMenuRistorante(String nomeRistorante) {
	 boolean controllo;
    String risposta;
    File file = new File("src/dati", nomeRistorante+"Menu.csv");
    
    try {
		file.createNewFile();
	} catch (IOException e1) {
		System.out.println("Errore nella creazione del Menù.");
		return;
	}
    
    do {
        String nome;
        do {
        	controllo = true;
            System.out.print("Nome del piatto: ");
            nome = scanner.nextLine().trim();
            
            if (nome.isEmpty()) {
                System.out.println("Il nome del piatto non può essere vuoto.");
                controllo = false;
            }else {
            	
            	if(nome.contains(",")) {
        			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
        			controllo = false;
        		}
            }
            
        } while (!controllo);

        String descrizione;
        do {
        	controllo = true;
            System.out.print("Descrizione del piatto: ");
            descrizione = scanner.nextLine().trim();
            if (descrizione.isEmpty()) {
                System.out.println("La descrizione non può essere vuota.");
                controllo = false;
            }else {
            	if(descrizione.contains(",")) {
        			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
        			controllo = false;
        		}
            }
        } while (!controllo);

        double prezzo = -1;
        do {
            try {
                System.out.print("Prezzo del piatto: ");
                prezzo = Double.parseDouble(scanner.nextLine().trim());
                if (prezzo < 0) {
                    System.out.println("Il prezzo non può essere negativo.");
                    prezzo = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Valore inserito non valido, riprova.");
            }
        } while (prezzo < 0);

        // Crea il piatto
        Piatto piatto = new Piatto(nome, descrizione, prezzo);

        // Usa il metodo centralizzato per aggiungere piatto e salvare su file
        aggiungiPiattoAlMenu(nomeRistorante+"Menu.csv", piatto);

        System.out.print("Vuoi aggiungere un altro piatto? (s = si/n = no): ");
        risposta = scanner.nextLine().trim();

    } while (risposta.equalsIgnoreCase("s"));

    System.out.println("Menu completato e salvato con successo.");
}
    /**
     * Cerca un ristorante per nome.
     *
     * @param nome nome del ristorante
     * @return ristorante corrispondente o null se non trovato
     */
   public static Ristorante cercaRistorantePerNome(String nome) {
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

   /**
     * Aggiunge un piatto al menù di un ristorante salvandolo su file CSV.
     *
     * @param nomeMenu nome del file menù
     * @param piatto piatto da aggiungere
     * @return true se aggiunto correttamente, false altrimenti
     */	
   public static boolean aggiungiPiattoAlMenu(String nomeMenu, Piatto piatto) {
	   
	   
	// aggiunge .csv al nome del menu (solo per il nome, non per usare CSV)
	    String nomeFile = nomeMenu.endsWith(".csv") ? nomeMenu : nomeMenu + ".csv";
	    File file = new File("src/dati", nomeFile);

	    // controlla che il file esista
	    if (!file.exists()) {
	        System.out.println("Errore: Menù inesistente!");
	        return false;
	    }

	    // controlla se il piatto esiste già
	    if (cercaPiatto(piatto.getNome(), nomeFile)) {
	        System.out.println("Il Piatto esiste già!");
	        return false;
	    }

	    // scrive il piatto come riga di testo
	    try (FileWriter fw = new FileWriter(file, true);
	         BufferedWriter bw = new BufferedWriter(fw)) {

	        
	        String riga = piatto.toCSV();
	        bw.write(riga);
	        bw.newLine(); 
	        System.out.println("Piatto aggiunto con successo.");
	        return true;

	    } catch (IOException e) {
	        System.out.println("Errore durante la scrittura del piatto: " + e.getMessage());
	        return false;
	    }
	}
    /**
     * Verifica se un piatto esiste già in un menù.
     *
     * @param piatto nome del piatto
     * @param nomeMenu nome del file menù
     * @return true se il piatto esiste, false altrimenti
     */
   private static boolean cercaPiatto(String piatto,String nomeMenu) {
	   
	   try (CSVReader reader = new CSVReader(new FileReader("src/dati/"+nomeMenu))) {
	        String[] riga;
	        while ((riga = reader.readNext()) != null) {
	            
	            if (piatto.equalsIgnoreCase(riga[0])) {
	                return true;
	            }
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Errore " + e.getMessage());
	    }
	   return false;
   }
   

    /**
     * Rimuove un piatto da un menù aggiornando il file CSV.
     *
     * @param nomeMenu nome del menù
     * @param nomePiatto piatto da rimuovere
     */
    public static void rimuoviPiattoDalMenu(String nomeMenu, String nomePiatto) {
    	
    	String nomeFile = nomeMenu.endsWith(".csv") ? nomeMenu : nomeMenu + ".csv";
    	File file = new File("src/dati", nomeFile);
    	
    	 ArrayList<String> piattiAggiornati = new ArrayList<String>();
    	 String unita;
    	 
    	 try (CSVReader reader = new CSVReader(new FileReader(file))) {
    	        String[] riga;
    	        while ((riga = reader.readNext()) != null) {
    	          if(riga[0].equalsIgnoreCase(nomePiatto)) {
    	        	  continue;
    	          }else {
    	        	  unita = String.join(",", riga);
    	        	  piattiAggiornati.add(unita);
    	          }  
    	          
    	        }
    	    } catch (IOException | CsvValidationException e) {
    	        System.out.println("Errore nella rimozione del piatto.");
    	        return;
    	    }
    	 
    	 try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
    	        for (String riga : piattiAggiornati) {
    	            writer.write(riga);
    	            writer.newLine();
    	        }
    	    } catch (IOException e) {
    	    	System.out.println("Errore nella rimozione del piatto.");
    	    }
    	 System.out.println("Piatto rimosso con successo!");
    	
    }
     /**
     * Stampa la lista dei ristoranti trovati con possibilità di azioni aggiuntive:
     * <ul>
     *   <li>Visualizzare informazioni</li>
     *   <li>Visualizzare recensioni</li>
     *   <li>Visualizzare menù</li>
     * </ul>
     *
     * @param listaRistorantiTrovati lista di ristoranti trovati
     */
	private void stampaListaRicerca(ArrayList<Ristorante> listaRistorantiTrovati) {
	    	String miniMenu;
	    	int scegli = 0,i=1;
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
					System.out.println("Scelta:");
					
					
					miniMenu = scanner.nextLine().toLowerCase().trim();
					
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
	        			   Ristorante.visualizzaRecensioni(listaRistorantiTrovati.get(scegli - 1).getName());
	        				
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
	                                break;
	                            } else {
	                                System.out.println("----- MENU DI " + rMenu.getName().toUpperCase() + " -----");
	                                for (Piatto p : menu) {
	                                    System.out.println("" + p.getNome() + " - " + p.getPrezzo() + "€");
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


/**
     * Fornisce un menù interattivo per cercare i ristoranti in base a criteri scelti dall’utente.
     *
     * @param citta città su cui effettuare la ricerca
     */
public void menuCercaRistoranti(String citta) {
	
	int sceltaCriterio = 0;
	String tipo;
	boolean controllo;
	ArrayList<Ristorante> listaRistorantiTrovati = null;

	do {	
		System.out.println("\n===== CERCA RISTORANTI =====");
		System.out.println("Seleziona i criteri di ricerca dei ristoranti ");
		System.out.println("1) Per tipo di cucina");
		System.out.println("2) Per città ");
		System.out.println("3) Per fascia di prezzo");
		System.out.println("4) Disponibilità Delivery");
		System.out.println("5) Disponibilità Prenotazione");
		System.out.println("6) Per media stelle");
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
			tipo = scanner.nextLine().trim();
			
		}while(!GestioneUtenti.nominativoValido(tipo));
		
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
                fasciaPrezzoMin = Double.parseDouble(scanner.nextLine().replace(",", "."));
                controllo = true;
            } catch (NumberFormatException e) {
                System.out.println("Hai inserito un valore non valido, riprova.");
            }

        } while (!controllo);
        
        do {
            controllo = false;
            System.out.println("Inserisci fascia di prezzo massima:");
            try {
                fasciaPrezzoMax = Double.parseDouble(scanner.nextLine().replace(",", "."));
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
                delivery = scanner.nextLine().trim();
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
                prenotazione = scanner.nextLine().trim();
                
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
                mediaStelle = Double.parseDouble(scanner.nextLine().replace(",", "."));
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
			 tipo = scanner.nextLine().trim();
			 
		 }while(!GestioneUtenti.nominativoValido(tipo));
		 
  		do {
        	controllo=false;
            System.out.println("Inserisci fascia di prezzo minima:");

            try {
                fasciaPrezzoMin = Double.parseDouble(scanner.nextLine().replace(",", "."));
                controllo = true;
            } catch (NumberFormatException e) {
                System.out.println("Hai inserito un valore non valido, riprova.");
            }

        } while (!controllo);
        
        do {
            controllo = false;
            System.out.println("Inserisci fascia di prezzo massima:");
            try {
                fasciaPrezzoMax = Double.parseDouble(scanner.nextLine().replace(",", "."));
                controllo = true;
            } catch (NumberFormatException e) {
                System.out.println("Hai inserito un valore non valido, riprova.");
            }
            
        } while (!controllo);
        
        do {
        	controllo=false;
            System.out.println("Richiedi servizio di delivery? (true/false):");
                del = scanner.nextLine().trim();
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
                pren = scanner.nextLine().trim();
                
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
                mediaStelle = Double.parseDouble(scanner.nextLine().replace(",", "."));
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
		 
	 	case 8:
		 break;
		 
		 default : 
			 System.out.println("Opzione non presente, riprova.\n");
	} 
	}while(sceltaCriterio!=8);
	
}

    /**
     * Verifica l'esistenza di un ristorante in base al nome.
     *
     * @param nomeRistorante nome del ristorante
     * @return true se esiste, false altrimenti
     */
	public static boolean verificaEsistenzaRistorante(String nomeRistorante) {
		
		try (CSVReader reader = new CSVReader(new FileReader("src/dati/ristoranti.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
          
            			if(riga[0].equalsIgnoreCase(nomeRistorante)) {
            				return true; //se esiste già quel ristorante
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





