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
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class Recensione {
    private String ristorante, cliente, data,testoRecensione;
    int stelle;
    private String risposta;

    public Recensione(String ristorante, String cliente, String data, String testoRecensione, int stelle, String risposta) {
        this.ristorante = ristorante;
        this.cliente = cliente;
        this.data = data;
        this.testoRecensione = testoRecensione;
        this.stelle = stelle;
        this.risposta = risposta;
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

    public void setTestoRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
    }

    public void setStelle(int stelle) {
        this.stelle = stelle;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }
    
    
    // Metodo per convertire la recensione in una stringa per il salvataggio
    /*public String toCSV() {
        return ristorante + "," + cliente + "," + data + "," + testoRecensione + "," + stelle + "," + (risposta != null ? risposta : "");
    }*/
    
    private static void aggiungiRecensione(Utente cliente, Ristorante ristorante, String testo, int stelle, String risposta) {
    Recensione nuovaRecensione = new Recensione(ristorante.getName(), cliente.getCodFiscale(), java.time.LocalDate.now().toString(), testo, stelle, null);
    ristorante.aggiungiRecensione(nuovaRecensione);
    
    // Salva la recensione nel file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/recensioni.csv", true))) {
        writer.write(nuovaRecensione.toCSV());
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Errore durante il salvataggio della recensione: " + e.getMessage());
    }
}

    
    // Metodo per visualizzare le recensioni
    public static void visualizzaRecensioni(ArrayList<Recensione> recensioni) {
        for (Recensione r : recensioni) {
            System.out.println(r.getTestoRecensione());
        }
    }
    
   
    public void scriviSuFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/recensioni.csv", true))) {
        writer.write(toCSV());
        writer.newLine();
        System.out.println("Recensione scritta: " + toCSV()); // Debug
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura della recensione: " + e.getMessage());
    }
}

    
    
    public static ArrayList<Recensione> cercaPerRistorante(String nomeRistorante) {
        ArrayList<Recensione> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(nomeRistorante)) {
                    result.add(new Recensione(parts[0], parts[1], parts[2], parts[3], 
                            Integer.parseInt(parts[4]), parts.length > 5 ? parts[5] : null));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file: " + e.getMessage());
        }
        return result;
    }
    
    public static ArrayList<Recensione> cercaPerCliente(String codFiscale) {
    ArrayList<Recensione> recensioni = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length >= 5 && tokens[1].equalsIgnoreCase(codFiscale)) {
                Recensione rec = new Recensione(tokens[0], tokens[1], tokens[2], 
                                                tokens[3], Integer.parseInt(tokens[4]), 
                                                tokens.length > 5 ? tokens[5] : null);
                recensioni.add(rec);
                System.out.println("Recensione letta: " + rec.toCSV()); // Debug
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file: " + e.getMessage());
    }
    return recensioni;
}


    
    public String toCSV() {
        return String.join(",",
                ristorante,
                cliente,
                data,
                testoRecensione,
                String.valueOf(stelle),
                risposta != null ? risposta : "");
    }
   
    
    

   public static ArrayList<Recensione> leggiRecensioniDaFile(String nomeRistorante) {
    ArrayList<Recensione> recensioni = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length >= 5 && tokens[0].toLowerCase().contains(nomeRistorante.toLowerCase())) {
                recensioni.add(new Recensione(tokens[0], tokens[1], tokens[2], 
                    tokens[3], Integer.parseInt(tokens[4]), tokens.length > 5 ? tokens[5] : null));
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file: " + e.getMessage());
    }
    return recensioni;
}

    
    

}
