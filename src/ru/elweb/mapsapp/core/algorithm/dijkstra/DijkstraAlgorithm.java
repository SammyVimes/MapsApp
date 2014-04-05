package ru.elweb.mapsapp.core.algorithm.dijkstra;

import ru.elweb.mapsapp.core.algorithm.MapSearchAlgorithm;
import ru.elweb.mapsapp.core.annotation.MethodDescription;
import ru.elweb.mapsapp.core.map.EltechMap;
import ru.elweb.mapsapp.core.map.Path;

/**
 * Created by Semyon Danilov on 05.04.2014.
 */
public class DijkstraAlgorithm implements MapSearchAlgorithm {


    @Override
    @MethodDescription(link = "http://ru.wikipedia.org/wiki/%C0%EB%E3%EE%F0%E8%F2%EC_%C4%E5%E9%EA%F1%F2%F0%FB")
    public Path findPath(final EltechMap map, final int fromId, final int toId) {
        return null;
    }

}
