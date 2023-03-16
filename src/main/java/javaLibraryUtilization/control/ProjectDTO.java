package javaLibraryUtilization.control;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ProjectDTO {
    @Id
    public String projectName;
    public List <MethodsDetails> methodsDetails;
    private int NUL;
    public List<Library> libraries;

    public ProjectDTO(String projectName, List<MethodsDetails> methodsDetails, int NUL) {
        this.projectName = projectName;
        this.methodsDetails = methodsDetails;
        this.NUL = NUL;
    }

    public ProjectDTO(String projectName, List<MethodsDetails> methodsDetails, int NUL, List<Library> libraries) {
        this.projectName = projectName;
        this.methodsDetails = methodsDetails;
        this.NUL = NUL;
        this.libraries = libraries;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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