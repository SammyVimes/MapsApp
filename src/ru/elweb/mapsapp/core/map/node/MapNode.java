package ru.elweb.mapsapp.core.map.node;

import java.util.LinkedList;
import java.util.List;

public class MapNode {

	private int id;
	private List<MapNode> linkedNodes;
	private boolean DEAD_END;
	
	public MapNode(final int id, final MapNode node) {
		linkedNodes = new LinkedList<>();
		linkedNodes.add(node);
		this.id = id;
		DEAD_END = true;
	}
	
	public MapNode(final int id, final List<MapNode> linkedNodes, final boolean DEAD_END) {
		this.id = id;
		this.linkedNodes = linkedNodes;
		this.DEAD_END = DEAD_END;
	}
	
}