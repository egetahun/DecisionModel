package edu.illinois.isws.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Output {
	double sedLd, nitLd, phoLd, ppr, totCost;
	public void readOutput(String fileLocation) throws FileNotFoundException {
		File f = new File(fileLocation);
		Scanner s  = new Scanner(f);
		sedLd = s.nextDouble();
		nitLd = s.nextDouble();
		phoLd = s.nextDouble();
		ppr = s.nextInt();
		totCost = s.nextDouble();
	}
	
	public String toString(String fileLocation) throws FileNotFoundException {
		readOutput(fileLocation);
		return String.valueOf(sedLd) + "," 
				+ String.valueOf(nitLd) + ","
				+ String.valueOf(phoLd) + ","
				+ String.valueOf(ppr) + ","
				+ String.valueOf(totCost) + "\n";
	}
}
