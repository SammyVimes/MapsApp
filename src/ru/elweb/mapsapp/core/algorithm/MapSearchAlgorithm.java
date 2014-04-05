package ru.elweb.mapsapp.core.algorithm;

import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public interface MapSearchAlgorithm {

    Path findPath(final EltechMap map, final int fromId, final int toId);

}