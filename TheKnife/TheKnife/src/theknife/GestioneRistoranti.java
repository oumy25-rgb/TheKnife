/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.util.ArrayList;
import java.util.Scanner;
import resources.GestioneFile;
import resources.GestioneMenu;
import resources.Piatto;

public class GestioneRistoranti {
    private ArrayList<Ristorante> ristoranti;
    private GestioneFile gf;

    public GestioneRistoranti() {
        ristoranti = new ArrayList<>();
        gf = new GestioneFile();
        caricaRistoranti(); // Carica i ristoranti all'inizializzazione
    }

    public void aggiungiRistorante(Ristorante ristorante) {
        if (ristorante != null) {
            ristoranti.add(ristorante);
        } else {
            System.out.println("Errore: il ristorante non può essere nullo.");
        }
    }

    public ArrayList<Ristorante> cercaRistoranti(String cuisine, String location, double priceMin, double priceMax, boolean delivery, boolean prenotazioneOnline, int stelleMin) {
        ArrayList<Ristorante> risultati = new ArrayList<>();
        ArrayList<String> datiRistoranti = gf.leggiDaFile("src/dati/ristoranti.csv");
        
        for (String linea : datiRistoranti) {
            String[] tokens = gf.dividereCsv(linea);
            if (tokens.length < 14) {
                System.out.println("Errore: dati ristorante incompleti nella linea: " + linea);
                continue; // Salta questa linea se i dati sono incompleti
            }

            Ristorante r = new Ristorante(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], 
                Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]), tokens[7], tokens[8], 
                tokens[9], tokens[10], true, tokens[12], tokens[13], new ArrayList<>());

            // Imposta matches a false di default
            boolean matches = false;

            // Controllo per tipo di cucina
            if (cuisine == null || cuisine.isEmpty() || r.getCuisine().equalsIgnoreCase(cuisine)) {
                // Controllo per locazione geografica
                if (location == null || location.isEmpty() || r.getLocation().equalsIgnoreCase(location)) {
                    // Controllo per fascia di prezzo
                    double prezzo = Double.parseDouble(r.getPrice());
                    if (priceMin < 0 || priceMax < 0 || (prezzo >= priceMin && prezzo <= priceMax)) {
                        // Controllo per disponibilità del servizio di delivery
                        if (!delivery || !r.getPhoneNumber().isEmpty()) {
                            // Controllo per disponibilità del servizio di prenotazione online
                            if (!prenotazioneOnline || !r.getUrl().isEmpty()) {
                                // Controllo per media del numero di stelle
                                if (stelleMin <= 0 || r.getMediaStelle() >= stelleMin) {
                                    matches = true; // Se tutte le condizioni sono soddisfatte, imposta matches a true
                                }
                            }
                        }
                    }
                }
            }

            // Se tutti i criteri sono soddisfatti, aggiungi il ristorante ai risultati
            if (matches) {
                risultati.add(r);
            } 
        }
        
        return risultati;
    }

    public void creaEMenuRistorante(String nomeRistorante) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Piatto> menu = new ArrayList<>();
    String risposta;

    do {
        try {
            System.out.print("Nome del piatto: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) throw new IllegalArgumentException("Il nome del piatto non può essere vuoto.");

            System.out.print("Descrizione del piatto: ");
            String descrizione = scanner.nextLine().trim();
            if (descrizione.isEmpty()) throw new IllegalArgumentException("La descrizione non può essere vuota.");

            System.out.print("Prezzo del piatto: ");
            double prezzo = Double.parseDouble(scanner.nextLine());
            if (prezzo < 0) throw new IllegalArgumentException("Il prezzo non può essere negativo.");

            Piatto piatto = new Piatto(nome, descrizione, prezzo);
            menu.add(piatto);

            System.out.println("✅ Piatto aggiunto con successo, grazie!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Errore: inserisci un numero valido per il prezzo.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Errore: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Errore imprevisto: " + e.getMessage());
        }

        System.out.print("Vuoi aggiungere un altro piatto? (s/n): ");
        risposta = scanner.nextLine();

    } while (risposta.equalsIgnoreCase("s"));

    try {
        new GestioneMenu().scriviMenu(nomeRistorante + "Menu.csv", menu);
        System.out.println("Menu creato e salvato con successo.");
    } catch (Exception e) {
        System.out.println("Errore durante il salvataggio del menu: " + e.getMessage());
    }
}


    public Ristorante cercaRistorantePerNome(String nome) {
        for (Ristorante ristorante : ristoranti) {
            if (ristorante.getName().equalsIgnoreCase(nome)) {
                return ristorante; // Restituisce il ristorante se trovato
            }
        }
        return null; // Se non trovato
    }

    // Nuovo metodo per aggiungere un piatto al menu
    public void aggiungiPiattoAlMenu(String nomeRistorante, Piatto piatto) {
        Ristorante ristorante = cercaRistorantePerNome(nomeRistorante);
        if (ristorante != null) {
            ristorante.aggiungiPiatto(piatto);
            System.out.println("Piatto aggiunto al menu di " + nomeRistorante);
        } else {
            System.out.println("Ristorante non trovato.");
        }
    }

    // Nuovo metodo per rimuovere un piatto dal menu
    public void rimuoviPiattoDalMenu(String nomeRistorante, String nomePiatto) {
        Ristorante ristorante = cercaRistorantePerNome(nomeRistorante);
        if (ristorante != null) {
            ristorante.rimuoviPiatto(nomePiatto);
            System.out.println("Piatto rimosso dal menu di " + nomeRistorante);
        } else {
            System.out.println("Ristorante non trovato.");
        }
    }
    
    private void caricaRistoranti() {
        ArrayList<String> datiRistoranti = gf.leggiDaFile("src/dati/ristoranti.csv");
        for (String linea : datiRistoranti) {
            String[] tokens = gf.dividereCsv(linea);
            if (tokens.length < 14) {
                System.out.println("Errore: dati ristorante incompleti nella linea: " + linea);
                continue; // Salta questa linea se i dati sono incompleti
            }
            Ristorante r = new Ristorante(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], 
                Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]), tokens[7], tokens[8], 
                tokens[9], tokens[10], true, tokens[12], tokens[13], new ArrayList<>());
            ristoranti.add(r); // Aggiungi il ristorante alla lista
        }
    }
    
}

