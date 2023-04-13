package javaLibraryUtilization.services;

import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.ProjectVersionDTO;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;
import utils.Commands;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class HistoricService {
    public List<ProjectVersionDTO> historicAnalysis(String url,int number) throws IOException, GitAPIException {

        String home = System.getProperty("user.dir");
        Commands.makeFolderForProject(home, url);

        ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO();
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

        for (String sha : updatedShaList) {
            try {
                String path;
                if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                    path= home + "/project/" + projectName;
                }
                else {
                    path= home + "\\project\\" + projectName;
                }
                Commands.checkoutSha(path, sha);
                StartAnalysis startAnalysis = new StartAnalysis();
                projectVersionDTO = startAnalysis.startAnalysisOfEach(path, projectName, sha);
                projectVersionDTOList.add(projectVersionDTO);

            } catch (IOException e) {
                System.err.println("This version couldnt be analyzed!");
                e.printStackTrace();
            }
        }
        Commands.deleteProject(home, projectName);
        return projectVersionDTOList;
    }
}