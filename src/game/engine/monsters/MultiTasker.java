package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.normalSpeedTurns = 0;
	}

	public int getNormalSpeedTurns() {
		return normalSpeedTurns;
	}

	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		normalSpeedTurns=2;
	}
	@Override
	public  void move(int distance){
		if (normalSpeedTurns>0) {
		this.setPosition(this.getPosition()+distance);
		normalSpeedTurns-=1;	
		}
		else{
		this.setPosition(this.getPosition()+distance/2);
		}
	}
	@Override
	public void alterEnergy(int energy) {
		if (this.isShielded() && energy <0){
			setShielded(false);
		}
		else{
			this.setEnergy(this.getEnergy()+energy+200);
		}
	}

}