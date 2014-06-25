package ru.elweb.mapsapp.core.server;


public class MapRequestImpl implements MapRequest {

    private int fromID;
    private int toID;

    public MapRequestImpl(final int fromID, final int toID) {
        this.fromID = fromID;
        this.toID = toID;
    }

    @Override
    public int getFromID() {
        return fromID;
    }

    @Override
    public int getToID() {
        return toID;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof MapRequest)) {
            return false;
        }
        MapRequest mr = (MapRequest) obj;
        return fromID == mr.getFromID() && toID == mr.getToID();
    }

    @Override
    public int hashCode() {
        return fromID * 42 + toID * 24;
    }

}
