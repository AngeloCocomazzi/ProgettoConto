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
import java.util.Scanner;

public class readAllFile {

	public static void main(String[] args) throws IOException {
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(""))) 
		{
			String path = "estrattoContoFinale.txt";
			FileWriter file_w = new FileWriter(path,true);//append mode on
			BufferedWriter buffer = new BufferedWriter(file_w);
			
		    for (Path file: stream) 
		    {
		    	if(file.toString().endsWith(".txt"))
		    	{
		    		 System.out.println(file.getFileName());
		    		 File myObj = new File(file.getFileName().toString());
		    		 Scanner myReader = new Scanner(myObj);
		    		 while (myReader.hasNextLine()) 
		    		 {
		    	        String data = myReader.nextLine();
		    	        buffer.write(data);
		    	        buffer.newLine();
		    	        System.out.println(data);
		    	      }
		    		 myReader.close();
		    		 myObj.delete();
		    	}
		       
		    }
			buffer.close();
		} catch (IOException | DirectoryIteratorException ex) {
		    System.err.println(ex);
		}
	}

}
