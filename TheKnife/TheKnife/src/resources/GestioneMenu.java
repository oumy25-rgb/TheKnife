/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class GestioneMenu {
    // Metodo per scrivere il menu di un ristorante in un file CSV
    public void scriviMenu(String nomeFile, ArrayList<Piatto> piatti) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/" + nomeFile, false))) {
        for (Piatto piatto : piatti) {
            writer.write(piatto.toCSV());
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del menu: " + e.getMessage());
    }
}

    public static boolean cercaMenu(String nome) {
        
        File file = new File("src/dati", nome);
        if(file.exists()) {
        	return true;
        }else {
        	return false;
        }
        
    }

    // Metodo per leggere il menu da un file CSV
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

