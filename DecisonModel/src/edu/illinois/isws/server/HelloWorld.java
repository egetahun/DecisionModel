package edu.illinois.isws.server;

import static spark.Spark.*;
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
				input = gson.fromJson(request.queryParams("input"), input.getClass());
///				Runtime runtime = Runtime.getRuntime();
//				Process process = null;
//				try {
//					String executable = "c:\\windows\notpad.exe";
//					process = runtime.exec(executable);
//				} catch (Exception e) {
//					System.out.println("Error: " + e.getMessage());
//					e.printStackTrace();
//				}
				//return request.queryParams(input.txtInOut);
				return input.txtInOut;
			}
		});
	}
}