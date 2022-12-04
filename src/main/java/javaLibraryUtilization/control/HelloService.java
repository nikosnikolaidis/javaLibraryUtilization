package javaLibraryUtilization.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import callgraph.InvestigatorFacade;
import callgraph.infrastructure.entities.MethodCallSet;
import callgraph.infrastructure.entities.MethodDecl;
import domain.Class;
import domain.JavaFile;
import domain.MethodOfLibrary;
import domain.Project;
import javaLibraryUtilization.LibUtil;
import utils.Commands;

@Service
public class HelloService {
		
	public static Project project;
    public static List<String> allMethodsCalled = new ArrayList<>();
    public static List<String> allMethodsCalledNew = new ArrayList<>();
    public static List<String> librariesWithProblem = new ArrayList<>();   
    public static Set<MethodCallSet> methodCallSetList =new HashSet<>();
    public static String name;

    public  void testing(String projectURLfromEndpoint) throws IOException{
    	String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");
    	
    	System.out.print("THe name is " + projectName);
    	
    	project=new Project("C:\\Users\\kolid\\eclipse-workspace"); 
    	Commands.FolderForCloneProject(project.getClonePath(),projectURLfromEndpoint);
      
    	String projectPath=project.getClonePath()+"\\project\\"+projectName;
    	
    	System.out.println ("project q13j32k1jck" + projectPath);
        //Commands.method2(projectPath);
    	//Commands.getJarDependenciesForInitParsing(projectPath);  
       // getMethodsCalled();
        
        //to check for duplicate values
      /*  for(String k:allMethodsCalled) {
        	if (!allMethodsCalledNew.contains(k)) {
        		allMethodsCalledNew.add(k.toString());}
        }
        allMethodsCalledNew.forEach(System.out::println);
        
       // List all files of Target 
        Path path = Paths.get(projectPath + "\\target\\dependency");
        try (Stream <Path> subPaths = Files.walk(path,1)) {
        	 List<String> allFiles = subPaths
        			 .map(Objects::toString)
        			 .collect(Collectors.toList());
        	 allFiles.remove(0);
       
        	for (int i =0; i<allFiles.size();i++) {
        		Commands.makeFolder(projectPath+ "\\target\\dependency", allFiles.get(i).toString());		
        		//get all methods of the file
        		LibUtil m = new LibUtil(allFiles.get(i).toString()+"new");
                List<MethodOfLibrary> methodsOfFile= new ArrayList<>();
                methodsOfFile = m.getMethodsOfLibrary();
                System.out.println("Methods of file " + allFiles.get(i).toString()+"new" + methodsOfFile);
                
                // check if it exists in our list of methods
                for(MethodOfLibrary j: methodsOfFile) {
                	for (String k : allMethodsCalledNew){
	                	if( j.toString().contains(k)) {
	                 		//CALLGRAPH
	                		//InvestigatorFacade facade = new InvestigatorFacade(dirOfProject, fileOfMethod, methodDeclaration);
	                		InvestigatorFacade facade = new InvestigatorFacade(allFiles.get(i).toString()+"new",
	                					j.getFilePath(),j.getMethodDeclaration());
	                        Set<MethodCallSet> methodCallSets = facade.start();
	                        
	                        for(MethodCallSet meth: methodCallSets) {
	                        	methodCallSetList.add(meth);
	                        }
	                        printResults(methodCallSets);
	                        break;
                        }
                	}
                 }
                 
                 System.out.print("The callgraph" + methodCallSetList);
        	} 
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
        Commands.deleteProject("C:\\Users\\kolid\\eclipse-workspace");
        */
    }

    private static void getMethodsCalled() {
        //Setup Parser and SymbolSolver
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(Paths.get(project.getClonePath()));
        List<SourceRoot> sourceRoots = projectRoot.getSourceRoots();
        System.out.print(projectRoot.getSourceRoots());
        try {
            createSymbolSolver(project.getClonePath());
        } catch (IllegalStateException e) {
            return;
        }
        if (createFileSet(sourceRoots) == 0) {
           System.err.println("No classes could be identified! Exiting...");
            return;
        }

        //For all files parse with visitor
        sourceRoots
                .forEach(sourceRoot -> {
                    System.out.println("Analysing Source Root: " + sourceRoot.getRoot().toString() );
                    try {
                        List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
                        parseResults
                                .stream()
                                .filter(res -> res.getResult().isPresent())
                                .filter(f -> !f.getResult().get().getStorage().get().getPath().toString().contains(".mvn\\wrapper"))
                                .forEach(res -> {
                                    analyzeCompilationUnit(res.getResult().get());
                                });
                    } catch (Exception ignored) {
                    }
                });
        System.out.println();
    }

    private static void analyzeCompilationUnit(CompilationUnit compilationUnit) {
        VoidVisitor<List<String>> methodCall = new MethodCall();
        methodCall.visit(compilationUnit,allMethodsCalled);
    }

    private static class MethodCall extends VoidVisitorAdapter<List<String>> {
        @Override
        public void visit(MethodCallExpr n, List<String> collector) {
            super.visit(n, collector);
            try {
                collector.add(n.resolve().getQualifiedSignature());
            } catch (Throwable t) {
                System.out.println("Problem with: "+n.getName() +"  ---"+n.resolve().getQualifiedSignature());
            }
        }
    }

    //Create Symbol Solver
    private static void createSymbolSolver(String projectDir) {
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(projectDir));
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(javaParserTypeSolver);
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration
                .setSymbolResolver(symbolSolver)
                .setAttributeComments(false).setDetectOriginalLineSeparator(true);
        StaticJavaParser
                .setConfiguration(parserConfiguration);
    }

    //Find all Java files and add to List
    private static int createFileSet(List<SourceRoot> sourceRoots) {
        try {
            sourceRoots
                    .forEach(sourceRoot -> {
                        try {
                            sourceRoot.tryToParse()
                                    .stream()
                                    .filter(res -> res.getResult().isPresent())
                                    .filter(cu -> cu.getResult().get().getStorage().isPresent())
                                    .filter(f -> !f.getResult().get().getStorage().get().getPath().toString().contains(".mvn\\wrapper"))
                                    .forEach(cu -> {
                                        try {
                                            project.getJavaFiles().add(
                                                    new JavaFile(cu.getResult().get(), cu.getResult().get().getStorage().get().getPath().toString().replace("\\", "/").replace(project.getClonePath(), "").substring(1),
                                                            cu.getResult().get().getStorage().get().getPath().toString().replace("\\", "/"),
                                                            cu.getResult().get().findAll(ClassOrInterfaceDeclaration.class)
                                                                    .stream()
                                                                    .filter(classOrInterfaceDeclaration -> classOrInterfaceDeclaration.getFullyQualifiedName().isPresent())
                                                                    .map(classOrInterfaceDeclaration -> classOrInterfaceDeclaration.getFullyQualifiedName().get())
                                                                    .map(Class::new)
                                                                    .collect(Collectors.toSet())));
                                        } catch (Throwable ignored) {
                                        }
                                    });
                        } catch (Exception ignored) {
                        }
                    });
        } catch (Exception ignored) {
        }
        return project.getJavaFiles().size();
    }

    
    public static void printResults(Set<MethodCallSet> results) {
        for (MethodCallSet methodCallSet : results) {
            System.out.printf("Methods involved with %s method: %s", methodCallSet.getMethod().getQualifiedName(), System.lineSeparator());
            for (MethodDecl methodCall : methodCallSet.getMethodCalls()) {
                System.out.print(methodCall.getFilePath() + " | " + methodCall.getQualifiedName());
                System.out.printf(" | StartLine: %d | EndLine: %d%s", methodCall.getCodeRange().getStartLine(), methodCall.getCodeRange().getEndLine(), System.lineSeparator());
            }
            System.out.println();
        }
    }
}