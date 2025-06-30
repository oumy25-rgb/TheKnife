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

/**
 *
 * @author HEW4K7Z2EA
 */
public class GestioneRistoranti {
    private ArrayList<Ristorante> ristoranti;
    private GestioneFile gf;

    public GestioneRistoranti() {
        ristoranti = new ArrayList<>();
        gf = new GestioneFile();
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
            if (tokens.length < 13) {
                System.out.println("Errore: dati ristorante incompleti nella linea: " + linea);
                continue; // Salta questa linea se i dati sono incompleti
            }

            
                Ristorante r = new Ristorante(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], 
                    Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]), tokens[7], tokens[8], 
                    tokens[9], tokens[10], true, tokens[12], tokens[13], new ArrayList<>());
                System.out.println("Ristorante "+r.getName()+" della citta di "+r.getLocation());
                boolean matches = true;

                // Controllo per tipo di cucina
                if (cuisine != null && !cuisine.isEmpty() && !r.getCuisine().equalsIgnoreCase(cuisine)) {
                    matches = false;
                }

                // Controllo per locazione geografica
                if (location != null && !location.isEmpty() && !r.getLocation().equalsIgnoreCase(location)) {
                    matches = false;
                }

                // Controllo per fascia di prezzo
                if (priceMin >= 0 && priceMax >= 0 && (Double.parseDouble(r.getPrice()) < priceMin || Double.parseDouble(r.getPrice()) > priceMax)) {
                    matches = false;
                }

                // Controllo per disponibilità del servizio di delivery
                if (delivery && !r.getPhoneNumber().isEmpty()) {
                    matches = false;
                }

                // Controllo per disponibilità del servizio di prenotazione online
                if (prenotazioneOnline && !r.getUrl().isEmpty()) {
                    matches = false;
                }

                // Controllo per media del numero di stelle
                if (stelleMin > 0 && r.getMediaStelle() < stelleMin) {
                    matches = false;
                }

                // Se tutti i criteri sono soddisfatti, aggiungi il ristorante ai risultati
                if (matches) {
                    System.out.println("Valore coincide con ricerca");
                    risultati.add(r);
                }else{
                    System.out.println("Scartato");
                }
        }
        return risultati;
    }

    public void creaEMenuRistorante(String nomeRistorante) {
        ArrayList<Piatto> menuRistorante = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Creazione menu per " + nomeRistorante);
        int numPiatti = 0;

        // Controllo per il numero di piatti
        while (true) {
            System.out.println("Quanti piatti vuoi aggiungere?");
            try {
                numPiatti = Integer.parseInt(scanner.nextLine());
                if (numPiatti > 0) {
                    break; // Esci dal ciclo se il numero è valido
                } else {
                    System.out.println("Il numero di piatti deve essere maggiore di zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero valido.");
            }
        }

        for (int i = 0; i < numPiatti; i++) {
            System.out.println("Piatto " + (i + 1) + ":");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Descrizione: ");
            String desc = scanner.nextLine();
            double prezzo = 0.0;

            // Controllo per il prezzo
            while (true) {
                System.out.print("Prezzo: ");
                try {
                    prezzo = Double.parseDouble(scanner.nextLine());
                    if (prezzo >= 0) {
                        break; // Esci dal ciclo se il prezzo è valido
                    } else {
                        System.out.println("Il prezzo deve essere un valore positivo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Errore: inserisci un numero valido per il prezzo.");
                }
            }

            menuRistorante.add(new Piatto(nome, desc, prezzo));
        }

        GestioneMenu gestioneMenu = new GestioneMenu();
        gestioneMenu.scriviMenu(nomeRistorante.toLowerCase() + ".csv", menuRistorante);
    }
}
