package domain;

import com.github.javaparser.ast.body.MethodDeclaration;

import callgraph.infrastructure.entities.MethodCallSet;

public class MethodOfLibrary {
    private MethodDeclaration methodDeclaration;
    private String qualifiedSignature;
    private String filePath;
    private MethodCallSet setSaved;

    public MethodOfLibrary(MethodDeclaration methodDeclaration, String qualifiedSignature) {
        this.methodDeclaration = methodDeclaration;
        this.qualifiedSignature = qualifiedSignature;
    } 

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }
    
    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }
 
    public String getQualifiedSignature() {
        return qualifiedSignature;
    }		    	

    public void setQualifiedSignature(String qualifiedSignature) {
        this.qualifiedSignature = qualifiedSignature;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public String toString() {
        return "domain.MethodOfLibrary{" +
                "methodDeclaration=" + methodDeclaration.getDeclarationAsString() +
                ", qualifiedSignature='" + qualifiedSignature + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

	public MethodCallSet getSetSaved() {
		return setSaved;
	}

	public void setSetSaved(MethodCallSet setSaved) {
		this.setSaved = setSaved;
	}
    
    
}
