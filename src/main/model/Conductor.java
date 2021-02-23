package main.model;

import java.util.Observable;

public class Conductor extends Observable implements Runnable{
    Vehiculo vehiculo;
    boolean status;

    public Conductor(Vehiculo vehiculo){
        this.vehiculo = vehiculo;
        status = true;
    }

    @Override
    public void run() {
        while (status){
            this.setChanged();
            this.notifyObservers(vehiculo);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                status = false;
            }
        }
    }
}
