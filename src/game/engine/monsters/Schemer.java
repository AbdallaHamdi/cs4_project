package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Constants;
import game.engine.Role;

public class Schemer extends Monster {
	
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}
	private int stealEnergyFrom(Monster target){
		int A=Constants.SCHEMER_STEAL;
		int B= target.getEnergy();
		return Math.min(A, B);
	}
	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		ArrayList<Monster>mon=Board.getStationedMonsters();
		int sum =stealEnergyFrom(opponentMonster);
		for (int index = 0; index < mon.size(); index++) {
			sum+=stealEnergyFrom(mon.get(index));
		}
		this.setEnergy(sum);
	}	
	@Override
	public void alterEnergy(int energy) {
		if (this.isShielded() && energy <0){
			setShielded(false);
		}
		else{
			this.setEnergy(this.getEnergy()+energy+10);
		}
	}
}
