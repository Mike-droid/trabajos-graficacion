package com.company;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import java.util.Objects;

public class DosLuces extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private double anclaX, anclaY, anclaAnguloX = 0, anclaAnguloY = 0;

    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Box caja = TextureBox();
        Group group = new Group(caja);
        group.getChildren().addAll(lightEffect());
        Camera camera = new PerspectiveCamera();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.STEELBLUE);
        scene.setCamera(camera);

        Transform rotateX = new Rotate(30, new Point3D(1, 0, 0));
        Transform rotateY = new Rotate(30, new Point3D(0, 1, 0));
        Transform rotateZ = new Rotate(30, new Point3D(0, 0, 1));

        camera.translateZProperty().set(-500);
        camera.translateXProperty().set(-300);
        camera.translateYProperty().set(-250);
        camera.setNearClip(1);
        camera.setFarClip(2000);
        initMouseControl(group, scene, primaryStage);

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W -> camera.translateZProperty().set(camera.getTranslateZ() + 30);
                case S -> camera.translateZProperty().set(camera.getTranslateZ() - 30);
                case D -> camera.translateXProperty().set(camera.getTranslateX() + 30);
                case A -> camera.translateXProperty().set(camera.getTranslateX() - 30);
                case Q -> group.translateYProperty().set(group.getTranslateY() - 30);
                case E -> group.translateYProperty().set(group.getTranslateY() + 30);
            }
        });

        primaryStage.setTitle("Luces");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initMouseControl(Group group, Scene scene, Stage stage) {
        Rotate xRotate, yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);
        scene.setOnMousePressed(event -> {
            anclaX = event.getSceneX();
            anclaY = event.getSceneY();
            anclaAnguloX = angleX.get();
            anclaAnguloY = angleY.get();
        });
        scene.setOnMouseDragged(event -> {
            angleX.set(anclaAnguloX - (anclaY - event.getSceneY()));
            angleY.set(anclaAnguloY - (anclaX - event.getSceneX()));
        });
        stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double scroll = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() - scroll);
        });
    }

    private Box TextureBox() {
        PhongMaterial texture = new PhongMaterial();
        texture.setDiffuseMap(new Image(getClass().getResourceAsStream("/assets/marble.png")));

        texture.setSpecularMap(new Image(getClass().getResourceAsStream("/assets/neon.png")));
        Box caja = new Box(350, 160, 400);
        caja.setMaterial(texture);
        return caja;
    }

    //private Node lightEffect() {
        private Node[] lightEffect () {
//        AmbientLight ambientLight = new AmbientLight();
//        ambientLight.setColor(Color.BLUEVIOLET);
//        return ambientLight;

            PointLight pointLight = new PointLight();
            pointLight.getTransforms().add(new Translate(300, -300, 0));
            pointLight.setColor(Color.AQUA);

            Sphere sphere = new Sphere(5);
            sphere.getTransforms().setAll(pointLight.getTransforms());

//        return pointLight;
            return new Node[]{pointLight, sphere};
        }
    //}

    public static void main(String[] args) {
        launch(args);
    }
}