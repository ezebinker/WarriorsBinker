import ia.battle.core.BattleField;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.exceptions.RuleException;

public class Fury extends WarriorManager {

	public String getName() {
		return "Nick Fury";
	}

	@Override
	public Warrior getNextWarrior() throws RuleException {
	
		int max = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		int health = max / 5; 
		int speed = max / 5;
		int strength = max / 5;
		int range = max / 5;
		int defense = max / 5;
		
		int spawneados=this.getCount()-1;
		
		if (BattleField.getInstance().getTick() != 0 ){ 
			if (spawneados < BattleField.getInstance().getEnemyData().getWarriorNumber()){
				health   = max * 65 / 100;
				defense  = max * 05 / 100; 
				strength = max * 05 / 100;
				speed    = max * 15 / 100;
				range    = max * 10 / 100;
				
			}
			else if (spawneados> BattleField.getInstance().getEnemyData().getWarriorNumber()){
				health   = max * 25 / 100;
				defense  = max * 10 / 100; 
				strength = max * 25 / 100;
				speed    = max * 15 / 100;
				range    = max * 25 / 100;	
			}
		}
			
		return new Spiderman("Spiderman",health , defense, strength, speed, range);
	}

	public void remember(int damage, FieldCell source) {
		System.out.println(source);
	}
	
	
}
