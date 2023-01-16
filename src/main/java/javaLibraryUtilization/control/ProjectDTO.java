package javaLibraryUtilization.control;

import java.util.List;

public class ProjectDTO {
    private int id;
    private String projectName;
    private methodsDetails methodsDetails;
    private int NUL;
    private List<Library> libraries;

    public ProjectDTO(int id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public javaLibraryUtilization.control.methodsDetails getMethodsDetails() {
        return methodsDetails;
    }

    public void setMethodsDetails(javaLibraryUtilization.control.methodsDetails methodsDetails) {
        this.methodsDetails = methodsDetails;
    }

    public int getNUL() {
        return NUL;
    }

    public void setNUL(int NUL) {
        this.NUL = NUL;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }
}
