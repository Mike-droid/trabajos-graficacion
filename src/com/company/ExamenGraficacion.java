package com.company;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class ExamenGraficacion extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double anclaX, anclaY;
    private double anclaAnguloX = 0;
    private double anclaAnguloY = 0;
    private final DoubleProperty anguloX = new SimpleDoubleProperty(0);
    private final DoubleProperty anguloY = new SimpleDoubleProperty(0);
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creando los objetos del campo de fútbol
        Box campo = TexturaCampo();
        Cylinder poste1 = CrearPoste();
        Cylinder poste2 = CrearPoste();
        Cylinder poste3 = CrearPoste();
        Sphere balon = CrearBalon();

        //Añadiendo los objetos al grupo
        Group grupo = new Group();
        grupo.getChildren().add(campo);
        grupo.getChildren().add(poste1);
        grupo.getChildren().add(poste2);
        grupo.getChildren().add(poste3);
        grupo.getChildren().add(balon);
        grupo.getChildren().addAll(lightEffect());

        //Creando la cámara de perspectiva
        Camera camara = new PerspectiveCamera();

        //Creando la escena
        Scene escena = new Scene(grupo, WIDTH, HEIGHT, true);
        escena.setFill(Color.LIGHTBLUE);
        escena.setCamera(camara);

        //Moviendo los objetos
        campo.translateXProperty().set(WIDTH/2);
        campo.translateYProperty().set(HEIGHT/2 +250);

        balon.translateXProperty().set(WIDTH/2);
        balon.translateYProperty().set(HEIGHT/2 + 220);

        poste1.translateXProperty().set(WIDTH/2 + 400);
        poste1.translateYProperty().set(HEIGHT/2);
        poste1.translateZProperty().set(1000);

        poste2.translateXProperty().set(WIDTH/2 - 400);
        poste2.translateYProperty().set(HEIGHT/2);
        poste2.translateZProperty().set(1000);

        poste3.translateXProperty().set(WIDTH/2);
        poste3.translateYProperty().set(HEIGHT/2 - 350);
        poste3.translateZProperty().set(1000);
        poste3.rotateProperty().set(90);

        initMouseControl(grupo, escena, primaryStage);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case UP -> camara.translateZProperty().set(camara.getTranslateZ()+50);
                case DOWN -> camara.translateZProperty().set(camara.getTranslateZ()-50);
                case LEFT -> camara.translateXProperty().set(camara.getTranslateX()-50);
                case RIGHT -> camara.translateXProperty().set(camara.getTranslateX()+50);
            }
        });

        primaryStage.setTitle("Campo de fútbol - Examen de Graficación - Miguel Ángel Reyes Moreno - 17430111");
        primaryStage.setScene(escena);
        primaryStage.show();

        mueveBalon(primaryStage, balon);
    }

    private Box TexturaCampo() {
        PhongMaterial texturaSuelo = new PhongMaterial();
        texturaSuelo.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/grass.jpg")));
        Box suelo = new Box(2000,20,2000);
        suelo.setMaterial(texturaSuelo);
        return suelo;
    }

    private Cylinder CrearPoste() {
        PhongMaterial texturaArbol = new PhongMaterial(Color.WHITE);
        Cylinder arbol = new Cylinder(30,750);
        arbol.setMaterial(texturaArbol);
        return arbol;
    }

    private Sphere CrearBalon() {
        PhongMaterial texturaHojas = new PhongMaterial();
        texturaHojas.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/football.jpg")));
        Sphere hojas = new Sphere(30);
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

    private void mueveBalon(Stage stage, Sphere balon){
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()){
                case W: balon.translateZProperty().set(balon.getTranslateZ()+40);
                    break;
                case S: balon.translateZProperty().set(balon.getTranslateZ()-40);
                    break;
                case A: balon.translateXProperty().set(balon.getTranslateX()-40);
                    break;
                case D: balon.translateXProperty().set(balon.getTranslateX()+40);
                    break;
                case E: balon.translateYProperty().set(balon.getTranslateY()-40);
                    break;
                case Q: balon.translateYProperty().set(balon.getTranslateY()+40);
                    break;
            }
        });
    }

    private Node[] lightEffect() {
        PointLight pointLight = new PointLight();
        pointLight.getTransforms().add(new Translate(100,100,0));
        pointLight.setColor(Color.GOLD);

        Sphere puntito = new Sphere(3);
        puntito.getTransforms().setAll(pointLight.getTransforms());

        return new Node[]{pointLight, puntito};
    }
}














