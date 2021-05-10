package com.company;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Sphere esfera = new Sphere(100);
        Group grupo = new Group();
        grupo.getChildren().add(esfera);
        Camera camara = new PerspectiveCamera();
        Scene s = new Scene(grupo, WIDTH, HEIGHT);
        s.setFill(Color.BEIGE);
        s.setCamera(camara);
        esfera.translateXProperty().set(WIDTH/2);
        esfera.translateYProperty().set(HEIGHT/2);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W: esfera.translateZProperty().set(esfera.getTranslateZ()+40);
                    break;
                case S: esfera.translateZProperty().set(esfera.getTranslateZ()-40);
                    break;
                case A: esfera.translateXProperty().set(esfera.getTranslateX()-40);
                    break;
                case D: esfera.translateXProperty().set(esfera.getTranslateX()+40);
                    break;
                case E: esfera.translateYProperty().set(esfera.getTranslateY()-40);
                    break;
                case Q: esfera.translateYProperty().set(esfera.getTranslateY()+40);
                    break;
            }
        });
        primaryStage.setTitle("Ejemplo esfera 3D");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}