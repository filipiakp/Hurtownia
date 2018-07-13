package model;


import view.ModelObserver;

/**
 * interfejs służący do implementacji wzorca Observer
 *
 */
public interface ModelInterface {
	
	public void addObserver(ModelObserver observer);
	
	public void removeObserver(ModelObserver observer);
	
	public void notifyAllObservers();

}
