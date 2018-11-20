package utils;
import ia.battle.core.FieldCell;

public class AtaqueRecibido {
	private FieldCell source;
	private int damage;
	
	public AtaqueRecibido(int damage, FieldCell source) {
		this.setDamage(damage);
		this.setSource(source);
	}
	public int getDamage() {
		return damage;
	}
	private void setDamage(int damage) {
		this.damage = damage;
	}
	public FieldCell getSource() {
		return source;
	}
	public void setSource(FieldCell source) {
		this.source = source;
	}

}
