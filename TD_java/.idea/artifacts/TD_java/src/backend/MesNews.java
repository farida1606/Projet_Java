package backend;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MesNews {
    static Scanner sc = new Scanner(System.in);
    public static BaseDeNews baseDeNews; //base de news
    //public static ArrayList<BaseDeNews> bases = new ArrayList<>();

    /*public static BaseDeNews rechercherBase(String nomBase){
        for(BaseDeNews b: bases){
            if(bases.contains(b.getBase(nomBase)))
                return b;
        }
        return null;
    }
    public static void ajouter(BaseDeNews base){
        if(!bases.contains(base))
            bases.add(base);
    }

    public static String[] names(){
        String names[] = {};
        int i = 0;
        for(BaseDeNews n : bases){
            names[i] = n.getNom();
            i++;
        }
        return names;
    }*/

    //METHODES
    public static BaseDeNews creer(String nom){
        baseDeNews = new BaseDeNews();
        baseDeNews.initialise(nom);
        //bases.add(baseDeNews);
        return baseDeNews;
    }

    public static boolean ouvrir(String fileName) throws IOException, ClassNotFoundException {
        //sc.nextLine();
        //System.out.println("Donnez le nom/chemin du fichier à partir du quel vous voulez charger la base d'actualités.");
        //String fileName = sc.nextLine();
        if(!fileName.isEmpty()){
            boolean done = baseDeNews.lire(fileName);
            if(done)
                return true;
        }
        return false;
    }

    public static boolean sauvegarder(String fileName) throws IOException{
        //sc.nextLine();
        //System.out.println("Donnez le nom/chemin du fichier où vous souhaitez enregistrer la base d'actualités.");
        if(!baseDeNews.getNom().isEmpty()) {
            baseDeNews.enregistrer(fileName);
            return true;
        }
        return false;
    }

   /* private static String[] saisieNews(){
        String[] tab = new String[6];
        System.out.println("Donnez le titre :");
        sc.nextLine();//vider le buffer
        tab[0]  = sc.nextLine();
        System.out.println("Donnez le nom de l'auteur :");
        tab[1] = sc.nextLine();
        System.out.println("Donnez la date :");
        tab[2] = sc.nextLine();
        System.out.println("Donnez la source :");
        tab[3] = sc.nextLine();
        return tab;
    }*/

   /* public static void inserer() throws MalformedURLException {
        //System.out.println("Choisissez le type d'actualité à insérer :\n1) Article\t\t2) Photo");
        //int choix = sc.nextInt();
        switch(type){ //type 1 : article; type 2 : photo
            case 1 :
                //saisie des infos
                String tab[] = saisieNews();
                System.out.println("Donnez le contenu de l'article :");
                tab[4] = sc.nextLine(); //réglerle problème des textes longs te textes avec espaces
                System.out.println("Donnez l'URL de l'article :");
                tab[5] = sc.next();
                System.out.println("Indiquez si l'article est disponible en version papier également : 1)Oui\t2)Non");
                boolean version = false;
                if(sc.nextInt()==1)
                    version = true;

                //construction de l'article
                    Article a = new Article(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], tab[5], version);
                    //insertion de l'article dans la base de news
                    baseDeNews.ajoute(a);
                break;
            case 2 :
                //saisie des infos
                String tab2[] = saisieNews();
                System.out.println("Donnez le format de la photo :");
                tab2[4] = sc.next();
                System.out.println("Donnez la résolution de la photo :");
                int resolution = sc.nextInt();
                System.out.println("Indiquez si l'article est en couleur : 1)Oui\t2)Non");
                boolean couleur = false;
                if(sc.nextInt()==1)
                    couleur = true;

                try {
                    //construction de la photo
                    Photo p = new Photo(tab2[0], tab2[1], tab2[2], tab2[3], tab2[4], resolution, couleur);
                    //insertion de la photo dans la base de news
                    baseDeNews.ajoute(p);
                } catch (MalformedURLException e) {
                    System.out.println("URL malformé.");
                }
                break;
        }
    }*/

    public static boolean inserer(String titre, String auteur, LocalDate date, String source, String texte, String url, boolean version) throws MalformedURLException {
        //insertion article
        if(baseDeNews.getNom() != null)
            return baseDeNews.ajoute(new Article(titre, auteur, date, source, texte, url, version));
        return false;
    }

    public static boolean inserer(String titre, String auteur, LocalDate date, String source, String format, int resolution, boolean couleur) throws MalformedURLException {
        //insertion d'une poto
        if(baseDeNews.getNom() != null) {
            baseDeNews.ajoute(new Photo(titre, auteur, date, source, format, resolution, couleur));
            return true;
        }
        return false;
    }

    public static void supprimer(){
        baseDeNews.afficher();
        System.out.println("Indiquez le numéro de l'actualité à supprimer :");
        int i = sc.nextInt();
        baseDeNews.supprimer(i);
    }

    public static ObservableList<News> afficher(byte type){
        //baseDeNews.afficher();
        if(type == 0)
         return baseDeNews.affichageArticles(baseDeNews.getBase());
        else
            return baseDeNews.affichagePhotos(baseDeNews.getBase());
    }

    /*public static void rechercher(){
        System.out.println("Donnez le titre de l'actualité que vous cherchez :");
        String titre = sc.nextLine();
        News n = baseDeNews.rechercherTitre(titre); //recherche selon le titre
        if(n!=null)
            System.out.println(n);
        else System.out.println("L'actualité que vous cherchez n'existe pas dans la base.");
        sc.nextLine();
        System.out.println("Qu'est ce que vous voulez chercher? Entrez un ou plusieurs mots.");
        String mot = sc.nextLine();
        ArrayList<News> listeRecherche = baseDeNews.rechercher(mot);
        if(listeRecherche==null)
            System.out.println("La base ne contient aucune actualité qui contient ce que vous cherchez.");
        else
            System.out.println(listeRecherche);
    }*/

    public static ObservableList<News> rechercher(String mots){
        return baseDeNews.rechercher(mots);
    }

    public static void quitter(){
        //System.out.println("Merci d'avoir utilisé l'application.");
        System.exit(0);
    }

    private static void afficherMenu(){
        System.out.println("MENU :\n1- Créer\n2 -Ouvrir\n3- Sauvegarder\n4- Insérer\n5- Supprimer\n6- Afficher\n7- Rechercher\n8- Quitter\nChoisissez une option parmi les précédentes.");
    }

    //MAIN
   /* public static void main(String[] args){
        boolean continuer = true;
        do {
            afficherMenu();

            //Lecture du choix
            int choix;
            choix = sc.nextInt();
            //TODO
            //ajouter un contrôle sur les valeurs entrées
            //si une chaine est entrée exception (à gérer)

            switch (choix) {
                case 1: //création d'une base d'actus
                    baseDeNews = creer();
                    break;
                case 2: //charger une base d'actus à partir d'un fichier
                    ouvrir();
                    break;
                case 3: //sauvegarder une base d'actus dans un fichier
                    sauvegarder();
                    break;
                case 4: //insérer une actu dans la base chargée
                    sc.nextLine();
                    inserer();
                    break;
                case 5: //supprimer une actu
                    supprimer();
                    break;
                case 6: //afficher la base chargée
                    afficher();
                    break;
                case 7: //rechercher une actu dans la base chargée
                    rechercher();
                    break;
                case 8: //quitter l'application
                    quitter();
                    break;
                default:
                    quitter();
            }
        }while(continuer);
     }*/
}
