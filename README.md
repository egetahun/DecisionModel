DecisionModel
=============

Decision support models to evaluate water quality impacts of best management practices

We followed client server archtiecture for developing this web application. We also created a github repository for this project. 
The link to the repository is: https://github.com/egetahun/DecisionModel. The credentials for the repository are as follows:
username: egetahun
password:  isws123
Server Component:
1) We used SPARK framework for Server development. The download link is http://www.sparkjava.com/download.html
2) The rationale behind choosing SPARK framework is to decrease development round time. The other viable alternatives include Servlets, JSPs etc.
3) Server can be started by running Server.java file. The default server url is: http://localhost:4567.
4) The default server has two handlers: 
    i) The URL http://localhost:4567/ displays homepage and the corresponding handler is get(new Route("/")) in Server.java.
  ii) The URL http://localhost:4567/singlerun displays takes user inputs for running a single swat simulation and the corresponding handler is post(new Route("/singlerun")
5) The server code has the following dependencies: 
     i) Commons-io-1.4 for file handling.  
	ii) Spark-0.9.9.4-SNAPSHOT and servlet-api-3.0.pre4 for implementing SPARK functionalities.
   iii) Please make sure that these depedencies are also copied along with the project. Currently, they are residing in C:\Users\srungar2\git\DecisionModel folder.
6) All user inputs from client are passed as query parameters and they can be accessed from Request object in the Server.
7) As soon as the server receives request from client, the server calls SWAT executable(OCM_AMGA2_IndBMP_LHS) located in:
    C:\Users\srungar2\git\DecisionModel\DecisonModel
8) The server copies the input received from client into Client_Input file located in 
    C:\Users\srungar2\git\DecisionModel\DecisonModel
	i) The first line represents whether single simulation needs to be run ( true if 1, false if 0, client always sends 1)
   ii) The second line represnts the watershed index ( 1 for Big Ditch, 2 for Big Long Creek)
  iii) The rest of the lines represent the BMP values for the corresponding watersheds.
9) The server copies the cost values received from client into BMP_DB_09_single_simulation located in:
    C:\Users\srungar2\git\DecisionModel\DecisonModel
10) A new file swat_single_simulation.f90 is created. The main method reads Client_Input.txt file and if single simulation mode is set (i.e. the first value is 1),
    it calls swat_single_simulation mehtod. This method copies all BMP practices provided by client into an array, and calls rerun_bmp method, which simulates the watershed scenario and
	the output of this method is copied back into scenario_simulation.txt file located in C:\Users\srungar2\git\DecisionModel\DecisonModel
11) The server reads the sceanario_simulation.txt file and returns the content back to client. The format of scenario_simulation.txt is as follows:
    Baseline sedement value, sedement percentage reduction, baseline nitrate value, nitrate reduction, baseline phosphorus value, phosphorus percentage reduction, baseline ppr, ppr reduction and cost.
12) Currently, the following are the limitations on server.
     i) The server can handle only a single request at a time.
	ii) Any requests which happen while server is processing a request will be reported an error message.




 

