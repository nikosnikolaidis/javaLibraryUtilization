package javaLibraryUtilization.control;

import callgraph.infrastructure.entities.MethodCallSet;
import javax.persistence.*;
import java.util.Set;

@Entity
public class MethodsDetails {
   @Id
   @GeneratedValue
    public Long methodId;
    public String classOfMethod;
    public String methodName;
    public String libOfMethod;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<callgraph.infrastructure.entities.MethodCallSet> methodCallSet;

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