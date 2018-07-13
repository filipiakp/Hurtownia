package model;

import java.util.ArrayList;
import view.ModelObserver;

/**
 * Klasa przechowująca informacje o następnym wolnym położeniu okna.
 * Implementuje interfejs „ModelInterface”.Posiada stałą FREE_POSITION_JUMP zawierającą o długości przeskoku okien w pikselach, w poziomie.
 *
 */
public class MainModel implements ModelInterface{
	private final int FREE_POSITION_JUMP = 60;
	private ArrayList<ModelObserver> observers;
	private int freePosition;
	
	
	public MainModel(){
		freePosition = 0;
	}
	
	public void changeFreePosition(){
		if(freePosition==(4*FREE_POSITION_JUMP)){
			freePosition = 0;
		}else{
			freePosition += FREE_POSITION_JUMP;
		}
		
	}
	
	public int getFreePosition() {
		return freePosition;
	}
	
	@Override
	public void addObserver(ModelObserver observer){
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(ModelObserver observer){
		observers.remove(observer);
	}
	
	@Override
	public void notifyAllObservers(){
		for(ModelObserver mo: observers){
			mo.update();
		}
	}

	
}
