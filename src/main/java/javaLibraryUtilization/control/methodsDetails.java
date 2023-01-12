package javaLibraryUtilization.control;

import callgraph.infrastructure.entities.MethodCallSet;

import java.util.ArrayList;
import java.util.Set;

public class methodsDetails {
    private Long methodId;
    private String methodName;
    private String libOfMethod;
    public Long methodCallSetId;
    public Set<MethodCallSet> methodCallSet;

    public methodsDetails(long methodId, String methodName, String libOfMethod, long methodCallSetId, Set<MethodCallSet> methodCallSet) {
        this.methodId = methodId;
        this.methodName = methodName;
        this.libOfMethod = libOfMethod;
        this.methodCallSetId = methodCallSetId;
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