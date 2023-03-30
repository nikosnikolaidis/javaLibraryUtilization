package javaLibraryUtilization.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javaLibraryUtilization.StartAnalysis;
import javaLibraryUtilization.models.ProjectDTO;
import javaLibraryUtilization.models.ProjectVersionDTO;
import javaLibraryUtilization.repositories.ProjectRepository;
import javaLibraryUtilization.services.HistoricService;
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
	public ProjectVersionDTO getMetricsAnalysis(@RequestParam("url")String url) {
		return projectRepository.findByProjectName("C:\\Users\\kolid\\eclipse-workspace\\javaLibraryUtilization\\project\\maria");
	}
	@CrossOrigin("*")
	@GetMapping(path="/startNewAnalysis")
	public List<ProjectVersionDTO> startNewAnalysis(@RequestParam("url")String url,
													@RequestParam("numberOfCommits")int number){
		try {
			return HistoricService.historicAnalysis(url,number);
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
}