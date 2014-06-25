package ru.elweb.mapsapp.core.algorithm;

import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */

/**
 * Abstract algorithm
 */
public interface MapSearchAlgorithm {

    /**
     * The main, the only purpose of all of this a bit complicated stuff: THE SEARCH
     *
     * @param map
     * @param fromId from where to search
     * @param toId   till where to search
     * @return path between nodes
     */
    Path findPath(final EltechMap map, final int fromId, final int toId);

}