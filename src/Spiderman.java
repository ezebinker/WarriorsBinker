import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.*;
import ia.exceptions.RuleException;

public class Spiderman extends Warrior{

	public Spiderman(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);

	}
	
	public boolean hasMoved;

	@Override
	public Action playTurn(long tick, int actionNumber) {
		WarriorData enemyData = BattleField.getInstance().getEnemyData();

		//TODO: ver que onda el getEnemyNumber. Yo siempre voy a querer que el guerrero se mueva en todos los turnos. 
		//Son 3 las acciones que se pueden hacer por turno. 1 x cada una, actionNumber indica en cual estas.
		//Se puede realizar Move, Attack, BuildWall, Suicide o Skip. 
		
		if (actionNumber==0) {
			hasMoved=false;
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
		((Fury) getWarriorManager()).remember(damage, source);

		System.out.println("Ouch!");
		
	}

	@Override
	public void enemyKilled() {
		System.out.println("Oh Yeah!");

	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		
	}
	
	//getSpecialItems te muestra las cajitas que esten cerca tuyo. 90% son cajitas buenas, el resto cajitas malas
	//Cuando comienza la batalla se puede invertir la distribución de las cajas. 
	//TODO: Sobreescribir useSpecialItem para que no agarre siempre las cajas

}
