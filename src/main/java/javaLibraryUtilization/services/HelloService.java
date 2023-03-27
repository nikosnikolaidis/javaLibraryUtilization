package javaLibraryUtilization.services;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.*;
import javaLibraryUtilization.repositories.CallRepository;
import javaLibraryUtilization.repositories.LibraryRepository;
import javaLibraryUtilization.repositories.MethodDetailsRepository;
import javaLibraryUtilization.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Commands;

import javax.sound.midi.SysexMessage;

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
    public static String home;
    public static List<String> shaList = new ArrayList<>();
    public static ProjectDTO projectDTO = new ProjectDTO();

    public ProjectVersionDTO projectAnalysis(String projectURLfromEndpoint) throws IOException {

        home = System.getProperty("user.dir");

        System.out.println(projectURLfromEndpoint);

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");
        List<String>  allTheFilesForAnalysis = new ArrayList<>();

        //make folder & clone
        Commands.makeFolderForProject(home,projectURLfromEndpoint);
        //Get project's sha
        String sha = Commands.revParse(home,projectURLfromEndpoint);

        home=home+"\\project";
        //check if project is multimaven
        checkerForMultiplePoms(home +"\\" + projectName,allTheFilesForAnalysis);

        ProjectVersionDTO projectVersionDTO = null;

        if (shaList.contains(sha)){
            System.out.println("It contains the sha");
            return projectDTO.getProjVersion();
        }
        else {
            shaList.add(sha);
            for ( String s : allTheFilesForAnalysis) {
                StartAnalysis startAnalysis = new StartAnalysis();
                System.out.println("The s is " + s);
                projectVersionDTO = startAnalysis.startAnalysisOfEach(s,projectName,sha);
            }
            allTheFilesForAnalysis.clear();
            Commands.deleteProject(home, projectName);

            for (LibraryDTO libraryDTO: projectVersionDTO.getLibraries()){
                for (MethodDetailsDTO methodDetailsDTO : libraryDTO.methodDetailsDTOList) {
                    for (CallDTO callDTO: methodDetailsDTO.callDTOList) {
                        callRepository.save(callDTO);
                    }
                    methodDetailsRepository.save(methodDetailsDTO);
                }
                libraryRepository.save(libraryDTO);
            }
            projectRepository.save(projectVersionDTO);

            projectDTO.setProjVersion(projectVersionDTO);

            return projectVersionDTO;
        }

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