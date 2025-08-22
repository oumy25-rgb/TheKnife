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

/**
 * La classe <strong>GestioneMenu</strong> gestisce la persistenza dei menu
 * dei ristoranti su file CSV all’interno della cartella <code>src/dati/</code>.
 * <p>
 * Le operazioni principali sono:
 * <ul>
 *   <li>Scrittura del menu di un ristorante</li>
 *   <li>Verifica dell’esistenza di un menu</li>
 *   <li>Lettura del menu da file</li>
 * </ul>
 * Ogni riga del file rappresenta un {@link Piatto}.
 * </p>
 *
 * @see resources.Piatto
 * 
 * @author omema gharsellaoui
 * @author Giuseppina Salvati
 */

public class GestioneMenu {
    // Metodo per scrivere il menu di un ristorante in un file CSV

    /**
     * Salva il menu di un ristorante in un file CSV.
     * <p>
     * Se il file esiste già, viene sovrascritto.
     * Ogni piatto è salvato nella forma: <br>
     * <code>nome,descrizione,prezzo</code>
     * </p>
     *
     * @param nomeFile nome del file (senza percorso)
     * @param piatti lista di piatti da scrivere
     */
    public static void scriviMenu(String nomeFile, ArrayList<Piatto> piatti) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/dati/" + nomeFile, false))) {
        for (Piatto piatto : piatti) {
            writer.write(piatto.toCSV());
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del menu: " + e.getMessage());
    }
}
    /**
     * Controlla se esiste un file menu per un determinato ristorante.
     *
     * @param nome nome del file menu
     * @return true se il file esiste, false altrimenti
     */
    public static boolean cercaMenu(String nome) {
        
        File file = new File("src/dati", nome);
        if(file.exists()) {
        	return true;
        }else {
        	return false;
        }
        
    }
     /**
     * Legge un menu da un file CSV.
     * <p>
     * Ogni riga deve contenere tre campi separati da virgola: <br>
     * <code>nome,descrizione,prezzo</code>
     * </p>
     *
     * @param nomeFile nome del file CSV del menu
     * @return lista di {@link Piatto} caricati dal file
     */
    // Metodo per leggere il menu da un file CSV
    public static ArrayList<Piatto> leggiMenu(String nomeFile) {
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
            System.out.println("Il ristorante non ha nessun menù");
        }
        return menu;
    }
}


