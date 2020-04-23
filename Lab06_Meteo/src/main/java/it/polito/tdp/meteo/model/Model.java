package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO mdao=new MeteoDAO();
	public Model() {

	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
	
	
		double m1=mdao.getAvgRilevamentiLocalitaMese(mese, "Torino");
		double m2=mdao.getAvgRilevamentiLocalitaMese(mese, "Milano");
		double m3=mdao.getAvgRilevamentiLocalitaMese(mese, "Genova");
		
		
		
	
	
		String s= "Torino "+m1+"\n"+"Genava "+m3+"\n"+"Milano "+m2+"\n";
		return s;
	}
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	

}
