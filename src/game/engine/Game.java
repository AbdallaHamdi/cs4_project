package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import game.engine.cells.Cell;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InvalidMoveException;
import game.engine.exceptions.OutOfEnergyException;
import game.engine.monsters.*;

public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	
	public Game(Role playerRole) throws IOException {
		this.board = new Board(DataLoader.readCards());
		
		this.allMonsters = DataLoader.readMonsters();
		
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		board.setStationedMonsters(allMonsters);
 	ArrayList<Cell> specialCells = DataLoader.readCells();
    board.initializeBoard(specialCells);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	private Monster getCurrentOpponent(){
		return opponent;
	}
	private int rollDice(){
		return (int)(Math.random()*6)+1;
	}	
	public void usePowerup() throws OutOfEnergyException{
		if(current.getEnergy()<Constants.POWERUP_COST){
			try{
				current.executePowerupEffect(opponent);
				}
			catch(OutOfEnergyException e){
				System.err.println(e.getMessage());
			}
			current.alterEnergy(Constants.POWERUP_COST);
	}
	}
	public void playTurn() throws InvalidMoveException{
		if(current.isFrozen()){
			 current.setFrozen(false);
		}
		else{
			if(current.getConfusionTurns()>0){ // this condition is added by me 
				current.decrementConfusion();
				switchTurn(); // added by me as logic 
			}
			else{
			int dice = rollDice();
			current.move(dice);
			switchTurn(); // added by me as logic 
			}
		}
	}
	private void switchTurn(){
		if (current==player){
			current=opponent;
		}
		else{
			current=player;
		}
	}
	private boolean checkWinCondition(Monster monster){
		if (monster.getPosition()==99&&monster.getEnergy()>= 1000){
			return true;
		}
		return false;
	}
	public Monster getWinner(){
		if(checkWinCondition(current)){
			return current;			
		}
		return null;
	}
}