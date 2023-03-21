package javaLibraryUtilization.control;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.springframework.stereotype.Service;
import utils.Commands;

@Service
public class HelloService {
    public static List<String> librariesWithProblem = new ArrayList<>();
    public static String home;
    public ProjectDTO projectAnalysis(String projectURLfromEndpoint) throws IOException {

        home = System.getProperty("user.dir");

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");
        List<String>  allTheFilesForAnalysis = new ArrayList<>();

        Commands.makeFolderForProject(home,projectURLfromEndpoint);
        home=home+"\\project";

        checkerForMultiplePoms(home +"\\" + projectName,allTheFilesForAnalysis);

        ProjectDTO projectDTO=null;
         for ( String s : allTheFilesForAnalysis) {
             StartAnalysis startAnalysis = new StartAnalysis();
             projectDTO= startAnalysis.startAnalysisOfEach(s,projectName);
        }

        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
        allTheFilesForAnalysis.clear();
        Commands.deleteProject(home, projectName);

        return projectDTO;
    }
    public void checkerForMultiplePoms(String path,List<String> allTheFilesForAnalysis) {

        int add=0;
        File f = new File (path);
        File[] files=f.listFiles();
        for (File file : files) {
                if (file.isDirectory()) {
                    checkerForMultiplePoms(file.toString(),allTheFilesForAnalysis);
                    if  (file.getName().equals("src")) {
                       add++;
                    }
                }
                if (file.isFile()) {
                    if  (file.toString().equals(path +"\\pom.xml")) {
                        add++;
                    }
                }
        }
        if  (add==2){
            allTheFilesForAnalysis.add(path);
        }
    }
}