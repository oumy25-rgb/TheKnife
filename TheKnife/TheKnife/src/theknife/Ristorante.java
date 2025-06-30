/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package theknife;

import java.util.ArrayList;
import resources.GestioneFile;

/**
 *
 * @author HEW4K7Z2EA
 */
public class Ristorante {
    //Name,Address,Location,Price,Cuisine,Longitude,Latitude,PhoneNumber,Url,WebsiteUrl,Award,GreenStar,FacilitiesAndServices,Description
//modificare gli attributi e i metodi
    
    private String name;
    private String address;
    private String location;
    private String price;
    private String cuisine;
    private double longitude;
    private double latitude;
    private String phoneNumber;
    private String url;
    private String websiteUrl;
    private String award;
    private boolean greenStar;
    private String facilitiesAndServices;
    private String description;
    private ArrayList<Recensione> recensioni; // Lista delle recensioni
    // Nuovi attributi per gestire menu e promozioni
    private ArrayList<String> menu;  // Lista dei piatti
    private ArrayList<String> promozioni; // Lista delle promozioni
    private GestioneFile gf;
    
    //Costruttore

    public Ristorante(String name, String address, String location, String price, String cuisine, double longitude, double latitude, String phoneNumber, String url, String websiteUrl, String award, boolean greenStar, String facilitiesAndServices, String description, ArrayList<Recensione> recensioni) {
        this.name = name;
        this.address = address;
        this.location = location;
        this.price = price;
        this.cuisine = cuisine;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.websiteUrl = websiteUrl;
        this.award = award;
        this.greenStar = greenStar;
        this.facilitiesAndServices = facilitiesAndServices;
        this.description = description;
        this.recensioni = recensioni;
        this.menu = new ArrayList<>(); // Inizializza l'ArrayList per il menu
        this.promozioni = new ArrayList<>(); // Inizializza l'ArrayList per le promozioni      
    }

    public Ristorante(GestioneFile gf) {
        this.gf = gf;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public boolean isGreenStar() {
        return greenStar;
    }

    public void setGreenStar(boolean greenStar) {
        this.greenStar = greenStar;
    }

    public String getFacilitiesAndServices() {
        return facilitiesAndServices;
    }

    public void setFacilitiesAndServices(String facilitiesAndServices) {
        this.facilitiesAndServices = facilitiesAndServices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Recensione> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(ArrayList<Recensione> recensioni) {
        this.recensioni = recensioni;
    }

    public ArrayList<String> getMenu() {
        setMenu();
        return menu;
    }

    public void setMenu() {
        GestioneFile gf = new GestioneFile();
        this.menu=gf.leggiDaFile("src/dati/" +name+"Menu.csv");
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
    
    public void aggiungiPiatto(String piatto) {
        if (piatto == null || piatto.isEmpty()) {
            System.out.println("Errore: il nome del piatto non può essere nullo o vuoto.");
            return;
        }
        setMenu();
        if (!menu.contains(piatto)) {
            menu.add(piatto);
            GestioneFile gestioneFile = new GestioneFile();
            gestioneFile.scriviPiatto(name + "Menu.csv", piatto);
        } else {
            System.out.println("Il piatto è già presente nel menu.");
        }
    }
     
    public void rimuoviPiatto(String piatto) {
        menu.remove(piatto);
        GestioneFile gestioneFile = new GestioneFile(); 
        gestioneFile.scriviSuFile(name+"Menu.csv", menu,false);//non scrive in append
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
    
     
     
     
    public void visualizzaRistorante() {
    System.out.println("Nome Ristorante: " + name);
    System.out.println("Indirizzo: " + address);
    System.out.println("Locazione: " + location);
    System.out.println("Prezzo: " + price);
    System.out.println("Cucina: " + cuisine);
    System.out.println("Coordinate: Latitudine " + latitude + ", Longitudine " + longitude);
    System.out.println("Numero di telefono: " + phoneNumber);
    System.out.println("Url: " + url);
    System.out.println("Url del sito web: " + websiteUrl);
    System.out.println("Premio: " + award);
    System.out.println("Stella verde: " + (greenStar ? "Si" : "No"));
    System.out.println("Servizi e strutture: " + facilitiesAndServices);
    System.out.println("Descrizione: " + description);
    }

    public void visualizzaRecensioni() {
        System.out.println("Numero recensioni: " + recensioni.size());
        System.out.printf("Media stelle: %.2f\n", getMediaStelle());
        if (recensioni.isEmpty()) {
            System.out.println("Nessuna recensione presente.");
        } else {
            System.out.println("Recensioni:");
            for (Recensione r : recensioni) {
                System.out.println("- " + r.getStelle() + " stelle da " + r.getCliente() + ": " + r.getTestoRecensione());
            }
        }
    }
    
    public void aggiungiRecensione(Recensione recensione) {
        recensioni.add(recensione);
    }
    
    
    public void visualizzaRiepilogo(Ristorante ristorante) {
    ArrayList<Recensione> recensioni = ristorante.getRecensioni();
    if (recensioni.isEmpty()) {
        System.out.println("Nessuna recensione disponibile per " + ristorante.getName());
        return;
    }
    
    double sommaStelle = 0;
    int numeroRecensioni = recensioni.size();
    
    for (Recensione recensione : recensioni) {
        sommaStelle += recensione.getStelle();
    }
    
    double mediaStelle = sommaStelle / numeroRecensioni;
    
    System.out.println("Riepilogo per " + ristorante.getName() + ":");
    System.out.println("Numero di recensioni: " + numeroRecensioni);
    System.out.println("Media stelle: " + mediaStelle);
}
    
public void caricaMenuRistorante() {
    ArrayList<String> piatti = gf.leggiDaFile("src/dati/" + name + "menu.csv");
    for (String piatto : piatti) {
        menu.add(piatto); // Aggiungi ogni piatto all'ArrayList menu
    }
}


    

   
    
   
    
    
 
    
}
