package com.company;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Main3 extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Box caja = new Box(50,100,100);
        Group grupo = new Group();
        grupo.getChildren().add(caja);
        Camera camara = new PerspectiveCamera(true);
        Scene s = new Scene(grupo, WIDTH, HEIGHT);
        s.setFill(Color.BEIGE);
        s.setCamera(camara);
        Transform rotarX = new Rotate(45, new Point3D(1,0,0));
        Transform rotarY = new Rotate(45, new Point3D(0,1,0));
        Transform rotarZ = new Rotate(45, new Point3D(0,0,1));
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
                case E: caja.getTransforms().add(rotarX);
                    break;
                case Q: caja.getTransforms().add(rotarY);
                    break;
                case Z: caja.getTransforms().add(rotarZ);
                    break;
            }
        });
        primaryStage.setTitle("Ejemplo esfera 3D");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}