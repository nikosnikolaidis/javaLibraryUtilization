package domain;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Project {
    private String url;
    private String projectPath;
    private Set<JavaFile> javaFiles;

    public Project(String url, String clonePath) {
        this.url = url;
        this.projectPath = clonePath;
    }

    public Project(String clonePath){
        this.projectPath=clonePath;
        this.javaFiles= ConcurrentHashMap.newKeySet();
    }	 
    public String getUrl() {
        return url;
    }
 
    public String getProjectPath() {
        return projectPath;
    }
    	 
    public void setProjectPath(String clonePath) {
		this.projectPath = clonePath;
	}
	public Set<JavaFile> getJavaFiles() {
        return javaFiles;
    }
}
