/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import resources.GestioneFile;
import resources.GestioneMenu;
import resources.Piatto;

/**
 *
 * @author HEW4K7Z2EA
 */
public class Ristorante {
    //Name,Address,city,nation,price,Cuisine,Longitude,Latitude,delivery,reservation,stars
//modificare gli attributi e i metodi
    
    private String name;
    private String address;
    private String city;
    private String nation;
    private String price;
    private String cuisine;
    private double longitude;
    private double latitude;
    private boolean delivery; //possibilità di delivery
    private boolean reservation; //possibilità di prenotazione online
   
    
    private ArrayList<Recensione> recensioni; // Lista delle recensioni
    // Nuovi attributi per gestire menu e promozioni
    private ArrayList<String> promozioni; // Lista delle promozioni
    private ArrayList<Piatto> menu; // Lista di piatti
    //Costruttore

    public Ristorante(String name, String address, String city, String price,String nation, String cuisine, double longitude, double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.price = price;
        this.cuisine = cuisine;
        this.longitude = longitude;
        this.latitude = latitude;
        this.delivery = delivery;
        this.reservation = reservation;
        this.nation = nation;
        this.recensioni = recensioni;
        this.promozioni = new ArrayList<>(); // Inizializza l'ArrayList per le promozioni   
        this.menu = new ArrayList<>();

    }

    public Ristorante(GestioneFile gf, ArrayList<Piatto> menu) {
        this.menu = menu;
    }
       
    public String getNation() {
    	return nation;
    }
    
    public void setNation(String nation) {
    	this.nation=nation;
    }
    
    public boolean getDelivery() {
    	return delivery;
    }
    
    public boolean getReservation() {
    	return reservation;
    }
    
    public void setDelivery(boolean delivery) {
    	this.delivery = delivery;
    }

    public void setReservation(boolean reservation) {
    	this.reservation = reservation;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setLocation(String location) {
        this.city = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArrayList<Piatto> getMenu() {
        return menu;
    }
    
    public ArrayList<String> getPromozioni() {
        return promozioni;
    }

    public void setPromozioni(ArrayList<String> promozioni) {
        this.promozioni = promozioni;
    }
    
    
     public double getMediaStelle() {
        if (recensioni.isEmpty()||recensioni == null ) return 0.0;
        double somma = 0.0;
        for (Recensione r : recensioni) {
            somma += r.getStelle();
        }
        return somma / recensioni.size();
    }
    
    // Metodi per gestire le promozioni
    public void aggiungiPromozione(String promozione,String nomeUtente) {
        if (!promozioni.contains(promozione)) {
            promozioni.add(promozione);
            GestioneFile gestioneFile = new GestioneFile();
            gestioneFile.scriviPreferiti("promozioni.csv", new Preferiti(nomeUtente, this.getName()));
        }else{
            System.out.println("La promozione è già presente.");
        }
    }
    public void rimuoviPromozione(String promozione) {
        promozioni.remove(promozione);
    }
    
    public void visualizzaPromozioni() {
    if (promozioni.isEmpty()) {
        System.out.println("Nessuna promozione disponibile.");
    } else {
        System.out.println("Promozioni disponibili:");
        for (String promozione : promozioni) {
            System.out.println("- " + promozione);
        }
    }
}
     
     
    public void visualizzaRistorante() {
	    System.out.println("Nome Ristorante: " + name);
	    System.out.println("Indirizzo: " + address);
	    System.out.println("Locazione: " + city);
	    System.out.println("Prezzo: " + price);
	    System.out.println("Cucina: " + cuisine);
	    System.out.println("Coordinate: Latitudine " + latitude + ", Longitudine " + longitude);
	    System.out.println("Servizio Delivery: " + delivery);
	    System.out.println("Prenotazione Online: " + reservation);
    
    }

    public void visualizzaRecensioni() {
    ArrayList<Recensione> recs = Recensione.cercaPerRistorante(this.name);
    
    if (recs.isEmpty()) {
        System.out.println("Nessuna recensione presente per " + this.name);
    } else {
        System.out.println("Recensioni per " + this.name + ":");
        for (Recensione r : recs) {
            System.out.println("- " + r.getTestoRecensione() + " (" + r.getStelle() + " stelle)");
            // Nessuna stampa della risposta
        }
    }
}

    
    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
    }
    
    
    public void visualizzaRiepilogo() {
    ArrayList<Recensione> recensioni = Recensione.cercaPerRistorante(this.name);
    if (recensioni.isEmpty()) {
        System.out.println("Nessuna recensione disponibile per " + this.name);
        return;
    }

    double sommaStelle = 0;
    for (Recensione recensione : recensioni) {
        sommaStelle += recensione.getStelle();
    }

    double mediaStelle = sommaStelle / recensioni.size();

    System.out.println("Riepilogo per " + this.name + ":");
    System.out.println("Numero di recensioni: " + recensioni.size());
    System.out.println("Media stelle: " + mediaStelle);
}

    
public void caricaMenuRistorante() {
    this.menu = new GestioneMenu().leggiMenu(name + "Menu.csv");
}


public void aggiungiPiatto(Piatto piatto) {
    if (piatto == null) {
        System.out.println("Errore: il piatto non può essere nullo.");
        return;
    }
    for (Piatto p : menu) {
        if (p.getNome().equalsIgnoreCase(piatto.getNome())) {
            System.out.println("Il piatto è già presente nel menu.");
            return;
        }
    }
    menu.add(piatto);
    new GestioneMenu().scriviMenu(name + "Menu.csv", menu); // salva tutto
    System.out.println("Piatto aggiunto correttamente.");
}

     
public void rimuoviPiatto(String nomePiatto) {
    boolean rimosso = false;
    for (int i = 0; i < menu.size(); i++) {
        if (menu.get(i).getNome().equalsIgnoreCase(nomePiatto)) {
            menu.remove(i);
            rimosso = true;
            break;
        }
    }

    if (rimosso) {
        new GestioneMenu().scriviMenu(name + "Menu.csv", menu);
        System.out.println("Piatto rimosso correttamente.");
    } else {
        System.out.println("Piatto non trovato nel menu.");
    }
}

	public double calcoloMediaStelle(String nome,String citta,String indirizzo) {
	
		double media,stelle=0;
		int cont=0;
		
    	
        try (CSVReader reader = new CSVReader(new FileReader("src/dati/recensioni.csv"))) {
            String[] riga;

            while ((riga = reader.readNext()) != null) {
                if (nome.equalsIgnoreCase(riga[0]) && indirizzo.equalsIgnoreCase(riga[2]) && citta.equalsIgnoreCase(riga[3]) ) {
                	//String name, String address, String city, String price,String nation, String cuisine, double longitude, 
                	//double latitude, boolean delivery,boolean reservation,ArrayList<Recensione> recensioni
                    stelle += Double.parseDouble(riga[6]);
                    cont++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
        
        return media = stelle/cont;
	
	}
     
} 
