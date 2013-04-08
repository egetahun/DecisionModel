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
}
