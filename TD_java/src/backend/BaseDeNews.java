package backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class BaseDeNews implements Serializable{
    private String nom;
    private TreeSet<News> base;

    public void initialise(String nom){
        this.nom = nom;
        base = new TreeSet();
    }

    public BaseDeNews getBase(String nom){
        if(this.nom.equalsIgnoreCase(nom))
            return this;
        return null;
    }

    //GETTERS
    public String getNom() {
        return nom;
    }

    public TreeSet<News> getBase() {
        return base;
    }

    //ajouter un élément dans la base
    public boolean ajoute(News n){
        if(base!=null){
            if(!base.contains(n)) {
                base.add(n);
                return true;
            }
        }
        return false;
    }

    //rechercher un élément dans la base à partir de son titre
    public News rechercherTitre(String titre){
        for(News n: base){
            if(n.getTitre().equalsIgnoreCase(titre))
                return n;
        }
        return null;
    }

    //rechercher un élément dans la base à partir de son index
    public News rechercherIndex(int i){
        for(News n: base){
            if(n.getNumero()==i)
                return n;
        }
        return null;
    }

    //rechercher un élément à partir d'un mot entré
    public ObservableList<News> rechercher(String mot){
        ObservableList<News> news = FXCollections.observableArrayList();
        for(News n : base){
            News r = n.rechercher(mot);
            if (r != null)
                news.add(n);
            }
        if(!(news.isEmpty()))
            return news;
        return null; //aucune actualité ne contient le mot recherché
    }


    //supprimer un élement de la base à partiede son index
    public void supprimer(int numero){
        News nRemove = rechercherIndex(numero);
        if(nRemove!=null)
            base.remove(nRemove);
        else System.out.println("Cette actualité n'existe pas dans la base.");
    }

    public ObservableList<News> affichageArticles(TreeSet<News> base){
        ObservableList<News> liste = FXCollections.observableArrayList();
        //Articles
        for(News n: base){
            if(n instanceof Article)
                liste.add(n);
        }
        return liste;
    }

    public ObservableList<News> affichagePhotos(TreeSet<News> base){
        ObservableList<News> liste = FXCollections.observableArrayList();
        //Photos
        for(News n: base){
            if(n instanceof Photo)
                liste.add(n);
        }
        return liste;
    }

    //afficher la base d'actualités
    public void afficher(){
        if(base!=null) {
            System.out.println("Toutes les actualités :");
            for (News n : base)
                System.out.println(n);
        }
        else System.out.println("Il n'y a aucune actualité dans cette base.");
    }

    //sauvegarder la base dans un fichier
    public void enregistrer(String fileName) throws IOException {
        FileOutputStream w = new FileOutputStream(fileName);
            ObjectOutputStream o = new ObjectOutputStream(w);
            o.writeObject(base);
            o.close();
            w.close();
    }

    //lire la base d'actus à partir d'un fichier
    public boolean lire(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream r = new FileInputStream(fileName);
        ObjectInputStream o = new ObjectInputStream(r);
        Object readObj = o.readObject();
        base = (TreeSet<News>)readObj;
        o.close();
        r.close();
        if(base!=null)
            return true;
        return false;
    }
}
