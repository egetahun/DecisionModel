package edu.illinois.isws.server;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import spark.*;
import com.google.gson.Gson;

public class HelloWorld {

	public static void main(String[] args) {

		get(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello World!";
			}
		});

		get(new Route("/run") {
			@Override
			public Object handle(Request request, Response response) {
				Gson gson = new Gson();
				InputParameters input = new InputParameters();
				Output output = new Output();
				String out = null;
				String workingDirectory = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS";
				//input = gson.fromJson(request.queryParams("input"),
					//	input.getClass());

				try {
					FileUtils.writeStringToFile(new File("OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\Client_Input.txt"), request.queryParams("is_single_simulation") + System.getProperty("line.separator") + request.queryParams("is_single_simulation"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				Runtime runtime = Runtime.getRuntime();
				Process process = null;
				try {
					String executable = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS.exe";
					process = runtime.exec(executable, null, new File(workingDirectory));
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
				
				// wait for the process to complete
				/*try {
				    process.waitFor();
				} catch (InterruptedException e) {
				    // Handle exception that could occur when waiting
				    // for a spawned process to terminate
				}*/

				try {
					// reads from allSolutions.txt file
					output.readOutput("OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\BigDitch_210\\allSolutions.txt");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return gson.toJson(output);
			}
		});
	}
}