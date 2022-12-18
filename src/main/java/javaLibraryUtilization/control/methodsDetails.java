package javaLibraryUtilization.control;

import callgraph.infrastructure.entities.MethodCallSet;

import java.util.ArrayList;

public class methodsDetails {
    public int methodId;
    public String methodName;
    public String libOfMethod;
    public int methodCallSetId;
    public String methodCallSet;

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
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

    public String getMethodCallSet() {
        return methodCallSet;
    }

    public void setMethodCallSet(String methodCallSet) {
        this.methodCallSet = methodCallSet;
    }

    public methodsDetails(int methodId, String methodName, String libOfMethod, int methodCallSetId, String methodCallSet) {
        this.methodId = methodId;
        this.methodName = methodName;
        this.libOfMethod = libOfMethod;
        this.methodCallSetId = methodCallSetId;
        this.methodCallSet = methodCallSet;
    }

    public int getMethodCallSetId() {
        return methodCallSetId;
    }

    public void setMethodCallSetId(int methodCallSetId) {
        this.methodCallSetId = methodCallSetId;
    }
}


