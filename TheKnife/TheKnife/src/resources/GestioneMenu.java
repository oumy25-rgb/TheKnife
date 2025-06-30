/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources;

import java.io.BufferedWriter;
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
        ArrayList<Piatto> piatti = new ArrayList<>();
        // Implementa la logica per leggere il file CSV e popolare l'ArrayList di piatti
        return piatti;
    }

    
}
