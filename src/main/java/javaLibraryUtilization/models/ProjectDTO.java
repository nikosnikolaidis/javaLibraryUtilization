package javaLibraryUtilization.models;

import java.util.List;
public class ProjectDTO {
    private List<ProjectVersionDTO> projectVersionDTOList;
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
}
