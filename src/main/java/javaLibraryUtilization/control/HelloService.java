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
    public static StartAnalysis startAnalysis = new StartAnalysis();
    public static String home;
    public void projectAnalysis(String projectURLfromEndpoint) throws IOException {

        home = System.getProperty("user.home");
        System.out.println("current" + home);

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

        project = new Project(home + "\\eclipse-workspace\\project\\" + projectName);
    	Commands.cloneProject(home +"\\eclipse-workspace\\project", projectURLfromEndpoint);

        startAnalysis.startAnalysisOfEach(project,projectName);

        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
       Commands.deleteProject(home +"\\eclipse-workspace\\project",projectName);
    }
}