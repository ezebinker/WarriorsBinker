package utils;


import java.util.Arrays;
import java.util.Collections;

public class MazeGenerator {
	private final int x;
	private final int y;
	private final int[][] maze;
	private int[][] normalizedMaze;

	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		normalizedMaze = new int[this.x * 4 + 1][this.y * 2 + 1];

		generateMaze(0, 0);
		normalize();
	}

	private void normalize() {
		String[] lineas = new String[this.y * 2 + 1];
		String linea;
		int nroLinea = 0;

		for (int i = 0; i < y; i++) {
			linea = "";
			for (int j = 0; j < x; j++)
				linea += (maze[j][i] & 1) == 0 ? "****" : "*   ";
			linea += "*";
			lineas[nroLinea++] = linea;
			linea = "";
			for (int j = 0; j < x; j++)
				linea += (maze[j][i] & 8) == 0 ? "*   " : "    ";
			linea += "*";
			lineas[nroLinea++] = linea;
		}
		linea = "";
		for (int j = 0; j < x; j++)
			linea += "****";
		linea += "*";
		lineas[nroLinea++] = linea;

		int j = 0;
		for (String s : lineas) {
			int i = 0;
			for (char c : s.toCharArray())
				if (c == '*')
					normalizedMaze[i++][j] = 1;
				else
					normalizedMaze[i++][j] = 0;
			j++;
		}
	}

	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}

	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}

		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};

	public int[][] getMaze() {
		return normalizedMaze;
	}
}