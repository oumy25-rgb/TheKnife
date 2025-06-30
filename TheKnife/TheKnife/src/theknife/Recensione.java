/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class Recensione {
    private String ristorante, cliente, data,testoRecensione;
    int stelle;

    public Recensione(String ristorante, String cliente, String data, String testoRecensione, int stelle) {
        this.ristorante = ristorante;
        this.cliente = cliente;
        this.data = data;
        this.testoRecensione = testoRecensione;
        this.stelle=stelle;
    }

    public String getRistorante() {
        return ristorante;
    }

    public void setRistorante(String ristorante) {
        this.ristorante = ristorante;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTestoRecensione() {
        return testoRecensione;
    }

    public void setRecensione(String testpRecensione) {
        this.testoRecensione = testoRecensione;
    }

    public int getStelle() {
        return stelle;
    }
    
     // Metodo per aggiungere una recensione a una lista di recensioni
    public static void aggiungiRecensione(ArrayList<Recensione> recensioni, Recensione recensione) {
        recensioni.add(recensione);
        // Scrivi la recensione nel file
        GestioneFile gf = new GestioneFile(); // Assicurati di avere un'istanza di GestioneFile
        gf.scriviRecensione("recensioni.csv", recensione);
    }
    // Metodo per visualizzare le recensioni
    public static void visualizzaRecensioni(ArrayList<Recensione> recensioni) {
        for (Recensione r : recensioni) {
            System.out.println(r.getTestoRecensione());
        }
    }
    
    public void scriviRecensioneSuFile(String nomeFile) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile, true))) {
        writer.write(ristorante + "," + cliente + "," + data + "," + testoRecensione + "," + stelle);
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura della recensione: " + e.getMessage());
    }
}

}
