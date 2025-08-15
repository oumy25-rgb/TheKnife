/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import resources.Piatto;
import resources.GestioneFile;
import resources.GestioneMenu;

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
        }
    }
}

    public ArrayList<String> visualizzaNomeMieiRistoranti(String cf) {
    	
    	ArrayList<String> lista = new ArrayList<String>();
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/proprietari.csv"))) {
        	String[] riga;
            while ((riga = reader.readNext()) != null) {
                if (cf.equals(riga[0])) {
                    String s = riga[1];
                    lista.add(s);
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
    
    public void mostraMenu(GestioneRistoranti gestioneRistoranti, GestioneRecensioni gestioneRecensioni) {
        int scelta = -1;   boolean controllo;
        do {
            System.out.println("===== MENU RISTORATORE =====");
            System.out.println("1. Aggiungi un ristorante");
            System.out.println("2. Effettua operazioni sulle recensioni"); //puoi accedere a queste opzioni solo se hai almeno un ristorante
            System.out.println("3. Effettua operazioni sui menu"); //puoi accedere a queste opzioni solo se hai almeno un ristorante
            System.out.println("4. Elimina un ristorante"); //puoi accedere a queste opzioni solo se hai almeno un ristorante
            System.out.println("0. Logout");
            
			do {
            	controllo = true;
            	System.out.print("Scelta: ");
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
                    aggiungiRistorante(gestioneRistoranti,this.getCodFiscale());
                    break;
               
                case 2:
                	if(!visualizzaNomeMieiRistoranti(this.getCodFiscale()).isEmpty())
                		operazioniRecensioni();
                	else 
                		System.out.println("Devi prima aggiungere almeno un ristorante, riprova.");
                	
                break;
                
                case 3:
                	if(!visualizzaNomeMieiRistoranti(this.getCodFiscale()).isEmpty())
                		operazioniMenu();
                	else 
                		System.out.println("Devi prima aggiungere almeno un ristorante, riprova.");
                break;
                
                case 4:
                	if(!visualizzaNomeMieiRistoranti(this.getCodFiscale()).isEmpty()) {
                		int scegli; int i=1;
            		   	ArrayList<String> lista = visualizzaNomeMieiRistoranti(this.getCodFiscale());
            		   	scegli = 0;
            		   	if(!lista.isEmpty()) {
            					System.out.println("\nLista ristoranti trovati: \n");
            					System.out.println("----------------------------------------------------------------------");
            					for(String s : lista) {
            						System.out.print((i++)+") "+s+"\n");
            						System.out.println("----------------------------------------------------------------------");
            					}
            		   	}
            		   	
            		   	do {
        				    controllo = true;
        				    System.out.print("Quale ristorante vuoi eliminare? ");
        				    try {
        				        scegli = Integer.parseInt(scanner.nextLine());
        				        if (scegli < 1 || scegli > lista.size()) {
        				            System.out.println("Scelta non presente, riprova.");
        				            controllo = false;
        				        }
        				    } catch (NumberFormatException e) {
        				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
        				        controllo = false;
        				    }
        				} while (!controllo);
        		
        				System.out.println("");
        				
        				if(GestioneFile.rimuoviRistorante("src/dati/ristoranti.csv", gestioneRistoranti.cercaRistorantePerNome(lista.get(scegli - 1)))) 
        					if(GestioneFile.rimuoviRistorante("src/dati/proprietari.csv",this.getCodFiscale(),lista.get(scegli - 1)))
        						if(GestioneFile.eliminaMenu("src/dati", (lista.get(scegli - 1)+"Menu.csv")))
        							System.out.println("Rimozione avvenuta con successo!");
        				
        			}else 
        				System.out.println("Devi prima aggiungere almeno un ristorante, riprova.");
                	break;
                case 0:
                    System.out.println("Logout effettuato.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (scelta != 0);
    }

    
    private void operazioniRecensioni() {
    	
    	int scelta=-1;
    	GestioneRecensioni gestioneRecensioni = new GestioneRecensioni();
    	
    	do {
    		
    		System.out.println("\n===== OPERAZIONI SULLE RECENSIONI =====");
    		System.out.println("1. Visualizza riepilogo delle recensioni");
            System.out.println("2. Visualizza recensioni");
            System.out.println("3. Rispondi a una recensione");
    	    System.out.println("4. Esci");
    		
    		boolean controllo;
    		do {
    	    	controllo = true;
    	    	System.out.print("Scelta: ");
    	    	try {
    	    		scelta = Integer.parseInt(scanner.nextLine());
    	    		System.out.println();
    	    	}catch(NumberFormatException e) {
    	    		System.out.println("Valore inserito non valido, riprova.");
    	    		controllo = false;
    	    	}
    	    }while(!controllo);
    		
    		switch(scelta) {	 
    		
    		case 1:
    			int scegli; int i=1;
    		   	ArrayList<String> lista = visualizzaNomeMieiRistoranti(this.getCodFiscale());
    		   	scegli = 0;
    		   	if(!lista.isEmpty()) {
    					System.out.println("\nLista ristoranti trovati: \n");
    					System.out.println("----------------------------------------------------------------------");
    					for(String s : lista) {
    						System.out.print((i++)+") "+s+"\n");
    						System.out.println("----------------------------------------------------------------------");
    					}
    		   	}
    		   	
    		   	do {
				    controllo = true;
				    System.out.print("Di quale ristorante vuoi visualizzare il riepilogo delle recensioni? ");
				    try {
				        scegli = Integer.parseInt(scanner.nextLine());
				        if (scegli < 1 || scegli > lista.size()) {
				            System.out.println("Scelta non presente, riprova.");
				            controllo = false;
				        }
				    } catch (NumberFormatException e) {
				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
				        controllo = false;
				    }
				} while (!controllo);
		
				System.out.println("");
				
    			Ristorante.visualizzaRiepilogo(lista.get(scegli - 1));
    			
    			break;
    		case 2:
    			scegli=-1; i=1;
    		   	lista = visualizzaNomeMieiRistoranti(this.getCodFiscale());
    		   	scegli = 0;
    		   	if(!lista.isEmpty()) {
    					System.out.println("\nLista ristoranti trovati: \n");
    					System.out.println("----------------------------------------------------------------------");
    					for(String s : lista) {
    						System.out.print((i++)+") "+s+"\n");
    						System.out.println("----------------------------------------------------------------------");
    					}
    		   	}
    		   	
    		   	do {
				    controllo = true;
				    System.out.print("Di quale ristorante vuoi visualizzare le recensioni? ");
				    try {
				        scegli = Integer.parseInt(scanner.nextLine());
				        if (scegli < 1 || scegli > lista.size()) {
				            System.out.println("Scelta non presente, riprova.");
				            controllo = false;
				        }
				    } catch (NumberFormatException e) {
				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
				        controllo = false;
				    }
				} while (!controllo);
		
				System.out.println("");
			
    			Ristorante.visualizzaRecensioni(lista.get(scegli - 1));
    			
    			break;
    		case 3:
    			scegli=-1; i=1;
    		   	lista = visualizzaNomeMieiRistoranti(this.getCodFiscale());
    		   	scegli = 0;
    		   	if(!lista.isEmpty()) {
    					System.out.println("\nLista ristoranti trovati: \n");
    					System.out.println("----------------------------------------------------------------------");
    					for(String s : lista) {
    						System.out.print((i++)+") "+s+"\n");
    						System.out.println("----------------------------------------------------------------------");
    					}
    		   	}
    		   	
    		   	do {
				    controllo = true;
				    System.out.print("Di quale ristorante? ");
				    try {
				        scegli = Integer.parseInt(scanner.nextLine());
				        if (scegli < 1 || scegli > lista.size()) {
				            System.out.println("Scelta non presente, riprova.");
				            controllo = false;
				        }
				    } catch (NumberFormatException e) {
				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
				        controllo = false;
				    }
				} while (!controllo);
		
				System.out.println("");
				
				rispondiARecensione(gestioneRecensioni,lista.get(scegli - 1));
    			break;
    		case 4:
    			break;
    		default:
 			   System.out.println("Scelta non presente,riprova.");
 		   }
 	   
 	   }while(scelta!=4);
 }
    
    
   private void operazioniMenu() {
	   GestioneRistoranti gestioneRistoranti = new GestioneRistoranti();
	   int scelta=-1;
	  
	do {
		
		System.out.println("\n===== OPERAZIONI SUI MIEI MENU' =====");
		   System.out.println("1. Crea il menu del ristorante");
	       System.out.println("2. Aggiungi un piatto al menu");
	       System.out.println("3. Rimuovi un piatto dal menu");
	       System.out.println("4. Visualizza menu dei miei ristoranti");
	       System.out.println("5. Elimina menu ristorante");
	       System.out.println("6. Esci");
		
		boolean controllo;
		do {
	    	controllo = true;
	    	System.out.print("Scelta: ");
	    	try {
	    		scelta = Integer.parseInt(scanner.nextLine());
	    		System.out.println();
	    	}catch(NumberFormatException e) {
	    		System.out.println("Valore inserito non valido, riprova.");
	    		controllo = false;
	    	}
	    }while(!controllo);
		
		switch(scelta) {	   
		   case 1:  
		   	
		   	int scegli; int i=1;
		   	ArrayList<String> lista = visualizzaNomeMieiRistoranti(this.getCodFiscale());
		   	scegli = 0;
		   	if(!lista.isEmpty()) {
					System.out.println("\nLista ristoranti trovati: \n");
					System.out.println("----------------------------------------------------------------------");
					for(String s : lista) {
						System.out.print((i++)+") "+s+"\n");
						System.out.println("----------------------------------------------------------------------");
					}
		   	}
					
		   	do {
				    controllo = true;
				    System.out.print("Per quale ristorante vuoi creare il menù? ");
				    try {
				        scegli = Integer.parseInt(scanner.nextLine());
				        if (scegli < 1 || scegli > lista.size()) {
				            System.out.println("Scelta non presente, riprova.");
				            controllo = false;
				        }
				    } catch (NumberFormatException e) {
				        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
				        controllo = false;
				    }
				} while (!controllo);
		
				System.out.println("");
				
				if(GestioneMenu.cercaMenu(lista.get(scegli - 1)+"Menu.csv")) {
		   		System.out.println("Il menù per questo ristorante esiste già.\n");
		   	}else {
		   		gestioneRistoranti.creaEMenuRistorante(lista.get(scegli - 1));
		   	}
		       
		       break;
		       
		   case 2:
		   	
		   	
		   	scegli = 0; i=1;
		   	ArrayList<String> listaMenu = visualizzaNomeMenu(this.getCodFiscale());
		
		   	if(!listaMenu.isEmpty()) {
					System.out.println("\nLista dei Menù trovati: \n");
					System.out.println("----------------------------------------------------------------------");
					for(String s : listaMenu) {
						s = s.replace("Menu", "");
						System.out.print((i++)+") "+s+"\n");
						System.out.println("----------------------------------------------------------------------");
					}
					
					do {
					    controllo = true;
					    System.out.print("A quale menù vuoi aggiungere un piatto? ");
					    try {
					        scegli = Integer.parseInt(scanner.nextLine());
					        if (scegli < 1 || scegli > listaMenu.size()) {
					            System.out.println("Scelta non presente, riprova.");
					            controllo = false;
					        }
					    } catch (NumberFormatException e) {
					        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
					        controllo = false;
					    }
					} while (!controllo);
		
					System.out.println("");
		       	
		       	String nomePiatto = "";
				do {
				    System.out.print("Nome del piatto: ");
				    nomePiatto = scanner.nextLine().trim();
				} while (!GestioneUtenti.campoNonVuoto(nomePiatto));
				
				String descrizionePiatto = "";
				do {
				    System.out.print("Descrizione del piatto: ");
				    descrizionePiatto = scanner.nextLine().trim();
				} while (!GestioneUtenti.campoNonVuoto(descrizionePiatto));
				
				
				double prezzoPiatto = 0.0;
				do {
				    try {
				        controllo = true;
				        System.out.print("Prezzo del piatto: ");
				        prezzoPiatto = Double.parseDouble(scanner.nextLine().trim());
				    } catch (NumberFormatException e) {
				        System.out.println("Valore inserito non valido, riprova.");
				        controllo = false;
				    }
				} while (!controllo);
				
				Piatto nuovoPiatto = new Piatto(nomePiatto, descrizionePiatto, prezzoPiatto);
				
				gestioneRistoranti.aggiungiPiattoAlMenu(listaMenu.get(scegli - 1),nuovoPiatto);  
				
		   	}else {
		   		System.out.println("Nessun Menù trovato.");
		   	}
					
		   	
			break;
		
		   case 3:
		   	scegli = 0; i=1;
		   	listaMenu = visualizzaNomeMenu(this.getCodFiscale());
		
		   	if(!listaMenu.isEmpty()) {
					System.out.println("\nLista dei Menù trovati: \n");
					System.out.println("----------------------------------------------------------------------");
					for(String s : listaMenu) {
						s = s.replace("Menu", "");
						System.out.print((i++)+") "+s+"\n");
						System.out.println("----------------------------------------------------------------------");
					}
					
					do {
					    controllo = true;
					    System.out.print("Da quale menù vuoi rimuovere un piatto? ");
					    try {
					        scegli = Integer.parseInt(scanner.nextLine());
					        if (scegli < 1 || scegli > listaMenu.size()) {
					            System.out.println("Scelta non presente, riprova.");
					            controllo = false;
					        }
					    } catch (NumberFormatException e) {
					        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
					        controllo = false;
					    }
					} while (!controllo);
		
					System.out.println("");
		       	
		       	
					ArrayList<String> listaPiatti = visualizzaPiattiMenu(listaMenu.get(scegli - 1));
					int scegliPiatto = 0; i=1;
					
					if(!listaPiatti.isEmpty()) {
						System.out.println("\nLista dei Piatti trovati: \n");
						System.out.println("----------------------------------------------------------------------");
						for(String s : listaPiatti) {
						
							System.out.print((i++)+") "+s+"\n");
							System.out.println("----------------------------------------------------------------------");
						}
						
						do {
						    controllo = true;
						    System.out.print("Quale piatto vuoi rimuovere? ");
						    try {
						        scegliPiatto = Integer.parseInt(scanner.nextLine());
						        if (scegliPiatto < 1 || scegliPiatto > listaPiatti.size()) {
						            System.out.println("Scelta non presente, riprova.");
						            controllo = false;
						        }
						    } catch (NumberFormatException e) {
						        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
						        controllo = false;
						    }
						} while (!controllo);
						
						gestioneRistoranti.rimuoviPiattoDalMenu(listaMenu.get(scegli - 1),listaPiatti.get(scegliPiatto - 1));
					
					}else {
						System.out.println("Nessun piatto trovato.");
					}
				
		   	}else {
		   		System.out.println("Nessun Menù trovato.");
		   	}
		   	
		       break;
		    
		   case 4:
			   
			   scegli = 0; i=1;
			   	listaMenu = visualizzaNomeMenu(this.getCodFiscale());
			
			   	if(!listaMenu.isEmpty()) {
						System.out.println("\nLista dei Menù trovati: \n");
						System.out.println("----------------------------------------------------------------------");
						for(String s : listaMenu) {
							s = s.replace("Menu", "");
							System.out.print((i++)+") "+s+"\n");
							System.out.println("----------------------------------------------------------------------");
						}
						
						do {
						    controllo = true;
						    System.out.print("Quale menu vuoi visualizzare? ");
						    try {
						        scegli = Integer.parseInt(scanner.nextLine());
						        if (scegli < 1 || scegli > listaMenu.size()) {
						            System.out.println("Scelta non presente, riprova.");
						            controllo = false;
						        }
						    } catch (NumberFormatException e) {
						        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
						        controllo = false;
						    }
						} while (!controllo);
			
						System.out.println("");
						
						ArrayList<String> listaPiatti = visualizzaPiattiMenu(listaMenu.get(scegli - 1));
						if(!listaPiatti.isEmpty()) {
							System.out.println("\nLista dei Piatti trovati: \n");
							System.out.println("----------------------------------------------------------------------");
							i=1;
							for(String s : listaPiatti) {
							
								System.out.print((i++)+") "+s+"\n");
								System.out.println("----------------------------------------------------------------------");
							}
						}else {
							System.out.println("Nessun piatto trovato.");
							
						}		
				}else {
			   		System.out.println("Nessun Menù trovato.");
			   	}
			   break;
			   
		   case 5:
			   listaMenu = visualizzaNomeMenu(this.getCodFiscale());
			   scegli = 0;i=1;
			   	if(!listaMenu.isEmpty()) {
						System.out.println("\nLista dei Menù trovati: \n");
						System.out.println("----------------------------------------------------------------------");
						for(String s : listaMenu) {
							s = s.replace("Menu", "");
							System.out.print((i++)+") "+s+"\n");
							System.out.println("----------------------------------------------------------------------");
						}
						
						do {
						    controllo = true;
						    System.out.print("Quale menu vuoi eliminare? ");
						    try {
						        scegli = Integer.parseInt(scanner.nextLine());
						        if (scegli < 1 || scegli > listaMenu.size()) {
						            System.out.println("Scelta non presente, riprova.");
						            controllo = false;
						        }
						    } catch (NumberFormatException e) {
						        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
						        controllo = false;
						    }
						} while (!controllo);
			
						System.out.println("");
						if(GestioneFile.eliminaMenu("src/dati", listaMenu.get(scegli - 1)+".csv"))
							System.out.println("Rimozione Menu avvenuta con successo!");
			   	}else {
			   		System.out.println("Nessun Menù trovato.");
			   	}
					
					
			   break;
			   
		   case 6:
			   break;
			   
		   default:
			   System.out.println("Scelta non presente,riprova.");
		   }
	   
	   }while(scelta!=6);
}
   
   private void aggiungiRistorante(GestioneRistoranti gestioneRistoranti,String cf) {
    // Aggiungi il ristorante al sistema
    gestioneRistoranti.aggiungiRistorante(menuAggiuntaRistorante(gestioneRistoranti,cf), cf);
    System.out.println("Ristorante aggiunto e associato con successo!");
}
   
   public ArrayList<String> visualizzaPiattiMenu(String nomeMenu){
	   
	   String nomeFile = nomeMenu.endsWith(".csv") ? nomeMenu : nomeMenu + ".csv";
	   
	   File file = new File("src/dati", nomeFile);
	   
	   ArrayList<String> listaPiatti = new ArrayList<>();

	    
	    try (CSVReader reader = new CSVReader(new FileReader(file))) {
	        String[] riga;
	        while ((riga = reader.readNext()) != null) {
	        	if(riga[0].equalsIgnoreCase("nome"))
	        		continue;
	            listaPiatti.add(riga[0]); 
	        }
	    } catch (IOException | CsvValidationException e) {
	        System.out.println("Errore nel visualizzare i piatti");
	        return null;
	    }

	    return listaPiatti;
   }
   
   
  public  ArrayList<String> visualizzaNomeMenu(String cf){
	   
	  File cartella = new File("src/dati");
	  
	  ArrayList<String> listaMenu = new ArrayList<String>();
	  ArrayList<String> nomiMenu = new ArrayList<>();
	  
	  String[] listaFile = null;
	  
	  if (cartella.isDirectory()) {
	        listaFile = cartella.list();

	        if (listaFile != null) {
	            for (String nome : listaFile) {
	                int punto = nome.lastIndexOf(".");
	                //Il metodo lastIndexOf(".") restituisce: La posizione dell’ultimo punto nel nome della stringa, Se il punto non esiste, restituisce -1.
	                if (punto != -1) {
	                    nomiMenu.add(nome.substring(0, punto));
	                } else {
	                    nomiMenu.add(nome);
	                }
	            }
	        } else {
	            System.out.println("La cartella è vuota o non può essere letta.");
	            return null;
	        }
	    } else {
	        System.out.println("Il percorso indicato non è una cartella valida.");
	        return null;
	    }
	    
      try (CSVReader reader = new CSVReader(new FileReader("src/dati/proprietari.csv"))) {
      	String[] riga;
          while ((riga = reader.readNext()) != null) {
              if (cf.equals(riga[0])) {
                  String s = riga[1];
                  for(String menu : nomiMenu) {
                	  
                	  if((s+"menu").equalsIgnoreCase(menu)) {
                		  listaMenu.add(menu);
                	  }
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
      return listaMenu;
	   
   }
   
	public static Ristorante menuAggiuntaRistorante(GestioneRistoranti gestioneRistoranti,String cf) {
		
		System.out.println("Inserisci i dettagli del ristorante");
	    boolean controllo;
	    String nome="";
	    
	    do {
	    	controllo = true;
	    	
	    	System.out.println("Inserisci il nome del ristorante:");
	    	 nome = scanner.nextLine().trim();
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
	    	address = scanner.nextLine().trim();
	    }while(!GestioneUtenti.campoNonVuoto(address));
	    
	    String city="";
	    
	    do {
		    System.out.print("Inserisci città del ristorante:");
		    city = scanner.nextLine().trim();
	    }while(!GestioneUtenti.campoNonVuoto(address));
	    
	    String nation="";
	    
	    do {
	    	System.out.print("Inserisci nazione del ristorante:");
	    	nation = scanner.nextLine().trim();
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
	    	cuisine = scanner.nextLine().trim();
	    }while(!GestioneUtenti.campoNonVuoto(cuisine));
	    
	    String delivery;

	    do {
	        controllo = false;
	        System.out.println("Inserisci opzione di servizio delivery (true/false): ");
	        delivery = scanner.nextLine().trim();
	        
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
	        prenotazione = scanner.nextLine().trim();
	        
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
	    
	    return  new Ristorante(
	            nome, address, city, 
	            price, nation, cuisine, 
	            Double.parseDouble(longi), Double.parseDouble(lati), Boolean.parseBoolean(delivery), Boolean.parseBoolean(prenotazione), new ArrayList<>()
	        );
	}
	

    private void rispondiARecensione(GestioneRecensioni gestioneRecensioni,String ristorante) {

        ArrayList<Recensione> recensioni = Recensione.cercaPerRistorante(ristorante);

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
        
        boolean controllo;
        int scelta=0;
		do {
        	controllo=true;
        	try {
        		System.out.print("A quale recensione vuoi rispondere? ");
        		scelta = Integer.parseInt(scanner.nextLine()) - 1;
        		if(scelta<0 || scelta>=recensioni.size()) {
        			System.out.println("Opzione non presente, riprova.");
        			controllo=false;
        		}
        	}catch(NumberFormatException e) {
        		System.out.println("Valore inserito non valido, riprova.");
        		controllo=false;
        	}
        }while(!controllo);
        
            Recensione selezionata = recensioni.get(scelta);
            String nomeCliente = GestioneFile.getNomeDaCodFiscale("src/dati/utente.csv", selezionata.getCliente());
            System.out.println("Rispondi al commento di " + nomeCliente + ":");
            String risposta = scanner.nextLine();

            gestioneRecensioni.rispondiARisposta(ristorante, selezionata.getCliente(), risposta);
            System.out.println("Risposta inviata correttamente.");
       
    }
}

