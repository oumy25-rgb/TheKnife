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
    private String ristorante;
    private String cliente;
    private String data;
    private String testoRecensione;
    private int stelle;
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

    public String getCliente() {
        return cliente;
    }

    public String getData() {
        return data;
    }

    public String getTestoRecensione() {
        return testoRecensione;
    }

    public int getStelle() {
        return stelle;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public void setTestoRecensione(String testoRecensione) {
        this.testoRecensione = testoRecensione;
    }

    public void setStelle(int stelle) {
        this.stelle = stelle;
    }

    @Override
    public String toString() {
        return visualizzaPerCliente();
    }

    public String toCSV() {
        return String.join(",",
                ristorante,
                cliente,
                data,
                testoRecensione,
                String.valueOf(stelle),
                (risposta != null ? risposta : ""));
    }

    public static ArrayList<Recensione> cercaPerRistorante(String nomeRistorante) {
        ArrayList<Recensione> recensioni = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 5 && tokens[0].equalsIgnoreCase(nomeRistorante)) {
                    recensioni.add(new Recensione(tokens[0], tokens[1], tokens[2],
                            tokens[3], Integer.parseInt(tokens[4]),
                            tokens.length > 5 ? tokens[5] : ""));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura delle recensioni: " + e.getMessage());
        }
        return recensioni;
    }

    public static ArrayList<Recensione> cercaPerCliente(String codFiscale) {
        ArrayList<Recensione> recensioni = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/recensioni.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 5 && tokens[1].equalsIgnoreCase(codFiscale)) {
                    recensioni.add(new Recensione(tokens[0], tokens[1], tokens[2],
                            tokens[3], Integer.parseInt(tokens[4]),
                            tokens.length > 5 ? tokens[5] : ""));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura delle recensioni: " + e.getMessage());
        }
        return recensioni;
    }

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
                String[] tokens = line.split(",");
                if (tokens.length >= 5) {
                    recensioni.add(new Recensione(tokens[0], tokens[1], tokens[2], tokens[3],
                            Integer.parseInt(tokens[4]), tokens.length > 5 ? tokens[5] : ""));
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura delle recensioni: " + e.getMessage());
        }
        return recensioni;
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

    public String visualizzaPerCliente() {
        String output = "Ristorante: " + ristorante +
                        "\nVoto: " + stelle + " stelle" +
                        "\nTesto: \"" + testoRecensione + "\"" +
                        "\nData: " + data;
        if (risposta != null && !risposta.trim().isEmpty()) {
            output += "\nRisposta del ristoratore: \"" + risposta + "\"";
        }
        return output;
    }

    public String visualizzaPerRistoratore() {
        return testoRecensione + " (" + stelle + " stelle)";
    }
    
    
    
}
