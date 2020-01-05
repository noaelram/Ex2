package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

public class Graph_GUI extends JFrame {

	private JButton loadGraph = new JButton("Load Graph from File");
	private JButton saveGraph = new JButton("Save Graph to File");
	private JButton drawGraph = new JButton("Draw Graph");
	private JButton runIsConnected = new JButton("Run isConnected");
	private JButton runShortestPathDist = new JButton("Run shortestPathDist");
	private JButton runShortestPath = new JButton("Run shortestPath");
	private JButton runTsp = new JButton("Run TSP");
	private JLabel targetsLabel = new JLabel("Targets (list of keys seperated by ','):");
	private JTextField targetsField = new JTextField();
	private JLabel srcLabel = new JLabel("src key:");
	private JTextField srcField = new JTextField();
	private JLabel destLabel = new JLabel("dest key:");
	private JTextField destField = new JTextField();
	private Graph_Algo ga;

	public static void main(String[] args) {
		new Graph_GUI(null).setVisible(true);
	}

	public Graph_GUI(graph g) {
		this.ga = new Graph_Algo(g);
		initializeUI();
		this.loadGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadGraph();
				System.out.println("Graph loaded");
			}
		});
		this.saveGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveGraph();
				System.out.println("Graph saved");
			}
		});
		this.runIsConnected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("isConnected = " + Graph_GUI.this.ga.isConnected());
			}
		});
		this.runShortestPathDist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int src = Integer.valueOf(srcField.getText());
				int dest = Integer.valueOf(destField.getText());
				System.out.println("shortestPathDist = " + Graph_GUI.this.ga.shortestPathDist(src, dest));
			}
		});
		this.runShortestPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int src = Integer.valueOf(srcField.getText());
				int dest = Integer.valueOf(destField.getText());
				List<node_data> path = Graph_GUI.this.ga.shortestPath(src, dest);
				if (path == null) {
					System.out.println("shortestPath = null");

				} else {
					String s = "";
					for (int i = 0; i < path.size(); i++) {
						s += path.get(i).getKey();
						if (i < path.size() - 1) {
							s += "->";
						}
					}
					System.out.println("shortestPath = " + s);
				}
			}
		});
		this.runTsp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] keys = targetsField.getText().split(",");
				List<Integer> l = new ArrayList<Integer>();
				for (int i = 0; i < keys.length; i++) {
					l.add(Integer.valueOf(keys[i]));
				}
				List<node_data> path = Graph_GUI.this.ga.TSP(l);
				if (path == null) {
					System.out.println("TSP = null");

				} else {
					String s = "";
					for (int i = 0; i < path.size(); i++) {
						s += path.get(i).getKey();
						if (i < path.size() - 1) {
							s += "->";
						}
					}
					System.out.println("TSP = " + s);
				}
			}
		});

		this.drawGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawGraph();
			}
		});
	}

	private void initializeUI() {
		this.setTitle("Graph GUI");
		Dimension fieldSize = new Dimension(70, 24);
		this.srcField.setPreferredSize(fieldSize);
		this.destField.setPreferredSize(fieldSize);
		fieldSize = new Dimension(200, 24);
		this.targetsField.setPreferredSize(fieldSize);
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		north.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		north.add(loadGraph);
		north.add(saveGraph);
		north.add(drawGraph);
		north.add(runIsConnected);
		//
		// dist
		// path

		JPanel center = new JPanel();
		center.add(srcLabel);
		center.add(srcField);
		center.add(destLabel);
		center.add(destField);
		center.add(runShortestPathDist);
		center.add(runShortestPath);

		// tsp
		JPanel south = new JPanel();
		south.add(targetsLabel);
		south.add(targetsField);
		south.add(runTsp);

		// add panels to main frame
		this.setLocationRelativeTo(null);
		this.setBounds(100, 100, 800, 150);
		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}

	public void loadGraph() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ga.init(selectedFile.getAbsolutePath());
		}
	}

	public void saveGraph() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ga.save(selectedFile.getAbsolutePath());
		}
	}

	public void drawGraph() {
		if (this.ga.getGraph().nodeSize() == 0) {
			JOptionPane.showMessageDialog(null, "There is nothing to draw, the graph is empty!");
			return;
		}
		int canvasWidth = 800;
		int canvasHeight = 800;
		StdDraw.setCanvasSize(canvasWidth, canvasHeight);
		double maxX = this.ga.getGraph().getV().parallelStream().map(n -> n.getLocation().x()).max(Double::compareTo)
				.get();
		double maxY = this.ga.getGraph().getV().parallelStream().map(n -> n.getLocation().y()).max(Double::compareTo)
				.get();
		double minX = this.ga.getGraph().getV().parallelStream().map(n -> n.getLocation().x()).min(Double::compareTo)
				.get();
		double minY = this.ga.getGraph().getV().parallelStream().map(n -> n.getLocation().y()).min(Double::compareTo)
				.get();
		StdDraw.setXscale(minX - 1, maxX + 1);
		StdDraw.setYscale(minY - 1, maxY + 1);
		// Draw vertices
		for (node_data n : this.ga.getGraph().getV()) {
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.01);
			StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
			Point3D l = n.getLocation();
			double x = l.x();
			double y = l.y();
			System.out.println("Drawing vertex: " + x + "," + y);
			StdDraw.point(x, y);
			StdDraw.text(x, y + 0.05, Integer.toString(n.getKey()));
		}
		// Draw edges
		for (node_data n : this.ga.getGraph().getV()) {
			for (edge_data e : this.ga.getGraph().getE(n.getKey())) {
				node_data n2 = this.ga.getGraph().getNode(e.getDest());
				StdDraw.setPenColor(Color.RED);
				StdDraw.setPenRadius(0.001);
				StdDraw.line(n.getLocation().x(), n.getLocation().y(), n2.getLocation().x(), n2.getLocation().y());
				StdDraw.text(n.getLocation().x() + ((n2.getLocation().x() - n.getLocation().x()) * 0.5),
						n.getLocation().y() + ((n2.getLocation().y() - n.getLocation().y()) * 0.5),
						Double.toString(e.getWeight()));
				StdDraw.setPenColor(Color.YELLOW);
				StdDraw.setPenRadius(0.01);
				StdDraw.point(n.getLocation().x() + ((n2.getLocation().x() - n.getLocation().x()) * 0.9),
						n.getLocation().y() + ((n2.getLocation().y() - n.getLocation().y()) * 0.9));
			}
		}
	}

}
