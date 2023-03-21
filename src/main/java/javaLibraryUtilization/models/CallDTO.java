package javaLibraryUtilization.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CallDTO {
    @Id
    @GeneratedValue
    private long callDTOid;
    public String filepath;
    public String packageName;
    public String qualifiedName;
    public String previousMethod;

    public CallDTO(String filepath, String packageName, String qualifiedName, String previousMethod) {
        this.filepath = filepath;
        this.packageName = packageName;
        this.qualifiedName = qualifiedName;
        this.previousMethod = previousMethod;
    }
}
