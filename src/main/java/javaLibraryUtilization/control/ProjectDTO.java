package javaLibraryUtilization.control;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ProjectDTO {
    public String projectName;
    @Id
    @GeneratedValue
    private Long Id;
    @OneToMany
    public List <MethodsDetails> methodsDetails;
    private int NUL;
    @OneToMany
    public List<Library> libraries;

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