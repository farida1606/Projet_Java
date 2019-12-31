package front;

import backend.MesNews;
import backend.News;
//import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.awt.Button;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;

public class Controller {
    private byte typeActu = 0; //0 pour article, 1 pour photo

    @FXML
    ComboBox comboFormatIns;

    @FXML
    AnchorPane fichierPane, afficherPane, insererPane, articlePane, photoPane, rechercherPane, supprimerPane;

    @FXML
    TextField txtNomBase, txtCheminSauvegarde, txtCheminOuvrir, txtAuteurArtIns, txtTitreArtIns, txtUrlArtIns, txtSourceArtIns, txtTitrePhIns, txtAuteurPhIns, txtSourcePhIns, txtResolPhIns, txtMotsCles;

    @FXML
    TextArea txtTexteIns;

    @FXML
    RadioButton radioAtricleIns, radioPhotoIns,radioNbPhIns, radioCouleurPhIns;

    @FXML
    CheckBox checkElecIns, checkPapierIns;

    @FXML
    DatePicker dateArtIns, datePhIns;

    @FXML
    TableColumn col1, col2, col3, col4, col5, col6, col7, col8;

    @FXML
    TableView tableAffichage, tableArtTrouvees, tablePhTrouvees;

    @FXML
    Button changerAffichage;

    @FXML
    Label labelAffichage;

    public void informationDialog(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void errorDialog(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //affichage des panes
    public void filesPane(){
        fichierPane.setVisible(true);
        afficherPane.setVisible(false);
        insererPane.setVisible(false);
        rechercherPane.setVisible(false);
        supprimerPane.setVisible(false);
    }

    public void afficherPane(){
        afficherPane.setVisible(true);
        fichierPane.setVisible(false);
        insererPane.setVisible(false);
        rechercherPane.setVisible(false);
        supprimerPane.setVisible(false);
        afficher();
    }

    public void insererPane(){
        insererPane.setVisible(true);
        afficherPane.setVisible(false);
        fichierPane.setVisible(false);
        rechercherPane.setVisible(false);
        supprimerPane.setVisible(false);
    }

    public void rechercherPane(){
        rechercherPane.setVisible(true);
        fichierPane.setVisible(false);
        afficherPane.setVisible(false);
        insererPane.setVisible(false);
        supprimerPane.setVisible(false);
    }

    public void supprimerPane(){
        supprimerPane.setVisible(true);
        rechercherPane.setVisible(false);
        fichierPane.setVisible(false);
        afficherPane.setVisible(false);
        insererPane.setVisible(false);
    }

    //fonctionnalités
    public void creer(){
        String nom = txtNomBase.getText();
        //TODO
        // contrôle sur le champ : expression régulière pas d'espace entre les caractères
        if(!(nom.isEmpty())) {
            MesNews.creer(nom);
            informationDialog("Base créée", "La base " + nom + " a bien été créée !");
            txtNomBase.setText("");
        }
        else
            errorDialog("Nom de base invalide", "Veuillez saisir un nom valide pour créer la base d'actualités.");
    }

    public void ouvrir(){
        String path = txtCheminOuvrir.getText();
        try {
            if(!path.isEmpty()) {
                boolean done = MesNews.ouvrir(path);
                if(!done)
                    errorDialog("Base non chargée", "Veuillez saisir un nouveau chemin valide.");
                else informationDialog("Base chargée", "Vous pouvez maintenant manipuler les fichiers de cette base");
            }
        } catch (IOException | ClassNotFoundException e) {
            errorDialog("Chemin invalide", "Veuillez entrer un chemin de fichier valide.");
        }
    } //TODO revoir et corriger

    public void sauvegarder(){
        String path;
        path = txtCheminSauvegarde.getText();
        //TODO
        // contrôle sur le champ + controle de si la base à sauvegarder est séléctionnée
        try {
            if(!(path.isEmpty())) {
                boolean done  = MesNews.sauvegarder(path);
                if(!done)
                    errorDialog("Base non sauvegardée", "Veuillez créer la base ou l'ouvrir pour la sauvegarder.");
            } else
                errorDialog("Chemin non spécifié", "Veuillez entrer un chemin de sauvegarde valide.");
        } catch (IOException e) {
            errorDialog("Chemin invalide", "Le chemin du fichier indiqué est invalide. Veuillez saisir un chemin valide.");
        }
        txtCheminSauvegarde.setText("");
    }

    public void afficher(){
        if(MesNews.baseDeNews!=null){
            initialize();
            tableAffichage.setItems(MesNews.afficher(typeActu));
            //TODO
            // gérer l'affichage des couleurs pour les photos et la disponibilité en version papier pour les articles
        }
    }

    public void insererArticle(){
        String titre = txtTitreArtIns.getText();
        String auteur = txtAuteurArtIns.getText();
        LocalDate date = dateArtIns.getValue();
        String source = txtSourceArtIns.getText();
        String texte = txtTexteIns.getText();
        String url = txtUrlArtIns.getText();
        boolean version = false;
        if (checkPapierIns.isSelected())
            version = true;

        try {
            //Si les champs sont bien remplis
            if(!titre.isEmpty() && !auteur.isEmpty() && !source.isEmpty() && !date.equals(null) && !texte.isEmpty() && !url.isEmpty()) {
                boolean done = MesNews.inserer(titre, auteur, date, source, texte, url, version);
                System.out.println("insertion done");
                if (!done)
                    errorDialog("Article non inséré", "Veuillez créer une base ou en ouvrir une déjà existante pour y insérer un article.");
                else {
                    emptyInsertionArticle();
                    informationDialog("Article inséré", "L'article a bien été inséré dans la base courante.");
                }
            }
        } catch (MalformedURLException e) {
            errorDialog("URL invalide", "Veuillez saisir une URL valide.");
        }
    }

    public void insererPhoto(){
        String titre = txtTitrePhIns.getText();
        String auteur = txtAuteurPhIns.getText();
        LocalDate date = datePhIns.getValue();
        String source = txtSourcePhIns.getText();
        String format = comboFormatIns.getValue().toString();
        int resolution = Integer.parseInt(txtResolPhIns.getText());
        boolean couleur = false; //Noir et blanc par défaut
        if(radioCouleurPhIns.isSelected())
            couleur = true;
        try {
            //Si tous les champs sont bien remplis
            if(!titre.isEmpty() && !auteur.isEmpty() && !source.isEmpty() && !date.equals(null) && !format.isEmpty() && resolution>0) {
                //insertion
                boolean done = MesNews.inserer(titre, auteur, date, source, format, resolution, couleur);
                if (!done)
                    errorDialog("Photo non insérée", "Veuillez créer une base ou en ouvrir une déjà existante pour y insérer une photo.");
                else {
                    emptyInsertionPhoto();
                    informationDialog("Photo insérée", "La photo a bien été insérée dans la base courante.");
                }
            }
        } catch (MalformedURLException e) {
            errorDialog("URL invalide", "Veuillez saisir une URL valide.");
        }

    }

    public void rechercher(){
        String mots = txtMotsCles.getText();
        if(!mots.isEmpty()){
            ObservableList<News> news = MesNews.rechercher(mots);
            tableArtTrouvees.setItems(news);
            tablePhTrouvees.setItems(news);
            //setCellValueFactory pour chaque colonne
        }
    }

    public void quitter(){
        informationDialog("Au revoir !", "Merci d'avoir utilisé l'application.");
        MesNews.quitter();
    }

    //autres fonctions
    @FXML
    public void initialize(){
        col1.setText("ID");
        col2.setText("Titre");
        col3.setText("Auteur");
        col4.setText("Date");
        col5.setText("Source");
        col1.setCellValueFactory(new PropertyValueFactory<News, String>("numero"));
        col2.setCellValueFactory(new PropertyValueFactory<News, String>("titre"));
        col3.setCellValueFactory(new PropertyValueFactory<News, String>("auteur"));
        col4.setCellValueFactory(new PropertyValueFactory<News, String>("date"));
        col5.setCellValueFactory(new PropertyValueFactory<News, String>("source"));
        if(typeActu == 0){
            col6.setText("Texte");
            col7.setText("URL");
            col8.setText("Version");
            col6.setCellValueFactory(new PropertyValueFactory<News, String>("texte"));
            col7.setCellValueFactory(new PropertyValueFactory<News, String>("url"));
            col8.setCellValueFactory(new PropertyValueFactory<News, String>("version"));
        }else{
            col6.setText("Format");
            col7.setText("Resolution (px)");
            col8.setText("Disponible en");
            col6.setCellValueFactory(new PropertyValueFactory<News, String>("format"));
            col7.setCellValueFactory(new PropertyValueFactory<News, String>("resolution"));
            col8.setCellValueFactory(new PropertyValueFactory<News, String>("couleur"));
        }
    }

    @FXML
    private void radioSelected(){
        if(radioAtricleIns.isSelected()) {
            photoPane.setVisible(false);
            articlePane.setVisible(true);
            checkElecIns.setSelected(true);
            checkElecIns.setDisable(true);
        }
        else
            if(radioPhotoIns.isSelected()){
                photoPane.setVisible(true);
                articlePane.setVisible(false);
                radioNbPhIns.setSelected(true);
            }
    }

    public void changer_typeActu(){
        if(typeActu==0) {
            //Photos
            typeActu = 1;
            labelAffichage.setText("Photos");
           // changerAffichage.setText("Afficher les articles");
            afficher();
        }else{
            //Articles
            typeActu = 0;
            labelAffichage.setText("Articles");
            //changerAffichage.setText("Afficher les photos");
            afficher();
        }
    }

    private void emptyInsertionArticle(){
        txtTitreArtIns.setText("");
        txtAuteurArtIns.setText("");
        txtSourceArtIns.setText("");
        txtTexteIns.setText("");
        txtUrlArtIns.setText("");
        checkPapierIns.setSelected(false);
    }

    private void emptyInsertionPhoto(){
        txtTitrePhIns.setText("");
        txtAuteurPhIns.setText("");
        txtSourcePhIns.setText("");
        comboFormatIns.setValue("");
        txtResolPhIns.setText("");
        radioPhotoIns.setSelected(false);
    }
}
