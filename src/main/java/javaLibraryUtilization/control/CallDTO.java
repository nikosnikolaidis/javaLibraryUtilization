package javaLibraryUtilization.control;

public class CallDTO {
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
