package com.company;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Arbol extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double anclaX, anclaY;
    private double anclaAnguloX = 0;
    private double anclaAnguloY = 0;
    private final DoubleProperty anguloX = new SimpleDoubleProperty(0);
    private final DoubleProperty anguloY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage primaryStage) throws Exception {
        Sphere hojas = TexturaHojas();
        Box suelo = TexturaSuelo();
        Cylinder arbol_tronco = TexturaArbol();

        Group grupo = new Group();
        grupo.getChildren().add(hojas);
        grupo.getChildren().add(suelo);
        grupo.getChildren().add(arbol_tronco);

        Camera camara = new PerspectiveCamera();

        Scene escena = new Scene(grupo, WIDTH, HEIGHT, true);
        escena.setFill(Color.BEIGE);
        escena.setCamera(camara);

        //Moviendo los objetos
        hojas.translateXProperty().set(WIDTH/2);
        hojas.translateYProperty().set(HEIGHT/2 - 50);

        suelo.translateXProperty().set(WIDTH/2);
        suelo.translateYProperty().set(HEIGHT/2 + 100);

        arbol_tronco.translateXProperty().set(WIDTH/2);
        arbol_tronco.translateYProperty().set(HEIGHT/2);

        initMouseControl(grupo, escena, primaryStage);

        primaryStage.setTitle("Parque");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private Box TexturaSuelo(){
        PhongMaterial texturaSuelo = new PhongMaterial();
        texturaSuelo.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/grass.jpg")));
        Box suelo = new Box(200,20,200);
        suelo.setMaterial(texturaSuelo);
        return suelo;
    }

    private Cylinder TexturaArbol(){
        PhongMaterial texturaArbol = new PhongMaterial();
        texturaArbol.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/tree.jpg")));
        Cylinder arbol = new Cylinder(40,200);
        arbol.setMaterial(texturaArbol);
        return arbol;
    }

    private Sphere TexturaHojas(){
        PhongMaterial texturaHojas = new PhongMaterial();
        texturaHojas.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/leaves.jpg")));
        Sphere hojas = new Sphere(65);
        hojas.setMaterial(texturaHojas);
        return hojas;
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
