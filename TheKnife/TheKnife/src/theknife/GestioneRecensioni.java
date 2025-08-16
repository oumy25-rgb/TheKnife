/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import static resources.GestioneFile.leggiTutteLeRecensioni;

/**
 *
 * @author HEW4K7Z2EA
 */
public class GestioneRecensioni {
    
     private final String filePath = "src/dati/recensioni.csv";

    public void aggiungiRecensione(Recensione rec) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.write(rec.toCSV());
        writer.newLine();
        writer.flush(); // Forza il salvataggio immediato
    } catch (IOException e) {
        System.err.println("ERRORE SALVATAGGIO: " + e.getMessage());
    }
}


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
    
    for (Recensione rec : tutteRecensioni) {
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(r.getNomeRistorante())) {
        	
            System.out.print("Nuovo testo recensione (opzionale) : ");
            String nuovoTesto = scanner.nextLine();
            String s="";
            
            do {
            	controllo = true;
            
            	System.out.print("Nuovo voto (1-5): ");
            	s = scanner.nextLine();
            	
            	if(GestioneUtenti.campoNonVuoto(s)) {
            		if(!s.equals("1") && !s.equals("2") && !s.equals("3") && !s.equals("4") && !s.equals("5")) {
                		controllo = false;
                		System.out.println("Numero di stelle inserite non valido, riprova.");
                	}
            	}
            	
            }while(!GestioneUtenti.campoNonVuoto(s) || !controllo);
            
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
        System.out.println("Risposta salvata per la recensione di " + cliente);
    } else {
        System.out.println("Recensione non trovata per il cliente: " + cliente);
    }
}

  

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
    
    public boolean recensioneEsistente(String nomeRistorante, String codiceFiscale) {
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



