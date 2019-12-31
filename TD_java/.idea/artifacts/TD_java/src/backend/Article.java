package backend;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Article extends News {

    //ATTRIBUTS
    private String texte;
    private URL url;
    private boolean version; //true: existe en version papier et éléctronique, false: existe en version électronique seulement

    //CONSTRUCTEUR
    public Article(String titre, String auteur, LocalDate date, String source, String texte, String url, boolean version) throws MalformedURLException, DateTimeParseException {
        super(titre, auteur, date, source);
        this.texte = texte;
        this.url = new URL(url);
        this.version = version;
    }

    private String version(){
        if(version)
            return "papier et éléctronique.";
        return "éléctronique seulement";
    }

    //AFFICHAGE
    @Override
    public String toString() {
        return super.toString()+"Contenu : "+texte+"\nURL : "+url+"\nDisponible en version : "+version();
    }

    //GETTERS AND SETTERS
    public String getTexte() {
        return texte;
    }

    public URL getUrl() {
        return url;
    }

    public boolean isVersion() {
        return version;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public Article rechercher(String mot){
        Pattern pattern = Pattern.compile(mot);
        Matcher matcher = pattern.matcher(super.titre+super.auteur+super.date+super.source+texte+url+version);
        if(matcher.find())
            return this;
        return null;
    }

}
