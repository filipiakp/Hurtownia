package controller;

public interface IDBConnector {
	public Object[][] getZawartoscTabeli();
	public int[] getTypyKolumn();
	public void dodajRekord(Object model);
}
