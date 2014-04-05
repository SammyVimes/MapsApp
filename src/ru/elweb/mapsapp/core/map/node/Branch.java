package ru.elweb.mapsapp.core.map.node;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public class Branch {

    protected int length;
    protected MapNode node;

    public  Branch(final int length, final MapNode node) {
        this.length = length;
        this.node = node;
    }

}
