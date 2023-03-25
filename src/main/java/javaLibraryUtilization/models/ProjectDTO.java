package javaLibraryUtilization.models;

import javax.persistence.*;
import java.util.List;
@Entity
public class ProjectDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectDTOid;
    public String projectName;
    private int NUL;
    @OneToMany
    public List<LibraryDTO> libraries;
    private String sha;

    public ProjectDTO() {
    }

    public ProjectDTO(String projectName, int NUL, List<LibraryDTO> libraries) {
        this.projectName = projectName;
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

    public List<LibraryDTO> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<LibraryDTO> libraries) {
        this.libraries = libraries;
    }
}