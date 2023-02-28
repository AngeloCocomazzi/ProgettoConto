package it.betacom.conto;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

//generaInteressi() random -100% a 100% che indica il tasso di interesse
public class ContoInvestimento extends Conto
{
	private double tasso;
	Calendar ultimo_genera_interessi;
	
	ContoInvestimento(String titolare, Calendar apertura, double saldo) {
		super(titolare, apertura, saldo);
		this.tasso = 0;
	}
	
	void generaInteressi(Calendar data) throws IOException
	{
		int min=-100, max=100;
		Random random = new Random();
		tasso = random.nextInt(max - min + 1) + min;
		
		super.generaInteressi(data,tasso);
	}
	
	void prelevaContanti(double prelievo,Calendar data) throws IOException
	{
		super.generaInteressi(data,tasso);
		super.prelevaContanti(prelievo);
	}	
}
