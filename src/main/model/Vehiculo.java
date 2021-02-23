package main.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vehiculo extends ImageView {
    private double velocityX;
    private double velocityY;
    private int tipo;
    private boolean isPuente;

    public Vehiculo(Image image, int tipo){
        super(image);
        this.tipo = tipo;
        isPuente = false;
    }
    public void setIsPuente(boolean isPuente){
        this.isPuente = isPuente;
    }
    public  boolean getIsPuente() {
        return isPuente;
    }

    public int getTipo(){
        return tipo;
    }

    public void setPosition(double x, double y)
    {
       this.setLayoutX(x);
       this.setLayoutY(y);
    }

    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        this.setLayoutX( this.getLayoutX() + ( velocityX * time));
        this.setLayoutY( this.getLayoutY() + ( velocityY * time));
    }
}