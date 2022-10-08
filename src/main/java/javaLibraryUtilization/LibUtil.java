package javaLibraryUtilization;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import domain.MethodOfLibrary;	
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LibUtil {
    
    public List<MethodOfLibrary> methodsOfLibrary= new ArrayList<>();
 
    public LibUtil(String project) {
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(Paths.get(project));
        //System.out.println("hello" + projectRoot);
        
        List<SourceRoot> sourceRoots = projectRoot.getSourceRoots();
        //System.out.print("Sour" + sourceRoots);
   
        try {
            createSymbolSolver(project);
        } catch (IllegalStateException e) {
            return;
        }		
        
        sourceRoots
                .forEach(sourceRoot -> {
                    System.out.println("Analysing Source Root: " + sourceRoot.getRoot().toString() );
                    try {
                    	
                        List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
                        parseResults
                                .stream()
                                .filter(res -> res.getResult().isPresent())
                                .filter(f -> !f.getResult().get().getStorage().get().getPath().toString().contains(".mvn\\wrapper"))
                                .forEach(res -> { analyzeUnit(res.getResult().get(), res.getResult().get().getStorage().get().getPath().toString());
                                });
                    } catch (Exception ignored) {
                    	
                    }
                });
       System.out.println();

       methodsOfLibrary.forEach(System.out::println);
    }

    private void analyzeUnit(CompilationUnit compilationUnit, String filePath) {
        VoidVisitor<List<MethodOfLibrary>> methodCall = new MethodCall();
        List<MethodOfLibrary> methodsOfFile= new ArrayList<>();
        methodCall.visit(compilationUnit,methodsOfFile);
        methodsOfFile.forEach(m -> {
            m.setFilePath(filePath);
        });
        methodsOfLibrary.addAll(methodsOfFile);
    }

    private class MethodCall extends VoidVisitorAdapter<List<MethodOfLibrary>> {
        @Override
        public void visit(MethodDeclaration n, List<MethodOfLibrary> collector) {
            super.visit(n, collector);
            collector.add(new MethodOfLibrary(n,n.resolve().getQualifiedSignature()));
        }
    }

    //Create Symbol Solver
    private void createSymbolSolver(String projectDir) {
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(projectDir));
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(javaParserTypeSolver);
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration
                .setSymbolResolver(symbolSolver)
                .setAttributeComments(false).setDetectOriginalLineSeparator(true);
        StaticJavaParser
                .setConfiguration(parserConfiguration);
    }

	public List<MethodOfLibrary> getMethodsOfLibrary() {
		return methodsOfLibrary;
	}

	public void setMethodsOfLibrary(List<MethodOfLibrary> methodsOfLibrary) {
		this.methodsOfLibrary = methodsOfLibrary;
	}
}
