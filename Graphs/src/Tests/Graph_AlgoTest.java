package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import elements.node;
import utils.Point3D;

public class Graph_AlgoTest {
	private Graph_Algo ga;
	private DGraph g;

	@Before
	public void setup() {
		g = new DGraph();
		g.addNode(new node(0, new Point3D(1, 1)));
		g.addNode(new node(1, new Point3D(1, 2)));
		g.addNode(new node(2, new Point3D(2, 1)));
		g.addNode(new node(3, new Point3D(2, 2)));
		g.addNode(new node(4, new Point3D(3, 1.5)));
		// 0
		g.connect(0, 1, 3);
		g.connect(0, 3, 7);
		g.connect(0, 4, 8);
		// 1
		g.connect(1, 2, 1);
		g.connect(1, 3, 4);
		// 2
		// 3
		g.connect(3, 2, 2);
		// 4
		g.connect(4, 3, 3);
		this.ga = new Graph_Algo();
		this.ga.init(g);
	}

	@Test
	public void testIsConnected() {
		assertEquals(false, this.ga.isConnected());
		this.g.connect(2, 0, 5);
		assertEquals(true, this.ga.isConnected());
	}

	@Test
	public void testShortestPathDist() {
		assertEquals(0, this.ga.shortestPathDist(0, 0));
		assertEquals(3, this.ga.shortestPathDist(0, 1));
		assertEquals(4, this.ga.shortestPathDist(0, 2));
		assertEquals(7, this.ga.shortestPathDist(0, 3));
		assertEquals(8, this.ga.shortestPathDist(0, 4));
		assertEquals(-1, this.ga.shortestPathDist(1, 0));
		assertEquals(1, this.ga.shortestPathDist(1, 2));
		assertEquals(4, this.ga.shortestPathDist(1, 3));
		assertEquals(-1, this.ga.shortestPathDist(1, 4));
		assertEquals(-1, this.ga.shortestPathDist(2, 0));
		assertEquals(-1, this.ga.shortestPathDist(2, 1));
		assertEquals(-1, this.ga.shortestPathDist(2, 3));
		assertEquals(-1, this.ga.shortestPathDist(2, 4));
		assertEquals(-1, this.ga.shortestPathDist(3, 0));
		assertEquals(-1, this.ga.shortestPathDist(3, 1));
		assertEquals(2, this.ga.shortestPathDist(3, 2));
		assertEquals(-1, this.ga.shortestPathDist(3, 4));
		assertEquals(-1, this.ga.shortestPathDist(4, 0));
		assertEquals(-1, this.ga.shortestPathDist(4, 1));
		assertEquals(5, this.ga.shortestPathDist(4, 2));
		assertEquals(3, this.ga.shortestPathDist(4, 3));
	}

	private static void assertPath(int[] keys, List<node_data> path) {
		if (keys == null) {
			assertEquals(null, path);
			return;
		}
		for (int i = 0; i < path.size(); i++) {
			String s = "[ ";
			for (int j = 0; j < keys.length; j++) {
				s += keys[j] + " ";
			}
			s += "] [ ";
			for (int j = 0; j < path.size(); j++) {
				s += path.get(j).getKey() + " ";
			}
			s += "]";
			assertEquals(keys[i], path.get(i).getKey(), s);
		}
	}

	@Test
	public void testShortestPath() {
		assertPath(new int[] { 0 }, this.ga.shortestPath(0, 0));
		assertPath(new int[] { 0, 1 }, this.ga.shortestPath(0, 1));
		assertPath(new int[] { 0, 1, 2 }, this.ga.shortestPath(0, 2));
		assertPath(new int[] { 0, 3 }, this.ga.shortestPath(0, 3));
		assertPath(new int[] { 0, 4 }, this.ga.shortestPath(0, 4));
		assertPath(null, this.ga.shortestPath(1, 0));
		assertPath(new int[] { 1, 2 }, this.ga.shortestPath(1, 2));
		assertPath(new int[] { 1, 3 }, this.ga.shortestPath(1, 3));
		assertPath(null, this.ga.shortestPath(1, 4));
		assertPath(null, this.ga.shortestPath(2, 0));
		assertPath(null, this.ga.shortestPath(2, 1));
		assertPath(null, this.ga.shortestPath(2, 3));
		assertPath(null, this.ga.shortestPath(2, 4));
		assertPath(null, this.ga.shortestPath(3, 0));
		assertPath(null, this.ga.shortestPath(3, 1));
		assertPath(new int[] { 3, 2 }, this.ga.shortestPath(3, 2));
		assertPath(null, this.ga.shortestPath(3, 4));
		assertPath(null, this.ga.shortestPath(4, 0));
		assertPath(null, this.ga.shortestPath(4, 1));
		assertPath(new int[] { 4, 3, 2 }, this.ga.shortestPath(4, 2));
		assertPath(new int[] { 4, 3 }, this.ga.shortestPath(4, 3));
	}

	@Test
	public void testCopy() {
		graph other = this.ga.copy();
		assertEquals(other.nodeSize(), this.g.nodeSize());
		assertEquals(other.edgeSize(), this.g.edgeSize());
		node_data n = this.g.removeNode(3);
		assertEquals(4, this.g.nodeSize());
		assertEquals(3, this.g.edgeSize());
		assertEquals(5, other.nodeSize());
		assertEquals(7, other.edgeSize());
		n.setTag(-1);
		other.getNode(3).setTag(-2);
		assertEquals(-2, other.getNode(3).getTag());
		assertEquals(-1, n.getTag());
	}

	@Test
	public void testTSP() {
		List<Integer> mustNodes = new ArrayList<Integer>();
		mustNodes.add(0);
		List<node_data> path = this.ga.TSP(mustNodes);
		assertPath(new int[] { 0 }, path);
		mustNodes.add(2);
		path = this.ga.TSP(mustNodes);
		assertPath(new int[] { 0, 1, 2 }, path);
		mustNodes.add(3);
		path = this.ga.TSP(mustNodes);
		assertPath(new int[] { 0, 3, 2 }, path);
	}

	@Test
	public void testinitAndSave() {
		this.ga.save("tmp.txt");
		this.ga.init("tmp.txt");
		graph g = this.ga.copy();
		assertEquals(5, g.nodeSize());
		assertEquals(7, g.edgeSize());
	}
}