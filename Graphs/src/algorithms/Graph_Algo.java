package algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	
	@Override
	public void save(String file_name) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file_name))) {
			for (node_data n : g.getV()) {
				// format:
				// src,dst1;w1,dst2;w2,dst3;w3,...
				String dests = "";
				for (edge_data e : g.getE(n.getKey())) {
					dests += e.getDest() + ";" + e.getWeight() + ",";
				}
				// delete last comma
				if (!dests.isEmpty()) {
					dests = dests.substring(0, dests.length() - 1);
				}
				pw.println(n.getKey() + ":" + n.getLocation().x() + "-" + n.getLocation().y() + "," + dests);
			}
		} catch (IOException e1) {
			this.g = null;
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
