package javaLibraryUtilization.services;

import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.*;
import javaLibraryUtilization.repositories.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Commands;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoricService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectVersionRepository projectVersionRepository;
    @Autowired
    ProjectModuleRepository projectModuleRepository;
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    MethodDetailsRepository methodDetailsRepository;
    @Autowired
    CallRepository callRepository;

    @Transactional
    public List<ProjectVersionDTO> historicAnalysis(String url,int number) throws IOException, GitAPIException {

        String home = System.getProperty("user.dir");
        Commands.makeFolderForProject(home, url);

        List<ProjectVersionDTO> projectVersionDTOList = new ArrayList<>();

        List<String> shaList = new ArrayList<>();
        int numberOfCommits=0;

        String projectName = url.split("/")[url.split("/").length - 1].replace(".git", "");

        Git gitRepo;
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            gitRepo = Git.open(new File(home + "/project/" + projectName));
        }
        else {
            gitRepo = Git.open(new File(home + "\\project\\" + projectName));
        }

        List<Ref> branches = gitRepo.branchList().call();

        Iterable<RevCommit> commits = null;
        for (Ref branch : branches) {
            if(branch.getName().equals(""+gitRepo.getRepository().getFullBranch())) {
                String branchName = branch.getName();
                System.out.println("Commits of branch: " + branchName);
                System.out.println("-------------------------------------");

                commits = gitRepo.log().add(gitRepo.getRepository().resolve(branchName)).call();

                for (RevCommit commit : commits) {
                    System.out.println(commit.getName());
                    numberOfCommits=numberOfCommits+1;
                    shaList.add(commit.getName());
                }
            }
        }

        numberOfCommits = numberOfCommits/number;

        List<String> updatedShaList = new ArrayList<>();

        for (int i=0; i<shaList.size(); i=i+numberOfCommits) {
            updatedShaList.add(shaList.get(i));
        }

        ///
        Optional<ProjectDTO> projectDTO_DB= projectRepository.findByProjectName(projectName);
        if(projectDTO_DB.isPresent() && projectDTO_DB.get().getProjectVersionDTOList().size()==shaList.size()){
            return projectDTO_DB.get().getProjectVersionDTOList();
        }
        ///

        ProjectDTO projectDTO;
        if(projectDTO_DB.isPresent()){
            projectDTO =projectDTO_DB.get();
        }
        else {
            projectDTO = new ProjectDTO(projectName);
        }


        for (String sha : updatedShaList) {
            try {
                if (projectVersionRepository.findBySha(sha)!=null) {
                    System.out.println("Exists "+ sha);
                    ProjectVersionDTO projectVersionDTO= projectVersionRepository.findBySha(sha);
                    projectVersionDTOList.add(projectVersionDTO);
                }
                else {
                    String path;
                    if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                        path = home + "/project/" + projectName;
                    } else {
                        path = home + "\\project\\" + projectName;
                    }
                    Commands.checkoutSha(path, sha);

                    List<String> allTheFilesForAnalysis = new ArrayList<>();
                    List<ProjectModuleDTO> listForAllProjectsOfMultiMaven = new ArrayList<>();
                    checkerForMultiplePoms(path, allTheFilesForAnalysis);

                    for (String s : allTheFilesForAnalysis) {
                        if (allTheFilesForAnalysis.size() >= 1) {
                            Commands.mvnPackage(s);
                        }
                        StartAnalysis startAnalysis = new StartAnalysis();
                        ProjectModuleDTO projectModuleDTO = startAnalysis.startAnalysisOfEach(path, projectName, sha);
                        listForAllProjectsOfMultiMaven.add(projectModuleDTO);
                    }
                    allTheFilesForAnalysis.clear();

                    ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO(projectName, sha, listForAllProjectsOfMultiMaven);
                    projectVersionDTOList.add(projectVersionDTO);
                }
            } catch (IOException e) {
                System.err.println("This version couldnt be analyzed!");
                e.printStackTrace();
            }
        }

        for(ProjectVersionDTO s: projectVersionDTOList ) {
            for (ProjectModuleDTO projectModuleDTO : s.getProjectModuleDTOS()) {
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
            projectVersionRepository.save(s);
        }

        projectDTO.setProjectVersionDTOList(projectVersionDTOList);
        if(!projectDTO_DB.isPresent()){
            projectRepository.save(projectDTO);
        }

        Commands.deleteProject(home, projectName);
        return projectVersionDTOList;
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