package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dataStructure.DGraph;
import dataStructure.node_data;
import elements.node;

public class DGraphTest {
	private DGraph g;

	@Before
	public void setup() {
		this.g = new DGraph();
		this.g.addNode(new node(1, null));
		this.g.addNode(new node(2, null));
		this.g.addNode(new node(3, null));
		this.g.connect(1, 2, 5);
		this.g.connect(2, 3, 6);
	}

	@Test
	public void testGetNode() {
		assertEquals(1, this.g.getNode(1).getKey());
		assertEquals(2, this.g.getNode(2).getKey());
		assertEquals(3, this.g.getNode(3).getKey());
		assertEquals(null, this.g.getNode(4));
	}

	@Test
	public void testGetEdge() {
		assertEquals(1, this.g.getEdge(1, 2).getSrc());
		assertEquals(2, this.g.getEdge(1, 2).getDest());
		assertEquals(2, this.g.getEdge(2, 3).getSrc());
		assertEquals(3, this.g.getEdge(2, 3).getDest());
		assertEquals(null, this.g.getEdge(1, 3));
	}

	@Test
	public void testGetV() {
		assertEquals(3, this.g.getV().size());
	}

	@Test
	public void testRemoveNode1() {
		node_data removed;
		removed = this.g.removeNode(4);
		assertEquals(null, removed);
		assertEquals(3, this.g.nodeSize());
		assertEquals(2, this.g.edgeSize());
		removed = this.g.removeNode(2);
		assertEquals(2, removed.getKey());
		assertEquals(2, this.g.nodeSize());
		assertEquals(0, this.g.edgeSize());
		removed = this.g.removeNode(2);
		assertEquals(null, removed);
	}

	@Test
	public void testRemoveNode2() {
		node_data removed;
		removed = this.g.removeNode(1);
		assertEquals(1, removed.getKey());
		assertEquals(2, this.g.nodeSize());
		assertEquals(1, this.g.edgeSize());
		removed = this.g.removeNode(3);
		assertEquals(3, removed.getKey());
		assertEquals(1, this.g.nodeSize());
		assertEquals(0, this.g.edgeSize());
	}
}