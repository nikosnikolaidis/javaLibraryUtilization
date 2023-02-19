package javaLibraryUtilization.control;

import java.io.IOException;
import java.util.*;
import org.springframework.stereotype.Service;
import domain.Project;
import utils.Commands;

@Service
public class HelloService {
		
	public static Project project;
    public static List<String> librariesWithProblem = new ArrayList<>();
    public static startAnalysis startAnalysis = new startAnalysis();

    public void projectAnalysis(String projectURLfromEndpoint) throws IOException {

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

        project=new Project("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName);
    	Commands.cloneProject("C:\\Users\\kolid\\eclipse-workspace\\project", projectURLfromEndpoint);

        startAnalysis.startAnalysisOfEach(project,projectName);

        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
       Commands.deleteProject("C:\\Users\\kolid\\eclipse-workspace\\project",projectName);
    }
}