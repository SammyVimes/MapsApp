package ru.elweb.mapsapp.core.map.node;

import java.util.LinkedList;
import java.util.List;

public class MapNode {

	ThreadLocal<DijkstraData> dijkstraData; 
	
	private int id;
	private List<MapNode> linkedNodes;
	private boolean DEAD_END;
	
	public MapNode(final int id, final MapNode node) {
		this(id, true);
		linkedNodes = new LinkedList<>();
		linkedNodes.add(node);
	}
	
	public MapNode(final int id, final List<MapNode> linkedNodes, final boolean DEAD_END) {
		this(id, DEAD_END);
		this.linkedNodes = linkedNodes;
	}
	
	public MapNode(final int id, final boolean DEAD_END) {
		this.id = id;
		this.DEAD_END = DEAD_END;
		this.dijkstraData = new ThreadLocal<>();
	}
	
}