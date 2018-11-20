package utils;

import java.util.ArrayList;
import java.util.Collections;

public class AStar {
	
	private int[][] map;
	private ArrayList<Node> nodes;
	private ArrayList<Node> closedNodes, openNodes;
	private Node origin, destination;

	public AStar(int[][] map) {
		this.map = map;
	}

	public ArrayList<Node> findPath(int x1, int y1, int x2, int y2) {
		nodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		openNodes = new ArrayList<Node>();

		// A node is added for each passable cell in the map
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0)
					nodes.add(new Node(i, j));
			}

		origin = nodes.get(nodes.indexOf(new Node(x1, y1)));
		destination = nodes.get(nodes.indexOf(new Node(x2, y2)));

		Node currentNode = origin;
		while (!currentNode.equals(destination)) {
			processNode(currentNode);
			currentNode = getMinFValueNode();
		}

		return retrievePath();
	}

	private ArrayList<Node> retrievePath() {
		ArrayList<Node> path = new ArrayList<Node>();
		Node node = destination;

		while (!node.equals(origin)) {
			path.add(node);
			node = node.getParent();
		}

		Collections.reverse(path);

		return path;
	}

	private void processNode(Node node) {

		ArrayList<Node> adj = getAdjacentNodes(node);

		openNodes.remove(node);
		closedNodes.add(node);

		for (Node n : adj) {

			if (closedNodes.contains(n))
				continue;

			//Compute the Manhattan distance from node 'n' to destination
			int h = Math.abs(destination.getX() - n.getX());
			h += Math.abs(destination.getY() - n.getY());

			//Compute the distance from origin to node 'n' 
			int g = node.getG();
			if (node.getX() == n.getX() || node.getY() == n.getY())
				g += 10;
			else
				g += 14;

			if (!openNodes.contains(n)) {

				n.setParent(node);
				n.setH(h);
				n.setG(g);

				openNodes.add(n);
			} else {

				if (h + g < n.getF()) {

					n.setParent(node);
					n.setH(h);
					n.setG(g);
				}
			}
		}
	}

	private Node getMinFValueNode() {
		Node node = openNodes.get(0);

		for (Node n : openNodes)
			if (node.getF() > n.getF())
				node = n;

		return node;
	}

	private ArrayList<Node> getAdjacentNodes(Node node) {
		ArrayList<Node> adjCells = new ArrayList<Node>();

		int x = node.getX();
		int y = node.getY();

		if (nodes.indexOf(new Node(x + 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y))));

		if (nodes.indexOf(new Node(x, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x, y + 1))));

		if (nodes.indexOf(new Node(x - 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y))));

		if (nodes.indexOf(new Node(x, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x, y - 1))));

		if (nodes.indexOf(new Node(x - 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y - 1))));

		if (nodes.indexOf(new Node(x + 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y + 1))));

		if (nodes.indexOf(new Node(x - 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x - 1, y + 1))));

		if (nodes.indexOf(new Node(x + 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new Node(x + 1, y - 1))));

		return adjCells;
	}

	private void mergePath(ArrayList<Node> path) {
		for(Node node : path)
			map[node.getX()][node.getY()] = 2;
		
	}
	
	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				switch (map[i][j]) {
				case 0:
					System.out.print("   ");
					break;
					
				case 1:
					System.out.print("XXX");
					break;
					
				case 2:
					System.out.print(" o ");
					break;
				}
				
			System.out.println();
		}
	}

	public static void main(String[] args) {
		
		MazeGenerator mg = new MazeGenerator((40 - 1) / 4, (40 - 1) / 2);
		
		AStar a = new AStar(mg.getMaze());
		
		System.out.println("The maze to resolve:");
		a.printMap();

		ArrayList<Node> bestPath = a.findPath(1, 1, 35, 37);
		
		a.mergePath(bestPath);
		
		System.out.println();
		System.out.println("The best path:");
		a.printMap();
	}
}
