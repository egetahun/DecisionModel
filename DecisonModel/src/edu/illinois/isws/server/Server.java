package edu.illinois.isws.server;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;

import spark.*;

public class Server {
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

		post(new Route("/singlerun") {
			@Override
			public Object handle(Request request, Response response) {
				InputParameters input = new InputParameters();
				String output = null;
				parseClientInputs(request);
				boolean result = runExecutable();
				if (result)
					return getOutputString();
				else
					return "failed";
			}

			private Object getOutputString() {
				try {
					return FileUtils.readFileToString(new File(
							"scenario_simulation.txt"));
				} catch (IOException e) {
					e.printStackTrace();
					return "Error...";
				}
			}

			private void parseClientInputs(Request request) {
				String bmps = request.queryParams("listHruBMP");
				String cost = request.queryParams("cost");
                   
				bmps = bmps.replace(",", NEW_LINE);
				cost = cost.replace(",", NEW_LINE);
//                bmps = "";
//				for (int i = 0; i < 289; i++)
//					bmps = bmps + "0" + NEW_LINE;

				try {
					FileUtils.writeStringToFile(
							new File("Client_Input.txt"),
							request.queryParams("is_single_simulation")
									+ NEW_LINE
									+ request.queryParams("wshIndex")
									+ NEW_LINE + bmps);
					FileUtils.writeStringToFile(new File(
							"BMP_DB_09_single_simulation.txt"), cost);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			private boolean runExecutable() {
				Runtime runtime = Runtime.getRuntime();
				Process process = null;
				String line;
				InputStream stdout = null;
				BufferedReader reader = null;
				try {
					ProcessBuilder builder = new ProcessBuilder(
							"OCM_AMGA2_IndBMP_LHS.exe");
					builder.redirectErrorStream(true);
					process = builder.start();
					stdout = process.getInputStream();
					reader = new BufferedReader(new InputStreamReader(stdout));
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}

				try {
					System.out.println("Execution Started");
					String result = null;
					int count = 1;
					process.waitFor();
					while ((line = reader.readLine()) != null) {
						result = line; 
						System.out.println("Stdout: " + line);
					}
					System.out.println("Execution completed");
					if (!result.contains("successfully"))
						return false;
				} catch (Exception e) {
					e.printStackTrace();
					// Handle exception that could occur when waiting
					// for a spawned process to terminate
				}
				return true;
			}
		});
	}
}