package front;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    	
     
 /*  FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            AnchorPane pane = loader.load();

            // controller
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setMain(this);

            // scene on stage
             Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();*/
        
  
        root.getStylesheets().add(getClass().getResource("style.css").toString());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 589, 426));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
