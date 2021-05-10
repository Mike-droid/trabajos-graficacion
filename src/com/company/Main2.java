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

public class Main2 extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Sphere esfera = new Sphere(100);
        Group grupo = new Group();
        grupo.getChildren().add(esfera);
        Camera camara = new PerspectiveCamera(true);
        Scene s = new Scene(grupo, WIDTH, HEIGHT);
        s.setFill(Color.BEIGE);
        s.setCamera(camara);
        camara.translateZProperty().set(-500);
        camara.setNearClip(1);
        camara.setFarClip(1000);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W: camara.translateZProperty().set(camara.getTranslateZ()+40);
                    break;
                case S: camara.translateZProperty().set(camara.getTranslateZ()-40);
                    break;
                case A: camara.translateXProperty().set(camara.getTranslateX()-40);
                    break;
                case D: camara.translateXProperty().set(camara.getTranslateX()+40);
                    break;
                case E: camara.translateYProperty().set(camara.getTranslateY()-40);
                    break;
                case Q: camara.translateYProperty().set(camara.getTranslateY()+40);
                    break;
            }
        });
        primaryStage.setTitle("Ejemplo esfera 3D");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}