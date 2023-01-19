package javaLibraryUtilization.control;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static javaLibraryUtilization.control.HelloService.methodsDetailsList;
import static javaLibraryUtilization.control.HelloService.projectDTOlist;

@RestController
public class Controller {
	@Autowired
	private HelloService HelloService;

	@GetMapping(path="/startAnalysis")
	public List<methodsDetails> user(@RequestParam("url")String url){
		try {
			HelloService.projectAnalysis(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodsDetailsList;
	}
	@GetMapping(path="/startAnalysisWithMetrics")
	public ProjectDTO metricsAnalysis1(@RequestParam("url")String url){
		try {
			HelloService.projectAnalysis(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return javaLibraryUtilization.control.HelloService.ProjectDTO;
	}
	@GetMapping(path="/tests")
	public ProjectDTO tests(@RequestParam("url")String url) throws IOException {
		HelloService.projectAnalysis(url);
		return javaLibraryUtilization.control.HelloService.ProjectDTO;
	}
}