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

}
