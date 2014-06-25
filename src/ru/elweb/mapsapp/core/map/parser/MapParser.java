package ru.elweb.mapsapp.core.map.parser;

import ru.elweb.mapsapp.core.map.EltechMap;

/**
 * XML Map parser
 */
public interface MapParser {

    /**
     * Parse the map file
     *
     * @param filePath path to xml
     * @return parsed map
     */
    EltechMap parse(final String filePath);

}
