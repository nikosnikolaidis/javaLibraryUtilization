package javaLibraryUtilization.control;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static javaLibraryUtilization.control.HelloService.methodsDetailsList;


@RestController
public class Controller {
	
	@Autowired
	private HelloService HelloService;
	
	@GetMapping(path="/startAnalysis")
	public String user(@RequestParam("url")String url){
		try {
			HelloService.testing(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return methodsDetailsList.toString();
	}
}
