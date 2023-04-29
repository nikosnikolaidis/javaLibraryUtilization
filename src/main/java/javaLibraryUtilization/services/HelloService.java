package javaLibraryUtilization.services;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.*;
import javaLibraryUtilization.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Commands;

@Service
public class HelloService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectVersionRepository projectVersionRepository;
    @Autowired
    private ProjectModuleRepository projectModuleRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private MethodDetailsRepository methodDetailsRepository;
    @Autowired
    private CallRepository callRepository;
    public static String home;
    public static List<String> shaList = new ArrayList<>();

    public ProjectVersionDTO projectAnalysis(String projectURLfromEndpoint) throws IOException {

        home = System.getProperty("user.dir");

        System.out.println(projectURLfromEndpoint);

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");
        List<String>  allTheFilesForAnalysis = new ArrayList<>();

        //make folder & clone
        Commands.makeFolderForProject(home,projectURLfromEndpoint);

        //get project path
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            home = home + "project/" +projectName;
        }
        else {
            home = home + "\\project\\"+projectName;
        }

        //Get project's sha
        String sha = Commands.revParse(home,projectURLfromEndpoint);

        //check if project is multimaven
        checkerForMultiplePoms(home , allTheFilesForAnalysis);


        ProjectDTO projectDTO = new ProjectDTO();
        ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO(projectName, sha);

        List<ProjectModuleDTO> listForAllProjectsOfMultiMaven=new ArrayList<>();

        if (projectVersionRepository.findBySha(sha)!=null) {
            System.out.println("Exists");
            return projectVersionRepository.findBySha(sha);
        }
        else {
            shaList.add(sha);
                for (String s : allTheFilesForAnalysis) {
                    System.out.println("All files for analysis element" +s);
                    if (allTheFilesForAnalysis.size() >= 1) {
                        Commands.mvnPackage(s);
                    }
                    StartAnalysis startAnalysis = new StartAnalysis();
                    ProjectModuleDTO projectModuleDTO = startAnalysis.startAnalysisOfEach(s, projectName, sha);
                    listForAllProjectsOfMultiMaven.add(projectModuleDTO);
                }

            allTheFilesForAnalysis.clear();
                System.out.println("before the delete home"+home);
            Commands.deleteProject(home, projectName);


            projectVersionDTO.setProjectModuleDTOS(listForAllProjectsOfMultiMaven);
            projectDTO.setProjVersion(projectVersionDTO);


            for(ProjectModuleDTO projectModuleDTO: listForAllProjectsOfMultiMaven) {
                for (LibraryDTO libraryDTO : projectModuleDTO.getLibraries()) {
                    for (MethodDetailsDTO methodDetailsDTO : libraryDTO.methodDetailsDTOList) {
                        for (CallDTO callDTO : methodDetailsDTO.callDTOList) {
                            callRepository.save(callDTO);
                        }
                        methodDetailsRepository.save(methodDetailsDTO);
                    }
                    libraryRepository.save(libraryDTO);
                }
                projectModuleRepository.save(projectModuleDTO);
            }
            projectVersionRepository.save(projectVersionDTO);

            projectRepository.save(projectDTO);

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
                    if  (file.getName().equals("pom.xml")) {
                        add++;
                    }
                }
        }
        if  (add==2){
            allTheFilesForAnalysis.add(path);
        }
    }
}