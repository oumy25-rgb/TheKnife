/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theknife;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import resources.GestioneFile;
import resources.Piatto;

public class Cliente extends Utente {
    private ArrayList<String> preferiti;
    private ArrayList<Recensione> recensioni;
    private final String filePreferiti;

    public Cliente(String nome, String cognome, String codFiscale, String username, String password,
                   String dataNascita, String luogoDomicilio) {
        super(nome, cognome, codFiscale, username, password, dataNascita, luogoDomicilio, "cliente");
        this.filePreferiti = "src/dati/" + codFiscale + "_preferiti.csv";
        this.preferiti = caricaPreferiti();
        this.recensioni = Recensione.cercaPerCliente(getCodFiscale());
    }
    

    public void mostraMenu(GestioneRistoranti gestioneRistoranti, GestioneRecensioni gestioneRecensioni, Scanner scanner) {
        int scelta;
        do {
            System.out.println("===== MENU CLIENTE =====");
            System.out.println("1. Visualizza preferiti");
            System.out.println("2. Aggiungi ristorante ai preferiti");
            System.out.println("3. Rimuovi ristorante dai preferiti");
            System.out.println("4. Visualizza recensioni personali");
            System.out.println("5. Aggiungi recensione");
            System.out.println("6. Modifica recensione");
            System.out.println("7. Elimina recensione");
            System.out.println("8. Visualizza menu di un ristorante");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");
            scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    visualizzaPreferiti();
                    break;
                case 2:
                    System.out.print("Nome ristorante: ");
                    String nomeAdd = scanner.nextLine();
                    Ristorante rAdd = gestioneRistoranti.cercaRistorantePerNome(nomeAdd);
                    if (rAdd != null) aggiungiPreferito(rAdd);
                    else System.out.println("Ristorante non trovato.");
                    break;
                case 3:
                    System.out.print("Nome ristorante: ");
                    String nomeRem = scanner.nextLine();
                    rimuoviPreferito(nomeRem);
                    break;
                case 4:
                    aggiornaRecensioniPersonali();
                    visualizzaRecensioniPersonali();
                    break;
                case 5:
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    Ristorante r = gestioneRistoranti.cercaRistorantePerNome(nome);
                    if (r != null) {
                        System.out.print("Testo: ");
                        String testo = scanner.nextLine();
                        System.out.print("Stelle (1-5): ");
                        int stelle = Integer.parseInt(scanner.nextLine());
                        Recensione rec = new Recensione(nome, getCodFiscale(), java.time.LocalDate.now().toString(), testo, stelle, "");
                        gestioneRecensioni.aggiungiRecensione(rec);
                        aggiornaRecensioniPersonali();
                    } else {
                        System.out.println("Ristorante non trovato.");
                    }
                    break;
                case 6:
                    gestioneRecensioni.modificaRecensione(this, scanner);
                    aggiornaRecensioniPersonali();
                    break;
                case 7:
                    gestioneRecensioni.eliminaRecensione(this, scanner);
                    aggiornaRecensioniPersonali();
                    break;
                case 8:             
                    System.out.print("Nome del ristorante: ");
                    String nomeRistMenu = scanner.nextLine();
                    Ristorante rMenu = gestioneRistoranti.cercaRistorantePerNome(nomeRistMenu);
                    if (rMenu != null) {
                        rMenu.caricaMenuRistorante();
                        ArrayList<Piatto> menu = rMenu.getMenu();
                        if (menu.isEmpty()) {
                            System.out.println("Il menu di questo ristorante è vuoto.");
                        } else {
                            System.out.println("----- MENU DI " + rMenu.getName().toUpperCase() + " -----");
                            for (Piatto p : menu) {
                                System.out.println("🍽 " + p.getNome() + " - " + p.getPrezzo() + "€");
                                System.out.println("   " + p.getDescrizione());
                            }
                            System.out.println("------------------------------------");
                        }
                    } else {
                        System.out.println("Ristorante non trovato.");
                    }     
                    break;
                case 0:
                    System.out.println("Logout effettuato.");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }

        } while (scelta != 0);
    }

    public void aggiungiPreferito(Ristorante ristorante) {
        if (!preferiti.contains(ristorante.getName())) {
            preferiti.add(ristorante.getName());
            salvaPreferiti();
            System.out.println("Aggiunto ai preferiti.");
        }
    }

    public void rimuoviPreferito(String nome) {
        if (preferiti.remove(nome)) {
            salvaPreferiti();
            System.out.println("Rimosso dai preferiti.");
        } else {
            System.out.println("Ristorante non nei preferiti.");
        }
    }

    public void visualizzaPreferiti() {
        if (preferiti.isEmpty()) {
            System.out.println("Nessun preferito.");
        } else {
            System.out.println("Preferiti:");
            for (String r : preferiti) {
                System.out.println("- " + r);
            }
        }
    }

    public void visualizzaRecensioniPersonali() {
    // Carica TUTTE le recensioni del cliente
    this.recensioni = Recensione.cercaPerCliente(getCodFiscale());
    
    if (recensioni.isEmpty()) {
        System.out.println("Non hai ancora scritto nessuna recensione.");
    } else {
        System.out.println("\n----- LE TUE RECENSIONI -----");
        for (Recensione r : recensioni) {
            System.out.println("\nRistorante: " + r.getRistorante());
            
            // Stampa le stelle
            System.out.print("Voto: ");
            for (int i = 0; i < r.getStelle(); i++) {
                System.out.print("★");
            }
            System.out.println(" (" + r.getStelle() + "/5)");
            
            System.out.println("Data: " + r.getData());
            System.out.println("Recensione: \"" + r.getTestoRecensione() + "\"");
            
            // Mostra la risposta solo se esiste
            if (r.getRisposta() != null && !r.getRisposta().isEmpty()) {
                System.out.println("\n[Risposta del ristoratore]");
                System.out.println("\"" + r.getRisposta() + "\"");
            }
            
            System.out.println("-----------------------------");
        }
    }
}

    private void aggiornaRecensioniPersonali() {
        this.recensioni = Recensione.cercaPerCliente(getCodFiscale());
    }

    private ArrayList<String> caricaPreferiti() {
        ArrayList<String> lista = new ArrayList<>();
        try (Scanner scanner = new Scanner(new java.io.File(filePreferiti))) {
            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine().trim());
            }
        } catch (IOException e) {
            // File non esistente o errore, lista vuota.
        }
        return lista;
    }

    private void salvaPreferiti() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePreferiti))) {
            for (String ristorante : preferiti) {
                writer.write(ristorante);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dei preferiti: " + e.getMessage());
        }
    }
    
    
    
}

    

