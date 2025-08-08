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
    String nomeRistorante="";
    do {
    	System.out.print("Nome del ristorante di cui vuoi modificare la recensione: ");
    	nomeRistorante = scanner.nextLine();
    }while(!GestioneUtenti.campoNonVuoto(nomeRistorante));
    
    for (Recensione rec : tutteRecensioni) {
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(nomeRistorante)) {
            System.out.print("Nuovo testo recensione (opzionale) : ");
            String nuovoTesto = scanner.nextLine();
            boolean controllo;
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
    System.out.println("Nessuna recensione trovata per questo ristorante.");
}



public void eliminaRecensione(Cliente cliente, Scanner scanner) {
    ArrayList<Recensione> tutteRecensioni = leggiTutteLeRecensioni();
    String nomeRistorante="";
    do {
    	System.out.print("Nome del ristorante di cui vuoi eliminare la recensione: ");
    	nomeRistorante = scanner.nextLine();
    }while(!GestioneUtenti.campoNonVuoto(nomeRistorante));
    
    Iterator<Recensione> iterator = tutteRecensioni.iterator();
    boolean removed = false;
    
    while (iterator.hasNext()) {
        Recensione rec = iterator.next();
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(nomeRistorante)) {
            iterator.remove();
            removed = true;
            break; // Esci dopo aver eliminato la prima (e unica) recensione
        }
    }

    if (removed) {
        riscriviRecensioni(tutteRecensioni);
        System.out.println("Recensione eliminata con successo.");
    } else {
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



