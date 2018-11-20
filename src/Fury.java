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
		
		if (BattleField.getInstance().getTick() != 0 ){ 
			if (this.getCount()-1 < BattleField.getInstance().getEnemyData().getWarriorNumber()){
				health   = max * 70 / 100;
				defense  = max * 05 / 100; 
				strength = max * 05 / 100;
				speed    = max * 15 / 100;
				range    = max * 05 / 100;
				
			}
			
			if (this.getCount()-1 > BattleField.getInstance().getEnemyData().getWarriorNumber()){
				health   = max * 15 / 100;
				defense  = max * 10 / 100; 
				strength = max * 35 / 100;
				speed    = max * 05 / 100;
				range    = max * 35 / 100;	
			}
		}
			
		return new Spiderman("Spiderman",health , defense, strength, speed, range);
	}

	public void remember(int damage, FieldCell source) {
		System.out.println(source);
	}
	
	
}
