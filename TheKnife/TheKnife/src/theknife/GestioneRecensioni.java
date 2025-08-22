
package theknife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import static resources.GestioneFile.leggiTutteLeRecensioni;

/**
 * La classe <strong>GestioneRecensioni</strong> gestisce tutte le operazioni
 * relative alle recensioni lasciate dai clienti sui ristoranti.
 * <p>
 * Le principali funzionalità sono:
 * <ul>
 *   <li>Aggiungere nuove recensioni</li>
 *   <li>Modificare recensioni già inserite</li>
 *   <li>Eliminare recensioni esistenti</li>
 *   <li>Rispondere alle recensioni (funzionalità per i ristoratori)</li>
 *   <li>Verificare se una recensione esiste già</li>
 * </ul>
 * I dati vengono salvati nel file CSV <code>recensioni.csv</code>.
 * </p>
 *
 * @see Recensione
 * @see Cliente
 * @see resources.GestioneFile
 * 
 * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */
public class GestioneRecensioni {
     /** Percorso del file CSV contenente le recensioni. */
     private final String filePath = "src/dati/recensioni.csv";
	
     /**
     * Aggiunge una nuova recensione al file delle recensioni.
     *
     * @param rec recensione da salvare
     */
    public void aggiungiRecensione(Recensione rec) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.write(rec.toCSV());
        writer.newLine();
        writer.flush(); // Forza il salvataggio immediato
    } catch (IOException e) {
        System.err.println("ERRORE SALVATAGGIO: " + e.getMessage());
    }
}

 /**
     * Permette a un cliente di modificare una delle proprie recensioni.
     * <p>
     * Viene mostrata la lista dei ristoranti recensiti e l’utente può:
     * <ul>
     *   <li>modificare il testo della recensione</li>
     *   <li>cambiare il voto (da 1 a 5 stelle)</li>
     * </ul>
     * </p>
     *
     * @param cliente cliente che vuole modificare la recensione
     * @param scanner oggetto per la lettura da console
     */
	
public void modificaRecensione(Cliente cliente, Scanner scanner) {
    ArrayList<Recensione> tutteRecensioni = leggiTutteLeRecensioni();
    ArrayList<Recensione> mie = Recensione.cercaPerCliente(cliente.getCodFiscale());

    int i=1; int scegli=-1;
	boolean controllo;
	if(!mie.isEmpty()) {
		System.out.println(" \nRistoranti che ho recensito:");
		System.out.println("----------------------------------------------------------------------");
		for(Recensione r : mie) {
			System.out.print((i++)+") "+r+"\n");
			System.out.println("----------------------------------------------------------------------");
		}
	
		do {
		    controllo = true;
		    System.out.print("Quale recensione vuoi modificare? ");
		    try {
		        scegli = Integer.parseInt(scanner.nextLine());
		        if (scegli < 1 || scegli > mie.size()) {
		            System.out.println("Scelta non presente, riprova.");
		            controllo = false;
		        }
		    } catch (NumberFormatException e) {
		        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
		        controllo = false;
		    }
		} while (!controllo);
		
		Recensione r = mie.get(scegli - 1);
		String nuovoTesto;
		
    for (Recensione rec : tutteRecensioni) {
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(r.getNomeRistorante())) {
        	
        	do {
        		controllo = true;
	            System.out.print("Nuovo testo recensione (opzionale) : ");
	            nuovoTesto = scanner.nextLine().trim();
	            
	            if(nuovoTesto.contains(",")) {
        			System.out.println("ATTENZIONE: Hai inserito il carattere \",\"  che non è consentito, riprova.");
        			controllo = false;
        		}

        	}while(!controllo);
        	
            String s="";
            
            do {
            	controllo = true;
            
            	System.out.print("Nuovo voto (1-5): ");
            	s = scanner.nextLine().trim();
            	
            	if(GestioneUtenti.campoNonVuoto(s)) {
            		
	            		if(!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4") && !s.equals("5")) {
	                		controllo = false;
	                		System.out.println("Numero di stelle inserite non valido, riprova.");
	                	}
            		
            	}else
            		controllo=false;
            }while(!controllo);
            
            double nuoveStelle = Double.parseDouble(s);
            
            rec.setTestoRecensione(nuovoTesto);
            rec.setStelle(nuoveStelle);
            riscriviRecensioni(tutteRecensioni);
            System.out.println("Recensione modificata con successo.");
            return;
        }
    }
    }else {
		System.out.println("Nessun ristorante trovato vicino a me.");
	}
}

/**
     * Permette a un cliente di eliminare una recensione precedentemente inserita.
     *
     * @param cliente cliente che vuole eliminare la recensione
     * @param scanner oggetto per la lettura da console
     */

public void eliminaRecensione(Cliente cliente, Scanner scanner) {
    ArrayList<Recensione> tutteRecensioni = leggiTutteLeRecensioni();
    ArrayList<Recensione> mie = Recensione.cercaPerCliente(cliente.getCodFiscale());

    int i=1; int scegli=-1;
	boolean controllo;
	if(!mie.isEmpty()) {
		System.out.println(" \nRistoranti che ho recensito:");
		System.out.println("----------------------------------------------------------------------");
		for(Recensione r : mie) {
			System.out.print((i++)+") "+r+"\n");
			System.out.println("----------------------------------------------------------------------");
		}
	
		do {
		    controllo = true;
		    System.out.print("Quale recensione vuoi eliminare? ");
		    try {
		        scegli = Integer.parseInt(scanner.nextLine());
		        if (scegli < 1 || scegli > mie.size()) {
		            System.out.println("Scelta non presente, riprova.");
		            controllo = false;
		        }
		    } catch (NumberFormatException e) {
		        System.out.println("Formato non valido, riprova."); // gestisce anche il caso in cui viene lasciata vuota
		        controllo = false;
		    }
		} while (!controllo);
		
		Recensione r = mie.get(scegli - 1);
    
    Iterator<Recensione> iterator = tutteRecensioni.iterator();
    boolean removed = false;
    
    while (iterator.hasNext()) {
        Recensione rec = iterator.next();
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(r.getNomeRistorante())) {
            iterator.remove();
            removed = true;
            break; // Esci dopo aver eliminato la prima (e unica) recensione
        }
    }

    if (removed) {
        riscriviRecensioni(tutteRecensioni);
        System.out.println("Recensione eliminata con successo.");
    }else
    	System.out.println("Errore nella rimozione.");
	}else {
        System.out.println("Nessuna recensione trovata per questo ristorante.");
    }
}
  /**
     * Consente al proprietario di un ristorante di rispondere a una recensione lasciata da un cliente.
     *
     * @param nomeRistorante nome del ristorante
     * @param cliente codice fiscale del cliente che ha scritto la recensione
     * @param risposta testo della risposta
     */
 public void rispondiARisposta(String nomeRistorante, String cliente, String risposta) {
    ArrayList<Recensione> tutte = resources.GestioneFile.leggiTutteLeRecensioni(); // leggi TUTTE
    boolean found = false;
    for (Recensione rec : tutte) {
        if (rec.getNomeRistorante().equalsIgnoreCase(nomeRistorante) &&
            rec.getCodiceFiscale().equalsIgnoreCase(cliente)) {
            rec.setRisposta(risposta);
            found = true;
            break;
        }
    }
    if (found) {
        riscriviRecensioni(tutte); // riscrive tutto usando r.toCSV()
       
    } else {
        System.out.println("Recensione non trovata per il cliente: " + cliente);
    }
}

   /**
     * Sovrascrive completamente il file delle recensioni con una nuova lista aggiornata.
     *
     * @param recensioni lista di recensioni da salvare
     */

    private void riscriviRecensioni(ArrayList<Recensione> recensioni) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        for (Recensione r : recensioni) {
            writer.write(r.toCSV());
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore riscrittura file recensioni: " + e.getMessage());
    }
}
     /**
     * Verifica se un cliente ha già recensito un determinato ristorante.
     *
     * @param nomeRistorante nome del ristorante
     * @param codiceFiscale codice fiscale del cliente
     * @return true se la recensione esiste già, false altrimenti
     */
	
    public static boolean recensioneEsistente(String nomeRistorante, String codiceFiscale) {
    ArrayList<Recensione> tutteRecensioni = leggiTutteLeRecensioni();
    for (Recensione rec : tutteRecensioni) {
        if (rec.getNomeRistorante().equalsIgnoreCase(nomeRistorante) && 
            rec.getCodiceFiscale().equalsIgnoreCase(codiceFiscale)) {
            return true; // La recensione esiste già
        }
    }
    return false; // Nessuna recensione trovata
}


}





