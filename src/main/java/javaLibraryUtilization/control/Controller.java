package javaLibraryUtilization.control;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Autowired
	private HelloService HelloService;

	@GetMapping(path="/startAnalysisWithMetrics")
	public ProjectDTO metricsAnalysis(@RequestParam("url")String url){
		try {
			HelloService.projectAnalysis(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return StartAnalysis.ProjectDTO;
	}
}