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

import javax.transaction.Transactional;

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

    @Transactional
    public ProjectVersionDTO projectAnalysis(String projectURLfromEndpoint, String commit) throws IOException {

        home = System.getProperty("user.dir");

        System.out.println(projectURLfromEndpoint);

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");
        List<String>  allTheFilesForAnalysis = new ArrayList<>();

        //make folder & clone
        Commands.makeFolderForProject(home,projectURLfromEndpoint);

        //get project path
        String path;
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            path = home + "project/" +projectName;
        }
        else {
            path = home + "\\project\\"+projectName;
        }

        //checkout to sha
        if(commit!=null && !commit.equals("")){
            Commands.checkoutSha(path, commit);
        }

        //Get project's sha
        String sha = Commands.revParse(path,projectURLfromEndpoint);

        //check if project is multimaven
        checkerForMultiplePoms(path , allTheFilesForAnalysis);

        ProjectDTO projectDTO;
        Optional<ProjectDTO> projectDTO_DB= projectRepository.findByProjectName(projectName);
        if(projectDTO_DB.isPresent()){
            projectDTO =projectDTO_DB.get();
        }
        else {
            projectDTO = new ProjectDTO(projectName);
        }
        ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO(projectName, sha);

        List<ProjectModuleDTO> listForAllProjectsOfMultiMaven=new ArrayList<>();

        if (projectVersionRepository.findBySha(sha)!=null) {
            System.out.println("Exists");
            return projectVersionRepository.findBySha(sha);
        }
        else {
            System.out.println("-------------");
            System.out.println("Size: "+allTheFilesForAnalysis.size());
            System.out.println("-------------");
            allTheFilesForAnalysis.forEach(System.out::println);
            System.out.println("-------------");
            int counter=1;
            for (String s : allTheFilesForAnalysis) {
                System.out.println("-----------");
                System.out.println("Module: " +counter+ "/" +allTheFilesForAnalysis.size());
                counter++;
                if (allTheFilesForAnalysis.size() >= 1) {
                    Commands.mvnPackage(s);
                }
                StartAnalysis startAnalysis = new StartAnalysis();
                ProjectModuleDTO projectModuleDTO = startAnalysis.startAnalysisOfEach(s, projectName, sha);
                if(projectModuleDTO != null) {
                    listForAllProjectsOfMultiMaven.add(projectModuleDTO);
                }
            }

            allTheFilesForAnalysis.clear();

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

            if(!projectDTO_DB.isPresent()){
                projectRepository.save(projectDTO);
            }

            Commands.deleteProject(home, projectName);

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