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
import java.util.Locale;

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
    public Recensione(String nomeRistorante, String codiceFiscale, String testoRecensione, 
                     double stelle, String data, String risposta) {
        this.nomeRistorante = nomeRistorante;
        this.codiceFiscale = codiceFiscale;
        this.testoRecensione = testoRecensione;
        this.stelle = stelle;
        this.data = data;
        this.risposta = risposta;
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

    public String toCSV() {
    return String.format(Locale.US, "%s,%s,%s,%.1f,%s,%s",
        nomeRistorante != null ? nomeRistorante : "",
        codiceFiscale != null ? codiceFiscale : "",
        testoRecensione != null ? testoRecensione.replace(",", ";") : "",
        stelle,   // qui stelle è double, formato con 1 cifra decimale
        data != null ? data : "",
        (risposta != null && !risposta.trim().isEmpty()) ? risposta.replace(",", ";") : ""
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



    public static ArrayList<Recensione> leggiTutteLeRecensioni() {
    ArrayList<Recensione> recensioni = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split(",", -1);  // -1 mantiene i valori vuoti
            if (tokens.length >= 6) {
                String nomeRistorante = tokens[0]; 
                String codiceFiscale = tokens[1];
                String testoRecensione = tokens[2];
                
                // Gestione valore stelle con eventuale 0 dopo
                String stelleStr = tokens[3];
                if (tokens.length > 5 && tokens[4].equals("0")) {
                    stelleStr = tokens[3] + "." + tokens[4]; // Trasforma "4,0" in "4.0"
                }
                double stelle = Double.parseDouble(stelleStr.replace(',', '.')); 
                
                // Data sarà tokens[5] se c'è lo 0, altrimenti tokens[4]
                String data = (tokens.length > 5 && tokens[4].equals("0")) ? tokens[5] : tokens[4];
                
                String risposta = tokens.length > 6 ? tokens[6] : "";

                recensioni.add(new Recensione(nomeRistorante, codiceFiscale, testoRecensione, stelle, data, risposta));
            }
        }
    } catch (Exception e) {
        System.err.println("Errore lettura recensioni: " + e.getMessage());
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

