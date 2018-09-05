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
	
		ConfigurationManager.getInstance().getMaxRangeForWarrior();

		//Hacer la división de los getMaxPoints para cada atributo. O hacerlo random. 
			
		return new Spiderman("Spiderman",20,20,20,20,20);
		
		
		
	}

	public void remember(int damage, FieldCell source) {
		System.out.println(source);
	}
	
	
}
