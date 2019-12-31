package backend;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class News implements Comparable, Serializable {
    //ATTRIBUTS
    protected String titre,auteur;
    protected LocalDate date;
    protected URL source;
    private static int cpt=1;
    protected final int numero=cpt;

    //CONSTRUCTEUR
    public News(String titre, String auteur, LocalDate date, String source) throws MalformedURLException, DateTimeParseException {
        this.titre=titre;
        this.auteur=auteur;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date = date;
        this.source = new URL(source);
        cpt++;
    }

    //SETTERS AND GETTERS

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public URL getSource() {
        return source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSource(URL source) {
        this.source = source;
    }

    //AFFICHAGE
    public String toString(){
        return numero+") Titre : "+titre+"\nAuteur : "+auteur+"\nDate : "+date+"\nSource : "+source+"\n";
    }

    public News rechercher(String mot){
        Pattern pattern = Pattern.compile(mot);
        Matcher matcher = pattern.matcher(titre+auteur+date+source);
        if(matcher.find())
            return this;
        return null;
    }

    /*public void afficher(){
        System.out.println("Titre : "+titre+"\nAuteur : "+auteur+"\nDate : "+date+"\nSource : "+source);
    }

    //SAISIE
    public void saisir() throws MalformedURLException, DateTimeParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Donnez le titre :");
        titre = sc.nextLine();
        System.out.println("Donnez le nom de l'auteur :");
        auteur = sc.nextLine();
        System.out.println("Donnez la date :");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date = LocalDate.parse(sc.nextLine(), formatter);
        System.out.println("Donnez la source :");
        source = new URL(sc.nextLine());
    }*/

    @Override
    public int compareTo(Object o) {
        News n2 = (News)o;
        return this.getTitre().compareTo(n2.getTitre());
    }

}
