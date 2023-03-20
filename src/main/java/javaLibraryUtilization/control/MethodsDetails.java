package javaLibraryUtilization.control;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class MethodsDetails {
    @Id
    @GeneratedValue
    private Long methodId;
    private String classOfMethod;
    private String methodName;
    private String libOfMethod;

   @OneToMany
   private Set<MethodCallSet> methodCallSet;

    public MethodsDetails(long methodId, String methodName, String libOfMethod, Set<MethodCallSet> methodCallSet) {
        this.methodId = methodId;
        this.methodName = methodName;
        this.libOfMethod = libOfMethod;
        this.methodCallSet = methodCallSet;
    }

    public MethodsDetails(Long methodId, String classOfMethod, String methodName, String libOfMethod, Set<MethodCallSet> methodCallSet) {
        this.methodId = methodId;
        this.classOfMethod = classOfMethod;
        this.methodName = methodName;
        this.libOfMethod = libOfMethod;
        this.methodCallSet = methodCallSet;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getLibOfMethod() {
        return libOfMethod;
    }

    public void setLibOfMethod(String libOfMethod) {
        this.libOfMethod = libOfMethod;
    }

}