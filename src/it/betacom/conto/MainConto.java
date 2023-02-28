package it.betacom.conto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import it.betacom.util.FileWriters;

public class MainConto {

	public static void main(String[] args) throws IOException 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar data_apertura =  new GregorianCalendar (2021,Calendar.JANUARY,1);
		Calendar data_prelievo =  new GregorianCalendar (2022,Calendar.JULY,1);
		
		Calendar data21 =  new GregorianCalendar (2021,Calendar.DECEMBER,31);
		Calendar data22 =  new GregorianCalendar (2022,Calendar.DECEMBER,31);
		Calendar data_oggi =  new GregorianCalendar();//data odierna (2023,Calendar.FEBRUARY,20)
		
		ContoCorrente conto_corrente = new ContoCorrente("Angelo",data_apertura,2000);
		ContoDeposito conto_deposito = new ContoDeposito("Mario",data_apertura,1000);
		ContoInvestimento conto_investimento = new ContoInvestimento("Ciro",data_apertura,500);
		

		conto_corrente.generaInteressi(data21);
		FileWriters.generatePdf(conto_corrente.getTitolare()/*+sdf.format(data21.getTime())*/, "", "Gestione Conto di "+conto_corrente.getTitolare()+" nel "+sdf.format(data21.getTime()));
		conto_corrente.prelevaContanti(900,data_prelievo);
		
		conto_corrente.generaInteressi(data22);
		FileWriters.generatePdf(conto_corrente.getTitolare()/*+sdf.format(data22.getTime())*/, "", "Gestione Conto di "+conto_corrente.getTitolare()+" nel "+sdf.format(data22.getTime()));
		conto_corrente.generaInteressi(data_oggi);
		
		
		System.out.println("\nSaldo finale: "+ String.format("%.2f", conto_corrente.getSaldo()));
		
		System.out.println("**************************************************");
		conto_deposito.generaInteressi(data21);
		FileWriters.generatePdf(conto_deposito.getTitolare()+sdf.format(data21.getTime()), "", "Gestione Conto di "+conto_deposito.getTitolare()+" nel "+sdf.format(data21.getTime()));
		conto_deposito.prelevaContanti(900,data_prelievo);
		
		conto_deposito.generaInteressi(data22);
		FileWriters.generatePdf(conto_deposito.getTitolare()+sdf.format(data22.getTime()), "", "Gestione Conto di "+conto_deposito.getTitolare()+" nel "+sdf.format(data22.getTime()));
		
		conto_deposito.generaInteressi(data_oggi);
		
		System.out.println("\nSaldo finale: "+ String.format("%.2f", conto_deposito.getSaldo()));
		
		System.out.println("**************************************************");
		conto_investimento.generaInteressi(data21);
		FileWriters.generatePdf(conto_investimento.getTitolare()+sdf.format(data21.getTime()), "", "Gestione Conto di "+conto_investimento.getTitolare()+" nel "+sdf.format(data21.getTime()));
		conto_investimento.prelevaContanti(900,data_prelievo);
		
		conto_investimento.generaInteressi(data22);
		FileWriters.generatePdf(conto_investimento.getTitolare()+sdf.format(data22.getTime()), "", "Gestione Conto di "+conto_investimento.getTitolare()+" nel "+sdf.format(data22.getTime()));
		
		conto_investimento.generaInteressi(data_oggi);
		
		System.out.println("\nSaldo finale: "+ String.format("%.2f", conto_investimento.getSaldo()));

	}
}
