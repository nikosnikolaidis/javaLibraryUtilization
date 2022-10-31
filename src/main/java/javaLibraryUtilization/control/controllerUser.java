package javaLibraryUtilization.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class controllerUser {
	
	@GetMapping(value="/api/user")
	public String user(){
		return "Welcome";
	}
}
