package edu.illinois.isws.server;

import static spark.Spark.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import spark.*;
import com.google.gson.Gson;

public class HelloWorld {

	public static void main(String[] args) {
		final String NEW_LINE = System.getProperty("line.separator");

		get(new Route("/") {
			@Override
			public Object handle(Request request, Response response) {
				String result = null;
				// set the response type
				response.type("text/html");
				try {
					result = FileUtils
							.readFileToString(new File("ws_bmp.html"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
			}
		});

		get(new Route("/pareto") {
			@Override
			public Object handle(Request request, Response response) {
				String result = "";
				// set the response type
				response.type("text/html");
				String directoryName = null;
				if(request.params("type").equals("1")) {
					directoryName = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\BigDitchWs";
				}
				else {
					directoryName = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\BigLongCreekWs";
				}
				try {
					String[] fileNames = {"nitrate", "phostphorus"};
					for (String name : fileNames)
						result = result + FileUtils.readFileToString(new File(
								directoryName + name));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				return result;
			}
		});

		get(new Route("/singlerun") {
			@Override
			public Object handle(Request request, Response response) {
				InputParameters input = new InputParameters();
				String output = null;
				String workingDirectory = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS";
				parseClientInputs(request);
				runExecutable(workingDirectory);
				return getOutputString();
			}

			private Object getOutputString() {
				try {
					return FileUtils
							.readFileToString(new File(
									"OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\BigDitch\\scenario_simulation.txt"));
				} catch (IOException e) {
					e.printStackTrace();
					return "Error....";
				}
			}

			private void parseClientInputs(Request request) {
				String bmps = request.queryParams("listHruBMP");
				String cost = request.queryParams("cost");

				bmps.replace(",", NEW_LINE);
				cost.replace(",", NEW_LINE);

				for (int i = 0; i < 371; i++)
					bmps = bmps + "1" + NEW_LINE;

				try {
					FileUtils
							.writeStringToFile(
									new File(
											"OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\Client_Input.txt"),
									request.queryParams("is_single_simulation")
											+ NEW_LINE
											+ request.queryParams("wshIndex")
											+ NEW_LINE + bmps);
					FileUtils
							.writeStringToFile(
									new File(
											"OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\BMP_DB_09_single_simulation.txt"),
									cost);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			private void runExecutable(String workingDirectory) {
				Runtime runtime = Runtime.getRuntime();
				Process process = null;
				try {
					String executable = "OCM_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS\\OCM_AMGA2_IndBMP_LHS.exe";
					process = runtime.exec(executable, null, new File(
							workingDirectory));
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}

				try {
					process.waitFor();
				} catch (InterruptedException e) {
					// Handle exception that could occur when waiting
					// for a spawned process to terminate
				}
			}
		});
	}
}