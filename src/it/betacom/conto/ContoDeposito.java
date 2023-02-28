package it.betacom.conto;

import java.io.IOException;
import java.util.Calendar;

public class ContoDeposito extends Conto
{
	private double tasso = 3.0;
	Calendar ultimo_genera_interessi;
	
	ContoDeposito(String titolare, Calendar apertura, double saldo) {
		super(titolare, apertura, saldo);
		this.tasso = 3.0;
		ultimo_genera_interessi = getData_apertura();
	}
	

	void prelevaContanti(double prelievo,Calendar data) throws IOException
	{
		if(prelievo <1000)
		{
			super.generaInteressi(data,tasso);
			super.prelevaContanti(prelievo);
		}
		else
		{
			System.out.println("Limite prelievo inferiore a 1000€ per il ContoDeposito!");
		}
	}
	
	void generaInteressi(Calendar data) throws IOException
	{
		super.generaInteressi(data,tasso);
	}
}
