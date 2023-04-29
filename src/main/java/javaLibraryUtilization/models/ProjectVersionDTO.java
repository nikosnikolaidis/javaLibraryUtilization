package javaLibraryUtilization.models;

import javax.persistence.*;
import java.util.List;
@Entity
public class ProjectVersionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long projectVersionDTOid;
    public String projectName;
    public String sha;
    @OneToMany
    public List<ProjectModuleDTO> projectModuleDTOS;

    public ProjectVersionDTO() {
    }

    public ProjectVersionDTO( String projectName, String sha) {
        this.projectName = projectName;
        this.sha = sha;
    }

    public ProjectVersionDTO( String projectName, String sha, List<ProjectModuleDTO> projectModuleDTOS) {
        this.projectName = projectName;
        this.sha=sha;
        this.projectModuleDTOS = projectModuleDTOS;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectModuleDTO> getProjectModuleDTOS() {
        return projectModuleDTOS;
    }

    public void setProjectModuleDTOS(List<ProjectModuleDTO> projectModuleDTOS) {
        this.projectModuleDTOS = projectModuleDTOS;
    }

    public void setProjectVersionDTOid(long projectVersionDTOid) {
        this.projectVersionDTOid = projectVersionDTOid;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}