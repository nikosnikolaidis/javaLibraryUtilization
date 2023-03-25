package javaLibraryUtilization.controllers;

import java.io.IOException;
import java.util.List;

import javaLibraryUtilization.models.ProjectDTO;
import javaLibraryUtilization.repositories.ProjectRepository;
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
	private ProjectRepository projectRepository;

	@CrossOrigin("*")
	@GetMapping(path="/startAnalysisWithMetrics")
	public ProjectDTO metricsAnalysis(@RequestParam("url")String url){
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
	public ProjectDTO getmetricsAnalysis(@RequestParam("url")String url){
		return projectRepository.findByProjectName("C:\\Users\\kolid\\eclipse-workspace\\javaLibraryUtilization\\project\\maria");
	}
}