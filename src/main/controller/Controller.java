package main.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import main.model.Conductor;
import main.model.Vehiculo;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Semaphore;


public class Controller implements Observer {

    @FXML
    private AnchorPane rootParent;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Rectangle recPuente01;

    @FXML
    private Rectangle recPuente02;


    private Vehiculo car01;
    private Vehiculo car02;
    private Vehiculo car03;
    private Vehiculo car04;
    private Semaphore puente01;
    private Semaphore puente02;

    @FXML
    public void initialize(){
        //Pintado vehículos Norte a Sur
        //Carro 01
        car01 = new Vehiculo(new Image("resources/auto1.png"),1);
        car01.setFitWidth(80);
        car01.setPreserveRatio(true);
        car01.setPosition(100,280);
        car01.setVelocity(10.0,0.0);
        //Carro 02
        car02 = new Vehiculo(new Image("resources/auto2.png"),1);
        car02.setFitWidth(80);
        car02.setPreserveRatio(true);
        car02.setPosition(0,280);
        car02.setVelocity(10.0,0.0);

        //Pintado vehículos Sur a Norte
        //Carro 03
        car03 = new Vehiculo(new Image("resources/auto5.png"),2);
        car03.setFitWidth(80);
        car03.setPreserveRatio(true);
        car03.setPosition(1100,170);
        car03.setVelocity(-10.0,0.0);
        //Carro 04
        car04 = new Vehiculo(new Image("resources/auto6.png"),2);
        car04.setFitWidth(80);
        car04.setPreserveRatio(true);
        car04.setPosition(1200,170);
        car04.setVelocity(-10.0,0.0);

        rootParent.getChildren().addAll(car01,car02,car03,car04);
        puente01 = new Semaphore(1);
        puente02 = new Semaphore(1);

    }

    @FXML
    void btnIniciarMouseClicked(MouseEvent event) {
        Conductor chofer01 = new Conductor(car01);
        chofer01.addObserver(this);
        Thread hilo1 = new Thread(chofer01);
        hilo1.start();

        Conductor chofer03 = new Conductor(car03);
        chofer03.addObserver(this);
        Thread hilo3 = new Thread(chofer03);
        hilo3.start();

    }

    @FXML
    void btnNuevoOnMouseClicked(MouseEvent event) {
        Conductor chofer02 = new Conductor(car02);
        chofer02.addObserver(this);
        Thread hilo2 = new Thread(chofer02);
        hilo2.start();

        Conductor chofer04 = new Conductor(car04);
        chofer04.addObserver(this);
        Thread hilo4 = new Thread(chofer04);
        hilo4.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        Vehiculo nodo;

        nodo = ((Vehiculo) arg);
        Platform.runLater( ()->nodo.update(2));

        if (nodo.getTipo() == 1) {
            if (nodo.getBoundsInParent().getMaxX() > 411  &&
                    nodo.getBoundsInParent().getMinX() <= 572 && !nodo.getIsPuente())
                try {
                    puente01.acquire();
                    nodo.setIsPuente(true);
                } catch (InterruptedException e) { }
            if (nodo.getBoundsInParent().getMinX() > 572 &&
                    nodo.getBoundsInParent().getMaxX() <= 803 && nodo.getIsPuente()) {
                puente01.release();
                nodo.setIsPuente(false);
            }
            //Puente 02
            if (nodo.getBoundsInParent().getMaxX() > 803 &&
                    nodo.getBoundsInParent().getMinX() <= 946 && !nodo.getIsPuente())
                try {
                    puente02.acquire();
                    nodo.setIsPuente(true);
                } catch (InterruptedException e) { }
            if (nodo.getBoundsInParent().getMinX() > 946 &&
                    nodo.getBoundsInParent().getMaxX() <= 1200 && nodo.getIsPuente()) {
                puente02.release();
                nodo.setIsPuente(false);
            }

            if (nodo.getBoundsInParent().getMinX() > 1200)
                Thread.currentThread().interrupt();
        }


        if  (nodo.getTipo() == 2) {
            //Puente 02
            if (nodo.getBoundsInParent().getMinX() <= 946 &&
                    nodo.getBoundsInParent().getMaxX() >= 804 && !nodo.getIsPuente()) {
                try {
                    puente02.acquire();
                    nodo.setIsPuente(true);
                } catch (InterruptedException e) { }

            }
            if (nodo.getBoundsInParent().getMaxX() < 804  &&
                    nodo.getBoundsInParent().getMinX() > 572 && nodo.getIsPuente()) {
                puente02.release();
                nodo.setIsPuente(false);
            }

            //Puente01
            if (nodo.getBoundsInParent().getMinX() <= 572 &&
                    nodo.getBoundsInParent().getMaxX() >= 412 && !nodo.getIsPuente()) {
                try {
                    puente01.acquire();
                    nodo.setIsPuente(true);
                } catch (InterruptedException e) { }

            }
            if (nodo.getBoundsInParent().getMaxX() < 412 &&
                    nodo.getBoundsInParent().getMinX() > 5 && nodo.getIsPuente()) {
                puente01.release();
                nodo.setIsPuente(false);
            }

            if (nodo.getBoundsInParent().getMinX() < 0)
                Thread.currentThread().interrupt();
        }
    }
}