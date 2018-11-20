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
		
		//bf.calculateDistance para calcular distancia a una celda
		
		//getEnemyNumber te dice cual es el numero de enemigo actual
		//A este evento se lo llama 3 veces consecutiva a cada uno.
		//El tick es el numero del turno, y el actionnumber es 0, 1 y 2. 
		
		//TODO: ver que onda el getEnemyNumber. Yo siempre voy a querer que el guerrero se mueva en todos los turnos. 
		//Son 3 las acciones que se pueden hacer por turno. 1 x cada una, actionNumber indica en cual estas.
		//Se puede realizar Move, Attack, BuildWall, Suicide o Skip. 
		
		//Sobreescribir el método useSpecialItems si no los quiero
		//Revisar si algun atributo cambió
		
		WarriorData hunterData=bf.getHunterData();
		ArrayList<FieldCell> si=bf.getSpecialItems();
		
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
		Ataques.add(new AtaqueRecibido(damage, source));
		System.out.println("Ouch!");
		
	}

	@Override
	public void enemyKilled() {
		System.out.println("Oh Yeah!");
		//Aca poner una variable de, si mat� al otro puedo cambiar la estrategia del playturn. 
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		
	}
	
	private int getMaxRange() {
		int max=getSpeed()/5;
		return max;
	}
	
	//getSpecialItems te muestra las cajitas que esten cerca tuyo. 90% son cajitas buenas, el resto cajitas malas
	//Cuando comienza la batalla se puede invertir la distribución de las cajas. 
	//TODO: Sobreescribir useSpecialItem para que no agarre siempre las cajas

}
