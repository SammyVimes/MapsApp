package ru.elweb.mapsapp.test.graphics;

import java.util.Random;

/**
 * Created by Semyon Danilov on 11.04.2014.
 */
public class Vertex {

    protected double x = 0;
    protected double y = 0;

    protected int id = 0;

    private static Random random = new Random();

    public Vertex() {
        x = (int) (200 + (random.nextFloat() * 300));
        y = (int) (100 + (random.nextFloat() * 200));
    }

    //represents force of the "net" towards other points
    protected double netForceX = 0;
    protected double netForceY = 0;

    protected double velocityX = 0.0f;
    protected double velocityY = 0.0f;

    protected boolean isDragged = false; //

}
