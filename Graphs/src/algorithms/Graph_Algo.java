package algorithms;

import java.io.IOException;
import java.util.List;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import elements.node;
import utils.Point3D;



public class Graph_Algo implements graph_algorithms {

	private graph g;

	public graph getGraph() {
		return g;
	}

	@Override
	public void init(graph g) {
		this.g = g;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithms.graph_algorithms#init(java.lang.String) Just go over the file
	 * lines and parse
	 */
	@Override
	public void init(String file_name) {
		this.g = new DGraph();
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(file_name));
			for (String line : lines) {
				String[] parts = line.split(",");
				String[] tmp = parts[0].split(":");
				String[] xy = tmp[1].split("-");
				Point3D location = new Point3D(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
				int src = Integer.parseInt(tmp[0]);
				this.g.addNode(new node(src, location));
				for (int i = 1; i < parts.length; i++) {
					String[] parts2 = parts[i].split(";");
					int dst = Integer.parseInt(parts2[0]);
					double weight = Double.parseDouble(parts2[1]);
					this.g.connect(src, dst, weight);
				}
			}
		} catch (IOException e) {
			this.g = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
