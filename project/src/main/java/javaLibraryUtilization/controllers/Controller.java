package javaLibraryUtilization.controllers;

import java.io.IOException;
import java.util.List;

import javaLibraryUtilization.models.ProjectVersionDTO;
import javaLibraryUtilization.repositories.ProjectRepository;
import javaLibraryUtilization.repositories.ProjectVersionRepository;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private javaLibraryUtilization.services.HelloService HelloService;
	@Autowired
	private javaLibraryUtilization.services.HistoricService HistoricService;
	@Autowired
	private ProjectVersionRepository projectModule;

	@CrossOrigin("*")
	@GetMapping(path="/startAnalysisWithMetricsForOneProjectVersion")
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
	public ProjectVersionDTO getMetricsAnalysis(@RequestParam("url")String url) {
		String projectName= url.split("/")[url.split("/").length-1].replace(".git", "");
		return projectModule.findByProjectName(projectName);
	}
	@CrossOrigin("*")
	@GetMapping(path="/startHistoryAnalysis")
	public List<ProjectVersionDTO> startNewAnalysis(@RequestParam("url")String url,
											 		@RequestParam("numberOfCommits")int number){
		try {
			return HistoricService.historicAnalysis(url,number);
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
}