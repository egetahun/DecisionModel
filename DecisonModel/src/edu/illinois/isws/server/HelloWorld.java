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
				String inputFileLocation = "";
				String outputFileLocation = "";
				String out = null;
				input = gson.fromJson(request.queryParams("input"),
						input.getClass());

				try {
					FileUtils.writeStringToFile(new File(inputFileLocation),
							input.txtInOut);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Runtime runtime = Runtime.getRuntime();
				Process process = null;
				try {
					String executable = "";
					process = runtime.exec(executable);
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
				try {
					FileUtils.readFileToString(new File(outputFileLocation),
							out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				output.setOutput(out);
				return gson.toJson(output);
			}
		});
	}
}