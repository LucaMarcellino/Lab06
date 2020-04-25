package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO mdao=new MeteoDAO();
	private int livello;
	Map<Date,String> spostamenti= new LinkedHashMap<Date,String>();
	List<Citta> parziale= new ArrayList<Citta>();
	List<Citta> best= new ArrayList<Citta>();
	
	
	
	List<Citta> leCitta= new ArrayList<Citta>();
	public Model() {
		livello=0;
		this.leCitta=mdao.getCitta();
	}

	
	
	
	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		Citta c1=new Citta("Torino");
		Citta c2=new Citta("Milano");
		Citta c3=new Citta("Genova");
		
	
		double m1=mdao.getAvgRilevamentiLocalitaMese(mese, c1);
		double m2=mdao.getAvgRilevamentiLocalitaMese(mese, c2);
		double m3=mdao.getAvgRilevamentiLocalitaMese(mese, c3);
		
		
		
	
	
		String s= "Torino "+m1+"\n"+"Genova "+m3+"\n"+"Milano "+m2+"\n";
		return s;
	}
	// of course you can change the String output with what you think works best
	public	List<Citta> trovaSequenza(int mese) {
		
		for(Citta c: leCitta) {
			c.setRilevamenti(mdao.getAllRilevamentiLocalitaMese(mese,c));
		}
		
		cerca(parziale,livello);
	
		
		
		
		
		return best;
	}




	private void cerca(List<Citta> parziale, int livello) {
		if(livello == NUMERO_GIORNI_TOTALI) {
			double costo= calcolaCosto(parziale);
			
			if(best==null || costo<calcolaCosto(best)) {
				best = new ArrayList<>(parziale);
			}
			
			
		}
		for(Citta prova: leCitta) {
			if(aggiuntaValida(prova,parziale)) {
				parziale.add(prova);
				cerca(parziale,livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}




	private double calcolaCosto(List<Citta> parziale2) {
		double costo=0.0;
		
		for (int giorno=1; giorno<=NUMERO_GIORNI_TOTALI; giorno++) {
			
			Citta c = parziale.get(giorno-1);
			
			double umid = c.getRilevamenti().get(giorno-1).getUmidita();
			costo+=umid;
		}
		for (int giorno=2; giorno<=NUMERO_GIORNI_TOTALI; giorno++) {
		
			if(!parziale.get(giorno-1).equals(parziale.get(giorno-2))) {
				costo +=COST;
			}
		}
		return costo;
	}




	private boolean aggiuntaValida(Citta prova, List<Citta> parziale) {
		int conta=0;
		
		for(Citta precendete:parziale) {
			if(precendete.equals(prova)) 
				conta++;
		}
		if(conta >= NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		if(parziale.size()==0) 
				return true;
		if(parziale.size()==1 || parziale.size()==2) 
			return parziale.get(parziale.size()-1).equals(prova);
		if(parziale.get(parziale.size()-1).equals(prova))
			return true;
		
		if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
				&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
					return true;
			
		
		return false;
	}
	

	
	
	
	
	
	

}
