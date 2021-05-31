package com.company;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Objects;

public class EjemploReflejoLuz extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double anclaX, anclaY;
    private double anclaAnguloX = 0;
    private double anclaAnguloY = 0;
    private final DoubleProperty anguloX = new SimpleDoubleProperty(0);
    private final DoubleProperty anguloY = new SimpleDoubleProperty(0);

    public void start(Stage primaryStage) throws Exception {
        Box madera = TexturaBox();
        Group grupo = new Group();
        grupo.getChildren().add(madera);
        Camera camara = new PerspectiveCamera(true);
        Scene s = new Scene(grupo, WIDTH, HEIGHT);
        s.setFill(Color.LIGHTBLUE);
        s.setCamera(camara);
        camara.translateZProperty().set(-500);
        camara.setNearClip(1);
        camara.setFarClip(1000);
        initMouseControl(grupo, s, primaryStage);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W -> camara.translateZProperty().set(camara.getTranslateZ()+40);
                case S -> camara.translateZProperty().set(camara.getTranslateZ()-40);
                case A -> camara.translateXProperty().set(camara.getTranslateX()-40);
                case D -> camara.translateXProperty().set(camara.getTranslateX()+40);
            }
        });
        primaryStage.setTitle("Ejemplo Rotar mouse");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private Box TexturaBox(){
        PhongMaterial textura = new PhongMaterial();
        textura.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/marble.png")));
        textura.setSpecularMap(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/neon.png"))));
        Box madera = new Box(50,20,50);
        madera.setMaterial(textura);
        return  madera;
    }

    private void initMouseControl(Group grupo, Scene s, Stage stage) {
        Rotate xRotate;
        Rotate yRotate;
        grupo.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotate.angleProperty().bind(anguloX);
        yRotate.angleProperty().bind(anguloY);

        s.setOnMousePressed(event -> {
            anclaX = event.getSceneX();
            anclaY = event.getSceneY();

            anclaAnguloX = anguloX.get();
            anclaAnguloY = anguloY.get();
        });

        s.setOnMouseDragged(event -> {
            anguloX.set(anclaAnguloX- (anclaY - event.getSceneY()));
            anguloY.set(anclaAnguloY- (anclaX - event.getSceneX()));
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double scrollVertical = event.getDeltaY();
            grupo.translateZProperty().set(grupo.getTranslateZ() - scrollVertical);
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}
