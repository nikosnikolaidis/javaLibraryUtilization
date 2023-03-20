package javaLibraryUtilization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	 public static void main(String[] args) {
		  /*
	         * 
	        run "mvn clean" in cmd
	        run "mvn dependency:copy-dependencies -Dclassifier=sources  -DexcludeTransitive" in cmd
	        foreach library in ./target/dependency
	        jar xf nameofjar.jar
	            parse
	        	  and get all method declarations (Done in Main2.java -> Main2.methodsOfLibrary)
	        	  foreach method
	        	  check if it exists in our list of methods -> Main  .allMethodsCalled
	        	    	if exists
	                    start call-graph from there
	                    InvestigatorFacade facade = new InvestigatorFacade(dirOfProject, fileOfMethod, methodDeclaration);
	                    Set<MethodCallSet> methodCallSets = facade.start();
	         */
	    	SpringApplication.run(Main.class,args);


	    }
}
