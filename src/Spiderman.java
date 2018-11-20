import java.util.ArrayList;

import ia.battle.core.BattleField;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.actions.*;
import ia.exceptions.RuleException;
import utils.AStar;
import utils.AtaqueRecibido;

public class Spiderman extends Warrior{

	public boolean hasMoved=false;
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
		
		if (enemyData.getInRange()) {
			return new Attack(enemyData.getFieldCell());
			
		}else if(enemyData.getHealth()<10) {
			return new Skip();
		}
		
		if (actionNumber == 0) { //Si es la primera acciÛn del turno
			ArrayList<FieldCell> specialItems = new ArrayList<>();
			
			//Trato de encontrar algun objeto especial
			
			BattleField.getInstance().getSpecialItems().forEach(fieldCell -> specialItems.add(fieldCell));
			for (FieldCell specialItem : specialItems) {
				int distance = obtenerDistancia(this.getPosition(), specialItem);
				if ((distanciaCercana > distance && distance <= getMaxRange())) {
					distanciaCercana = distance;
					target = specialItem;
				}
			}
		}
		
		if (target != null && enemyData.getFieldCell() != null) {
			si = getPathFrom(getPosition(), target);
			if (!si.isEmpty()){ 
				hasMoved = true;
				return new Movimiento(si);
			}
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
	
	private ArrayList<FieldCell> getPathFrom(FieldCell source, FieldCell target) {
		ArrayList<FieldCell> path = new ArrayList<FieldCell>();
		if (!(source == null || target == null)) {
			AStar a = new AStar(ConfigurationManager.getInstance().getMapWidth(),
					ConfigurationManager.getInstance().getMapHeight());
			
			a.findPath(source, target).forEach(node -> {
				path.add(BattleField.getInstance().getFieldCell(node.getX(), node.getY()));
			});
		}
		return path;
	}
	
	//getSpecialItems te muestra las cajitas que esten cerca tuyo. 90% son cajitas buenas, el resto cajitas malas
	//Cuando comienza la batalla se puede invertir la distribuci√≥n de las cajas. 
	//TODO: Sobreescribir useSpecialItem para que no agarre siempre las cajas

}
