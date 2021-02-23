package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("view/root.fxml"));
        Scene scene = new Scene(root);
        scene.setOnMouseClicked( event -> {
            System.out.println(event.getScreenX()+":"+event.getSceneX()+":"+event.getX());
            System.out.println(event.getScreenY()+":"+event.getSceneY()+":"+event.getY());
        });
        primaryStage.setTitle("Carreras");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
