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

    public CallDTO() {
    }

    public long getCallDTOid() {
        return callDTOid;
    }

    public void setCallDTOid(long callDTOid) {
        this.callDTOid = callDTOid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getPreviousMethod() {
        return previousMethod;
    }

    public void setPreviousMethod(String previousMethod) {
        this.previousMethod = previousMethod;
    }

    public CallDTO(String filepath, String packageName, String qualifiedName, String previousMethod) {
        this.filepath = filepath;
        this.packageName = packageName;
        this.qualifiedName = qualifiedName;
        this.previousMethod = previousMethod;
    }
}
