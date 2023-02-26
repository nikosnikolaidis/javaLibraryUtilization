package javaLibraryUtilization.control;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.stereotype.Service;
import domain.Project;
import utils.Commands;

@Service
public class HelloService {

    public static List<String> librariesWithProblem = new ArrayList<>();
    public static StartAnalysis startAnalysis = new StartAnalysis();
    public static String home;
    public static String current;
    public static List<String> allTheFilesForAnalysis = new ArrayList<>();

    public void projectAnalysis(String projectURLfromEndpoint) throws IOException {

        home = System.getProperty("user.home");
        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

        Commands.cloneProject(home +"\\project", projectURLfromEndpoint);
        checkerForMultiplePoms(home +"\\project\\" + projectName);

        System.out.println("Hello " +allTheFilesForAnalysis);
         for ( String s : allTheFilesForAnalysis) {
           startAnalysis.startAnalysisOfEach(s,projectName);
        }

        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
       Commands.deleteProject(home+"\\project",projectName);
    }
    public void checkerForMultiplePoms(String path){

        File f = new File (path);
        File[] files=f.listFiles();
        for (File file : files) {
                if (file.isDirectory()){
                    checkerForMultiplePoms(file.toString());
                }
                if (file.isFile()) {
                    if  (file.toString().equals(path +"\\pom.xml")) {
                        allTheFilesForAnalysis.add(path);
                    }
                }
        }
    }
}
