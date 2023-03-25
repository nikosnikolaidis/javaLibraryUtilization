package javaLibraryUtilization.services;

import java.io.File;
import java.io.IOException;
import java.util.*;

import callgraph.infrastructure.entities.MethodCallSet;
import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.CallDTO;
import javaLibraryUtilization.models.LibraryDTO;
import javaLibraryUtilization.models.MethodDetailsDTO;
import javaLibraryUtilization.models.ProjectDTO;
import javaLibraryUtilization.repositories.CallRepository;
import javaLibraryUtilization.repositories.LibraryRepository;
import javaLibraryUtilization.repositories.MethodDetailsRepository;
import javaLibraryUtilization.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Commands;

@Service
public class HelloService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private MethodDetailsRepository methodDetailsRepository;
    @Autowired
    private CallRepository callRepository;
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


        for (LibraryDTO libraryDTO: projectDTO.getLibraries()){
            for (MethodDetailsDTO methodDetailsDTO : libraryDTO.methodDetailsDTOList) {
                for (CallDTO callDTO: methodDetailsDTO.callDTOList) {
                    callRepository.save(callDTO);
                }
                methodDetailsRepository.save(methodDetailsDTO);
            }
            libraryRepository.save(libraryDTO);
        }
        projectRepository.save(projectDTO);

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