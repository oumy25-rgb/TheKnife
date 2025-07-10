/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author HEW4K7Z2EA
 */
public class GestioneMenu {
    // Metodo per scrivere il menu di un ristorante in un file CSV
    public void scriviMenu(String nomeFile, ArrayList<Piatto> piatti) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            for (Piatto piatto : piatti) {
                writer.write(piatto.getNome() + "," + piatto.getDescrizione() + "," + piatto.getPrezzo());
                writer.newLine();
            }
            System.out.println("Menu scritto con successo nel file: " + nomeFile);
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del menu: " + e.getMessage());
        }
    }
    // Metodo per leggere il menu da un file CSV (opzionale)
    public ArrayList<Piatto> leggiMenu(String nomeFile) {
        ArrayList<Piatto> menu = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dati/" + nomeFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 3) {
                    String nome = tokens[0];
                    String descrizione = tokens[1];
                    double prezzo = Double.parseDouble(tokens[2]);
                    menu.add(new Piatto(nome, descrizione, prezzo));
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del menu: " + e.getMessage());
        }
        return menu;
    }

    
}
