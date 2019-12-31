package backend;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Photo extends News{

    //ATTRIBUTS
    private Format format;
    private int resolution;
    private boolean couleur; //false: noir et blanc, true: couleur

    //CONSTRUCTEUR
    public Photo(String titre, String auteur, LocalDate date, String source, String format, int resolution, boolean couleur) throws MalformedURLException, DateTimeParseException {
        super(titre, auteur, date, source);
        this.format = Format.valueOf(format.toUpperCase());
        this.resolution = resolution;
        this.couleur = couleur;
    }

    private String couleur(){
        if(couleur)
            return "couleur";
        return "noir et blanc";
    }

    //AFFICHAGE
    @Override
    public String toString() {
        return super.toString()+"Format : "+format+"\nRésolution : "+resolution+"px\nEn "+couleur();
    }

    //GETTERS AND SETTERS
    public boolean isCouleur() {
        return couleur;
    }

    public Format getFormat() {
        return format;
    }

    public int getResolution() {
        return resolution;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    public void setCouleur(boolean couleur) {
        this.couleur = couleur;
    }

    public Photo rechercher(String mot){
        Pattern pattern = Pattern.compile(mot);
        Matcher matcher = pattern.matcher(super.titre+super.auteur+super.date+super.source+format+resolution+couleur);
        if(matcher.find())
            return this;
        return null;
    }

}
