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

    public void aggiungiRistorante(Ristorante ristorante) {
        if (ristorante != null) {
            ristoranti.add(ristorante);
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
    ArrayList<Piatto> menu = new ArrayList<>();
    String risposta;

    do {
        try {
            System.out.print("Nome del piatto: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) throw new IllegalArgumentException("Il nome del piatto non può essere vuoto.");

            System.out.print("Descrizione del piatto: ");
            String descrizione = scanner.nextLine().trim();
            if (descrizione.isEmpty()) throw new IllegalArgumentException("La descrizione non può essere vuota.");

            System.out.print("Prezzo del piatto: ");
            double prezzo = Double.parseDouble(scanner.nextLine());
            if (prezzo < 0) throw new IllegalArgumentException("Il prezzo non può essere negativo.");

            Piatto piatto = new Piatto(nome, descrizione, prezzo);
            menu.add(piatto);

            System.out.println("✅ Piatto aggiunto con successo, grazie!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Errore: inserisci un numero valido per il prezzo.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Errore imprevisto: " + e.getMessage());
        }

        System.out.print("Vuoi aggiungere un altro piatto? (s/n): ");
        risposta = scanner.nextLine();

    } while (risposta.equalsIgnoreCase("s"));

    try {
        new GestioneMenu().scriviMenu(nomeRistorante + "Menu.csv", menu);
        System.out.println("Menu creato e salvato con successo.");
    } catch (Exception e) {
        System.out.println("Errore durante il salvataggio del menu: " + e.getMessage());
    }
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



    // Nuovo metodo per aggiungere un piatto al menu
    public void aggiungiPiattoAlMenu(String nomeRistorante, Piatto piatto) {
        Ristorante ristorante = cercaRistorantePerNome(nomeRistorante);
        if (ristorante != null) {
            ristorante.aggiungiPiatto(piatto);
            System.out.println("Piatto aggiunto al menu di " + nomeRistorante);
        } else {
            System.out.println("Ristorante non trovato.");
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
	    	int scegli,i=1;
	    	Scanner scanner = new Scanner(System.in);
	    	
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




   

 
    
}

