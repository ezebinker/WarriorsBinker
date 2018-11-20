import java.util.ArrayList;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.*;
import ia.exceptions.RuleException;
import utils.AtaqueRecibido;

public class Spiderman extends Warrior{

	public boolean hasMoved=false;
	private static final int RANGO_CAZADOR = 5;
	private ArrayList<AtaqueRecibido> Ataques;
	
	
	public Spiderman(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		Ataques=new ArrayList<AtaqueRecibido>();
	}
	

	@Override
	public Action playTurn(long tick, int actionNumber) {
		BattleField bf=BattleField.getInstance();
		WarriorData enemyData = bf.getEnemyData();
		
		WarriorData hunterData=bf.getHunterData();
		ArrayList<FieldCell> si=bf.getSpecialItems();
		
		FieldCell target = null;
		int distanciaCercana = Integer.MAX_VALUE;
		
		if (actionNumber == 0) { //Si es la primera acción del turno
			ArrayList<FieldCell> specialItems = new ArrayList<>();
			
			BattleField.getInstance().getSpecialItems().forEach(fieldCell -> specialItems.add(fieldCell));
			for (FieldCell specialItem : specialItems) {
				int distance = obtenerDistancia(this.getPosition(), specialItem);
				if ((distanciaCercana > distance && distance <= getMaxRange())) {
					distanciaCercana = distance;
					target = specialItem;
				}
			}
		}
		
		if (enemyData.getInRange()) {
			return new Attack(enemyData.getFieldCell());
			
		}else if(enemyData.getHealth()<10) {
			return new Skip();
		}

		return new SaltoSpider(getPosition(), 5, 4);
		
	}

	@Override
	public void wasAttacked(int damage, FieldCell source) {
		Ataques.add(new AtaqueRecibido(damage, source));
		System.out.println("Ouch!");
		
	}

	@Override
	public void enemyKilled() {
		System.out.println("Oh Yeah!");
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		
	}
	
	private int obtenerDistancia(FieldCell source, FieldCell target) {
		int distancia = 0;

		distancia = Math.abs(target.getX() - source.getX());
		distancia += Math.abs(target.getY() - source.getY());

		return distancia;
	}
	
	private int getMaxRange() {
		int max=getSpeed()/5;
		return max;
	}
	
	//getSpecialItems te muestra las cajitas que esten cerca tuyo. 90% son cajitas buenas, el resto cajitas malas
	//Cuando comienza la batalla se puede invertir la distribuciÃ³n de las cajas. 
	//TODO: Sobreescribir useSpecialItem para que no agarre siempre las cajas

}
