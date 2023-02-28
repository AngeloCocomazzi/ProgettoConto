package it.betacom.conto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import it.betacom.util.FileWriters;

public class Conto 
{
	private String titolare;
	private Calendar data_apertura;
	private double saldo;
	private Calendar ultimo_genera_interessi;
	
	Conto(String titolare,Calendar apertura,double saldo)
	{
		this.titolare = titolare;
		this.data_apertura = apertura;
		this.saldo = saldo;
		this.ultimo_genera_interessi = apertura;
	}
	
	String getTitolare() {
		return titolare;
	}

	void setTitolare(String titolare) {
		this.titolare = titolare;
	}

	Calendar getData_apertura() {
		return data_apertura;
	}

	void setData_apertura(Calendar data_apertura) {
		this.data_apertura = data_apertura;
	}

	double getSaldo() {
		return saldo;
	}

	void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	
	void prelevaContanti(double prelievo)
	{
		
		if(prelievo <= getSaldo())
		{
			double nuovo_saldo = getSaldo()-prelievo;
			setSaldo(nuovo_saldo);
			System.out.println("Prelevati : "+prelievo+"€ Nuovo saldo : " + String.format("%.2f",getSaldo()));
		}
		else
		{
			System.out.println("Non hai abbastanza soldi per prelevare!");
		}
	}
	
	void depositaContanti(double deposito) 
	{
		setSaldo(getSaldo()+deposito);
		System.out.println("Depositati : "+deposito+"€ Nuovo saldo : " + getSaldo());
	}
	
	int giorni_passati(Calendar c1,Calendar c2) 
	{
		//Calendar c1 = new GregorianCalendar ();//data di oggi
		
		long millisecondi = c1.getTimeInMillis() - c2.getTimeInMillis();
		int giorni = (int) (millisecondi / 86400000);
		
		return giorni;
	}
	
	void generaInteressi(Calendar data,double tasso) throws IOException
	{
		
		if(giorni_passati(getData_apertura(),ultimo_genera_interessi)==0)//entra solo la prima volta
		{
			ultimo_genera_interessi = data;
			
			int giorni_interessi = giorni_passati(data,getData_apertura());
			System.out.println("Giorni passati dall'ultimo interesse: "+giorni_interessi);

			if(giorni_interessi>0)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				double interessi = getSaldo()*(((tasso/100)/365)*giorni_interessi);
				
				setSaldo(getSaldo()+interessi);
				
				FileWriters.scriviFile(getTitolare()/*+sdf.format(data.getTime())*/+".txt","Titolare: " + getTitolare()+" | Interessi: " + String.format("%.2f",interessi) + 
						" | Tasso: "+ tasso +" | Saldo totale:" +String.format("%.2f", getSaldo()) + " | Data saldo: "+sdf.format(data.getTime()));
				
				System.out.println("Interessi dell'"+tasso +"% sono : " +  String.format("%.2f",interessi));
			}
			else
			{
				System.out.println("E' troppo presto per generare interessi");
			}
		}
		else
		{
			Calendar data_oggi = new GregorianCalendar ();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			if(giorni_passati(data,getData_apertura())>0 && giorni_passati(data_oggi,data)>=0 && giorni_passati(data,ultimo_genera_interessi)>0)
			{
				if(giorni_passati(data_oggi,data)==0)//se la data passata è uguale a quella di oggi genero pdf con estratto conto finale
				{
					System.out.println("\nEstratto Conto Finale Generato\n");
					estrattoContoFinale();
					//FileWriters.generatePdf(/*"estrattoContoFinale"+*/getTitolare(),"", "Estratto Conto Finale di "+getTitolare()+" nel "+sdf.format(data.getTime()));
				}
				else
				{
					int giorni_interessi = giorni_passati(data,ultimo_genera_interessi);
					System.out.println("Giorni passati dall'ultimo interesse: "+giorni_interessi);
					
					ultimo_genera_interessi = data;
					
					if(giorni_interessi>0)
					{
						//SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						double interessi = getSaldo()*(((tasso/100)/365)*giorni_interessi);
						
						setSaldo(getSaldo()+interessi);
						
						FileWriters.scriviFile(getTitolare()/*+sdf.format(data.getTime())*/+".txt","Titolare: " + getTitolare()+" | Interessi: " + String.format("%.2f",interessi) + 
								" | Tasso: "+ tasso +" | Saldo totale: " +String.format("%.2f", getSaldo()) + " | Data saldo: "+sdf.format(data.getTime()));
						
						System.out.println("Interessi dell'"+tasso +"% sono : " +  String.format("%.2f",interessi));
					}
					else
					{
						System.out.println("E' troppo presto per generare interessi");
					}
				}
			}
			else
			{
				System.out.println("Data non valida");
			}
		}
	}
	
	private void estrattoContoFinale()
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(""))) 
		{
			String path = /*"estrattoContoFinale"+*/getTitolare()+".txt";
			FileWriter file_w = new FileWriter(path,true);//append mode on
			BufferedWriter buffer = new BufferedWriter(file_w);
			
		    for (Path file: stream)//prendo tutti i file che finiscono per txt nella cartella specificata e 
		    						// li unisco in un unico file
		    {
		    	if(file.toString().endsWith(".txt"))//.endsWith(".txt")
		    	{
		    		 //System.out.println(file.getFileName());
		    		 File myObj = new File(file.getFileName().toString());
		    		 Scanner myReader = new Scanner(myObj);
		    		 while (myReader.hasNextLine()) 
		    		 {
		    	        String data = myReader.nextLine();
		    	        buffer.write(data);
		    	        buffer.newLine();
		    	        //System.out.println(data);
		    	      }
		    		 myReader.close();
		    		 myObj.delete();
		    	}
		       
		    }
			buffer.close();
		} 
		catch (IOException | DirectoryIteratorException ex) 
		{
		    System.err.println(ex);
		}
	}
}

	
