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

        Git gitRepo= Git.open(new File(home+"\\project\\"+projectName));

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
            System.out.println(i);
            updatedShaList.add(shaList.get(i));
        }

        System.out.println("The sha List" +updatedShaList.size());

        for (String sha : updatedShaList) {
            try {
                Commands.checkoutSha(home + "\\project\\" + projectName, sha);
                StartAnalysis startAnalysis = new StartAnalysis();
                projectVersionDTO = startAnalysis.startAnalysisOfEach(home + "\\project\\" + projectName, projectName, sha);
                projectVersionDTOList.add(projectVersionDTO);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return projectVersionDTOList;
    }
}