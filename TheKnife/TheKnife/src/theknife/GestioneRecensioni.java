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
    ArrayList<Recensione> tutteRecensioni = Recensione.leggiTutteLeRecensioni();
    
    System.out.print("Nome ristorante: ");
    String nomeRistorante = scanner.nextLine();

    boolean found = false;
    for (Recensione rec : tutteRecensioni) {
        if (rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
            rec.getRistorante().equalsIgnoreCase(nomeRistorante)) {

            System.out.print("Nuovo testo: ");
            String nuovoTesto = scanner.nextLine();
            System.out.print("Nuovo voto (1-5): ");
            int nuoveStelle = Integer.parseInt(scanner.nextLine());

            rec.setTestoRecensione(nuovoTesto);
            rec.setStelle(nuoveStelle);
            found = true;
            break;
        }
    }

    if (found) {
        riscriviRecensioni(tutteRecensioni);
        System.out.println("Recensione aggiornata.");
    } else {
        System.out.println("Recensione non trovata.");
    }
}


public void eliminaRecensione(Cliente cliente, Scanner scanner) {
    ArrayList<Recensione> tutteRecensioni = Recensione.leggiTutteLeRecensioni();

    System.out.print("Nome ristorante: ");
    String nomeRistorante = scanner.nextLine();

    boolean removed = tutteRecensioni.removeIf(rec ->
        rec.getCliente().equalsIgnoreCase(cliente.getCodFiscale()) &&
        rec.getRistorante().equalsIgnoreCase(nomeRistorante)
    );

    if (removed) {
        riscriviRecensioni(tutteRecensioni);
        System.out.println("Recensione eliminata.");
    } else {
        System.out.println("Recensione non trovata.");
    }
}

    public void rispondiARisposta(String nomeRistorante, String cliente, String risposta) {
    ArrayList<Recensione> tutte = Recensione.cercaPerRistorante(nomeRistorante);
    for (Recensione rec : tutte) {
        if (rec.getCliente().equalsIgnoreCase(cliente)) {
            rec.setRisposta(risposta);
            System.out.println("Risposta salvata per la recensione di " + cliente);
            riscriviRecensioni(tutte); // Assicurati di riscrivere il file dopo aver aggiornato la risposta
            return;
        }
    }
    System.out.println("Recensione non trovata per il cliente: " + cliente);
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

}


