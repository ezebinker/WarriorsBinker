import java.util.ArrayList;

import ia.battle.core.FieldCell;
import ia.battle.core.actions.Move;

public class Movimiento extends Move {
	private ArrayList<FieldCell> path;
	public Movimiento(ArrayList<FieldCell> path) {
		this.path = path;
	}
	@Override
	public ArrayList<FieldCell> move() {
		return path;
	}
}
