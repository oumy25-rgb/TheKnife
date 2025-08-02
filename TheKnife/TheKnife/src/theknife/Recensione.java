/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Recensione {
    private String nomeRistorante;
    private String codiceFiscale;
    private String indirizzo;
    private String citta;
    private double stelle;
    private String testoRecensione;
    private String data;
    private String risposta; // NUOVO CAMPO

    // Costruttore completo
    public Recensione(String nomeRistorante, String codiceFiscale, String indirizzo, String citta,
                      double stelle, String testoRecensione, String data, String risposta) {
        this.nomeRistorante = nomeRistorante;
        this.codiceFiscale = codiceFiscale;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.stelle = stelle;
        this.testoRecensione = testoRecensione;
        this.data = data;
        this.risposta = risposta;
    }

    // Costruttore senza risposta (opzionale, utile per vecchi dati)
    public Recensione(String nomeRistorante, String codiceFiscale, String indirizzo, String citta,
                      double stelle, String testoRecensione, String data) {
        this(nomeRistorante, codiceFiscale, indirizzo, citta, stelle, testoRecensione, data, "");
    }

    // Getters e Setters
    public String getNomeRistorante() { return nomeRistorante; }
    public void setNomeRistorante(String nomeRistorante) { this.nomeRistorante = nomeRistorante; }

    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public double getStelle() { return stelle; }
    public void setStelle(double stelle) { this.stelle = stelle; }

    public String getTestoRecensione() { return testoRecensione; }
    public void setTestoRecensione(String testoRecensione) { this.testoRecensione = testoRecensione; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getRisposta() { return risposta; } // NUOVO GETTER
    public void setRisposta(String risposta) { this.risposta = risposta; } // NUOVO SETTER
    
    public String getCliente() {
    return getCodiceFiscale(); // già presente
}

    public String getRistorante() {
        return getNomeRistorante(); // già presente
    }

    // Formattazione per scrittura su CSV
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%.1f,%s,%s,%s",
            nomeRistorante,
            codiceFiscale,
            indirizzo,
            citta,
            stelle,
            testoRecensione.replace(",", ";"),
            data,
            risposta.replace(",", ";")
        );
    }

    // Scrittura su file
    public void scriviSuFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/recensioni.csv", true))) {
            writer.write(this.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura della recensione: " + e.getMessage());
        }
    }

    // Lettura da file
    public static ArrayList<Recensione> leggiTutteLeRecensioni() {
        ArrayList<Recensione> recensioni = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",", 8); // ora ci sono 8 campi
                if (tokens.length >= 7) {
                    String risposta = tokens.length == 8 ? tokens[7] : ""; // risposta può essere vuota
                    recensioni.add(new Recensione(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        Double.parseDouble(tokens[4]),
                        tokens[5],
                        tokens[6],
                        risposta
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura delle recensioni: " + e.getMessage());
        }

        return recensioni;
    }

    public static ArrayList<Recensione> cercaPerRistorante(String nomeRistorante) {
        ArrayList<Recensione> tutte = leggiTutteLeRecensioni();
        ArrayList<Recensione> filtrate = new ArrayList<>();

        for (Recensione r : tutte) {
            if (r.getNomeRistorante().equalsIgnoreCase(nomeRistorante)) {
                filtrate.add(r);
            }
        }
        return filtrate;
    }

    public static ArrayList<Recensione> cercaPerCliente(String codFiscale) {
        ArrayList<Recensione> tutte = leggiTutteLeRecensioni();
        ArrayList<Recensione> filtrate = new ArrayList<>();

        for (Recensione r : tutte) {
            if (r.getCodiceFiscale().equalsIgnoreCase(codFiscale)) {
                filtrate.add(r);
            }
        }
        return filtrate;
    }

    public static void riscriviRecensioni(ArrayList<Recensione> recensioni) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/recensioni.csv"))) {
            for (Recensione r : recensioni) {
                writer.write(r.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nella riscrittura delle recensioni: " + e.getMessage());
        }
    }

    // Visualizzazione per cliente
    public String visualizzaPerCliente() {
        String base = "Ristorante: " + nomeRistorante +
                      "\nIndirizzo: " + indirizzo + ", " + citta +
                      "\nVoto: " + stelle + " stelle" +
                      "\nRecensione: \"" + testoRecensione + "\"" +
                      "\nData: " + data;
        if (risposta != null && !risposta.isEmpty()) {
            base += "\n[Risposta del ristoratore]: \"" + risposta + "\"";
        }
        return base;
    }

    // Visualizzazione per ristoratore
    public String visualizzaPerRistoratore() {
        return "Cliente: " + codiceFiscale +
               "\nVoto: " + stelle + " stelle" +
               "\nRecensione: \"" + testoRecensione + "\"" +
               "\nData: " + data +
               (risposta != null && !risposta.isEmpty() ? "\nRisposta: \"" + risposta + "\"" : "");
    }

    @Override
    public String toString() {
        return visualizzaPerCliente();
    }
}


