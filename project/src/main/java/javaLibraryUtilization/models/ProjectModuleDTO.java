package javaLibraryUtilization.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProjectModuleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectModuleID;
    public String moduleName;
    private int NUL;
    @OneToMany
    public List<LibraryDTO> libraries;
    public ProjectModuleDTO() {
    }
    public ProjectModuleDTO(String projectName) {
        this.moduleName = projectName;
    }

    public ProjectModuleDTO( String projectName, int NUL, List<LibraryDTO> libraries) {
        this.moduleName = projectName;
        this.NUL = NUL;
        this.libraries = libraries;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
