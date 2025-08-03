/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.util.Scanner;
import java.util.ArrayList;
import resources.Piatto;
import resources.GestioneFile;

public class Ristoratore extends Utente {
    private Ristorante ristorante;

    public Ristoratore(String nome, String cognome, String codFiscale, String username, String password,
                       String dataNascita, String luogoDomicilio, Ristorante ristorante) {
        super(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, "ristoratore");
        this.ristorante = ristorante;
    }

    public void caricaRistoranteAssociato(GestioneRistoranti gestioneRistoranti) {
    if (this.ristorante != null) {
        return; // Già associato
    }
    
    String nomeRistorante = GestioneFile.cercaRistoranteDaProprietario("src/dati/proprietari.csv", this.getCodFiscale());
    if (nomeRistorante != null) {
        Ristorante r = gestioneRistoranti.cercaRistorantePerNome(nomeRistorante);
        if (r != null) {
            this.ristorante = r;
            System.out.println("Ristorante associato caricato: " + ristorante.getName());
        }
    }
}


    public void mostraMenu(GestioneRistoranti gestioneRistoranti, GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        int scelta;
        do {
            System.out.println("===== MENU RISTORATORE =====");
            System.out.println("1. Aggiungi un ristorante");
            System.out.println("2. Visualizza riepilogo delle recensioni");
            System.out.println("3. Visualizza recensioni");
            System.out.println("4. Rispondi a una recensione");
            System.out.println("5. Crea il menu del ristorante");
            System.out.println("6. Aggiungi un piatto al menu");
            System.out.println("7. Rimuovi un piatto dal menu");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    aggiungiRistorante(gestioneRistoranti, scanner);
                    break;
                case 2:
                    visualizzaRiepilogo();
                    break;
                case 3:
                    visualizzaRecensioni();
                    break;
                case 4:
                    rispondiARecensione(gestioneRecensioni, scanner);
                    break;
                case 5:
                    gestioneRistoranti.creaEMenuRistorante(ristorante.getName());
                    ristorante.caricaMenuRistorante();
                    break;
                case 6:
                    System.out.print("Nome del piatto: ");
                    String nomePiatto = scanner.nextLine();
                    System.out.print("Descrizione del piatto: ");
                    String descrizionePiatto = scanner.nextLine();
                    System.out.print("Prezzo del piatto: ");
                    double prezzoPiatto = Double.parseDouble(scanner.nextLine());

                    Piatto nuovoPiatto = new Piatto(nomePiatto, descrizionePiatto, prezzoPiatto);
                    ristorante.aggiungiPiatto(nuovoPiatto);
                    break;
                case 7:
                    System.out.print("Nome del piatto da rimuovere: ");
                    String piattoDaRimuovere = scanner.nextLine();
                    ristorante.rimuoviPiatto(piattoDaRimuovere);
                    break;
                case 0:
                    System.out.println("Logout effettuato.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (scelta != 0);
    }

    private void aggiungiRistorante(GestioneRistoranti gestioneRistoranti, Scanner scanner) {
        System.out.println("Inserisci i dettagli del ristorante:");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Indirizzo: ");
        String address = scanner.nextLine();
        System.out.print("Città: ");
        String city = scanner.nextLine();
        System.out.print("Nazione: ");
        String nation = scanner.nextLine();
        System.out.print("Prezzo medio: ");
        String price = scanner.nextLine();
        System.out.print("Tipo di cucina: ");
        String cuisine = scanner.nextLine();
        System.out.print("Servizio di Delivery? (true/false) : ");
        String delivery = scanner.nextLine();
        System.out.print("Servizio di Prenotazione Online? (true/false) : ");
        String prenotazione = scanner.nextLine();
        

        Ristorante nuovoRistorante = new Ristorante(nome, address, city, price,nation, cuisine, 0.0, 0.0,Boolean.parseBoolean(delivery),
        		Boolean.parseBoolean(prenotazione), Recensione.cercaPerRistorante(nome));

        gestioneRistoranti.aggiungiRistorante(nuovoRistorante);
        this.ristorante = nuovoRistorante;
        System.out.println("Ristorante aggiunto e associato con successo!");
    }

    private void visualizzaRiepilogo() {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        ristorante.visualizzaRiepilogo();
    }

    private void visualizzaRecensioni() {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        ristorante.visualizzaRecensioni();
    }

    private void rispondiARecensione(GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }

        ArrayList<Recensione> recensioni = Recensione.cercaPerRistorante(ristorante.getName());

        if (recensioni.isEmpty()) {
            System.out.println("Nessuna recensione trovata per questo ristorante.");
            return;
        }

        for (int i = 0; i < recensioni.size(); i++) {
            Recensione rec = recensioni.get(i);
            String nomeCliente = GestioneFile.getNomeDaCodFiscale("src/dati/utente.csv", rec.getCliente());
            System.out.println("[" + (i + 1) + "] " + nomeCliente + ": " + rec.getTestoRecensione());
            if (rec.getRisposta() != null && !rec.getRisposta().isEmpty()) {
                System.out.println("Risposta proprietario: " + rec.getRisposta());
            }
        }

        System.out.print("A quale recensione vuoi rispondere? ");
        int scelta = Integer.parseInt(scanner.nextLine()) - 1;

        if (scelta >= 0 && scelta < recensioni.size()) {
            Recensione selezionata = recensioni.get(scelta);
            String nomeCliente = GestioneFile.getNomeDaCodFiscale("src/dati/utente.csv", selezionata.getCliente());
            System.out.println("Rispondi al commento di " + nomeCliente + ":");
            String risposta = scanner.nextLine();

            gestioneRecensioni.rispondiARisposta(ristorante.getName(), selezionata.getCliente(), risposta);
            System.out.println("Risposta inviata correttamente.");
        } else {
            System.out.println("Scelta non valida.");
        }
    }

    private void aggiungiPiatto(Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        System.out.print("Nome del piatto: ");
        String nomePiatto = scanner.nextLine();
        System.out.print("Descrizione del piatto: ");
        String descrizionePiatto = scanner.nextLine();
        System.out.print("Prezzo del piatto: ");
        double prezzoPiatto = Double.parseDouble(scanner.nextLine());

        Piatto nuovoPiatto = new Piatto(nomePiatto, descrizionePiatto, prezzoPiatto);
        ristorante.aggiungiPiatto(nuovoPiatto);
    }

    private void rimuoviPiatto(Scanner scanner) {
        if (ristorante == null) {
            System.out.println("Nessun ristorante associato.");
            return;
        }
        System.out.print("Nome del piatto da rimuovere: ");
        String nomePiatto = scanner.nextLine();
        ristorante.rimuoviPiatto(nomePiatto);
    }
}
