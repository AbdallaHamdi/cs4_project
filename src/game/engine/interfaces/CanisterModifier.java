package game.engine.interfaces;

import game.engine.monsters.Monster;


public interface CanisterModifier {
abstract public void modifyCanisterEnergy(Monster monster, int canisterValue);
}
