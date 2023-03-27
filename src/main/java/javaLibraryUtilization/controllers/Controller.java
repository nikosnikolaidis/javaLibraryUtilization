package javaLibraryUtilization.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.ProjectDTO;
import javaLibraryUtilization.models.ProjectVersionDTO;
import javaLibraryUtilization.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.Commands;

@RestController
public class Controller {
	@Autowired
	private javaLibraryUtilization.services.HelloService HelloService;
	@Autowired
	private ProjectRepository projectRepository;

	@CrossOrigin("*")
	@GetMapping(path="/startAnalysisWithMetrics")
	public ProjectVersionDTO metricsAnalysis(@RequestParam("url")String url){
		try {
			return HelloService.projectAnalysis(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@CrossOrigin("*")
	@GetMapping(path="/getAnalysisWithMetrics")
	public ProjectVersionDTO getMetricsAnalysis(@RequestParam("url")String url){
		return projectRepository.findByProjectName("C:\\Users\\kolid\\eclipse-workspace\\javaLibraryUtilization\\project\\maria");
	}
	@CrossOrigin("*")
	@GetMapping(path="/startNewAnalysis")

	public List<ProjectVersionDTO> startNewAnalysis(@RequestParam("url")String url){

		ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO();
		List <ProjectVersionDTO> projectVersionDTOList = new ArrayList<>();

		List<String> shaList = new ArrayList<>();
		shaList.add("179f4c326be92e0371bda0e873bfa7baf259ff41");
		shaList.add("b8fc2461de1862e49f5bbc90409c71b8a24358b9");

		String projectName= url.split("/")[url.split("/").length-1].replace(".git", "");
		String home = System.getProperty("user.dir");

		for (String sha:shaList) {
			try {
				Commands.makeFolderForProject(home,url);
				Commands.checkoutSha(home+"\\project\\"+projectName,sha);
				StartAnalysis startAnalysis = new StartAnalysis();
				projectVersionDTO = startAnalysis.startAnalysisOfEach(home+"\\project\\"+projectName,projectName,sha);
				projectVersionDTOList.add(projectVersionDTO);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return projectVersionDTOList;
	}
}