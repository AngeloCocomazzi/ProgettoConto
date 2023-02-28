package it.betacom.conto;

import java.io.IOException;
import java.util.Calendar;

public class ContoCorrente extends Conto
{
	private double tasso;

	
	ContoCorrente(String titolare, Calendar apertura, double saldo) {
		super(titolare, apertura, saldo);
		this.tasso = 1.0;
		
	}
	
	
	void prelevaContanti(double prelievo,Calendar data) throws IOException
	{
		super.generaInteressi(data,tasso);
		super.prelevaContanti(prelievo);
	}
	
	void generaInteressi(Calendar data) throws IOException
	{
		super.generaInteressi(data,tasso);
	}
}
