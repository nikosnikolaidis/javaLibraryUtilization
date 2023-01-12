package javaLibraryUtilization.control;

import java.util.List;

public class ProjectDTO {
    private Long id;
    private String projectName;
    private methodsDetails methodsDetails;
    private int NUL;
    private List<Library> libraries;

    public ProjectDTO(Long id, String projectName) {
        this.id = id;
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
