package javaLibraryUtilization.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import callgraph.InvestigatorFacade;
import callgraph.infrastructure.entities.MethodCallSet;
import callgraph.infrastructure.entities.MethodDecl;
import domain.MethodOfLibrary;
import domain.Project;
import javaLibraryUtilization.LibUtil;
import utils.Commands;

@Service
public class HelloService {
		
	public static Project project;
    public static List<String> allMethodsCalledByProject = new ArrayList<>();
    public static List<String> librariesWithProblem = new ArrayList<>();
    public static List<MethodsDetails> methodsDetailsList = new ArrayList<MethodsDetails>();
    public static ProjectDTO ProjectDTO;

    public void projectAnalysis(String projectURLfromEndpoint) throws IOException {

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

        project=new Project("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName +"\\flink-connector-jdbc");
    	Commands.cloneProject("C:\\Users\\kolid\\eclipse-workspace\\project", projectURLfromEndpoint);

        //mvn clean command
        Commands.methodForMvnCleanCommand(project.getProjectPath());
    	Commands.getJarDependenciesForInitParsing(project.getProjectPath());

        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
       Commands.deleteProject("C:\\Users\\kolid\\eclipse-workspace\\project",projectName);
    }
}