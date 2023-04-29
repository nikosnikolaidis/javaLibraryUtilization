package javaLibraryUtilization.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProjectDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    public String projectName;
    @OneToMany
    private List<ProjectVersionDTO> projectVersionDTOList;
    @OneToOne
    public ProjectVersionDTO ProjVersion;

    public ProjectDTO() {
    }
    public ProjectDTO(ProjectVersionDTO projVersion) {
        ProjVersion = projVersion;
    }

    public List<ProjectVersionDTO> getProjectVersionDTOList() {
        return projectVersionDTOList;
    }

    public void setProjectVersionDTOList(List<ProjectVersionDTO> projectVersionDTOList) {
        this.projectVersionDTOList = projectVersionDTOList;
    }

    public ProjectVersionDTO getProjVersion() {
        return ProjVersion;
    }

    public void setProjVersion(ProjectVersionDTO projVersion) {
        ProjVersion = projVersion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
