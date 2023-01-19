package javaLibraryUtilization.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

import static org.apache.logging.log4j.ThreadContext.containsKey;

@Service
public class HelloService {
		
	public static Project project;
    public static List<String> allMethodsCalledByProject = new ArrayList<>();
    public static List<String> allMethodsCalledByProjectNew = new ArrayList<>();
    public static List<String> librariesWithProblem = new ArrayList<>();
    public static List<methodsDetails> methodsDetailsList = new ArrayList<methodsDetails>();
    public static List<ProjectDTO> projectDTOlist=new ArrayList<ProjectDTO>();
    private Library Library;

    public static List<Library> listOfLibrariesPDO = new ArrayList<Library>();
    public static ProjectDTO ProjectDTO;

    public void projectAnalysis(String projectURLfromEndpoint) throws IOException{

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

    	//file project insert the project you want to startAnalysis
        project=new Project("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName);
    	Commands.cloneProject("C:\\Users\\kolid\\eclipse-workspace\\project",projectURLfromEndpoint);

        //mvn clean command
        Commands.methodForMvnCleanCommand(project.getProjectPath());
    	Commands.getJarDependenciesForInitParsing(project.getProjectPath());
        getMethodsCalled();
        
        //to check for duplicate values
        for(String k: allMethodsCalledByProject) {
        	if (!allMethodsCalledByProjectNew.contains(k)) {
        		allMethodsCalledByProjectNew.add(k.toString());}}
        System.out.println("The methods called by the Project" + allMethodsCalledByProjectNew);

       // List all files of Target 
        Path path = Paths.get(project.getProjectPath() + "\\target\\dependency");
        try (Stream <Path> subPaths = Files.walk(path,1)) {
        	 List<String> librariesInProject = subPaths
        			 .map(Objects::toString)
        			 .collect(Collectors.toList());
        	 librariesInProject.remove(0);

            int countForPLMI;
            int countForNUL=0;

        	for (int i =0; i<librariesInProject.size();i++){
                //Get NOM per class
                InvestigatorForNOM investigatorForNOM= new InvestigatorForNOM(project);
                investigatorForNOM.getHashMap().forEach((k,e)-> System.out.println("key: "+k+"    v: "+e));
                //count for LUF
                int paronomastisLUF=1;
                int arithmitisLUF=0;
                //count
                int count=0;
                countForPLMI=0;
        		Commands.makeFolder(project.getProjectPath()+ "\\target\\dependency", librariesInProject.get(i).toString());
        		//get all methods of the file
        		LibUtil m = new LibUtil(librariesInProject.get(i).toString()+"new");
                List<MethodOfLibrary> allMethodsOfLibrary= new ArrayList<>();
                allMethodsOfLibrary = m.getMethodsOfLibrary();
                // check if it exists in our list of methods
            	for (String k : allMethodsCalledByProjectNew){
            		for(MethodOfLibrary j: allMethodsOfLibrary) {
	                	if( j.toString().contains (k)) {

                            countForPLMI=countForPLMI+1;
                            count=1;

	                 		//CALLGRAPH
	                		InvestigatorFacade facade = new InvestigatorFacade(librariesInProject.get(i).toString()+"new",
	                					j.getFilePath(),j.getMethodDeclaration());
	                        Set<MethodCallSet> methodCallSets = facade.start();

                            //Metric LUF
                            String help = j.getQualifiedSignature().replace(j.getQualifiedSignature().substring
                                    (j.getQualifiedSignature().lastIndexOf(".")),"");
                            if(methodCallSets.stream().findFirst().isPresent()){
                                if (InvestigatorForNOM.getHashMap().containsValue(help)){
                                    System.out.println("Hello from inside");
                                    paronomastisLUF = investigatorForNOM.getHashMap().get(help) + paronomastisLUF;
                                    }
                                int methodsCalledFromThiaCallTreeUsed = methodCallSets.stream().findFirst().get().getMethodCalls().size();
                                arithmitisLUF += methodsCalledFromThiaCallTreeUsed;}

                           methodsDetailsList.add(new methodsDetails(1,k,
                                   librariesInProject.get(i).toString()+"new", methodCallSets));
                            printResults(methodCallSets);
                            }
                	}
                 }
                listOfLibrariesPDO.add(Library = new Library(librariesInProject.get(i),
                         (countForPLMI/allMethodsOfLibrary.size() * 100.00),arithmitisLUF/paronomastisLUF * 100.00));

                if (count == 1){
                    countForNUL++;}
               // System.out.println("Arithmitis LUF"+arithmitisLUF);

        	}
            //projectDTOlist.add(new ProjectDTO("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName, methodsDetailsList,
              //      countForNUL));
            //projectDTOlist.add(new ProjectDTO("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName, methodsDetailsList,
           //         countForNUL,listOfLibrariesPDO));
           ProjectDTO=  new ProjectDTO("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName, methodsDetailsList,
                    countForNUL,listOfLibrariesPDO);
        	
		} catch (IOException e) {
			e.printStackTrace();
		}
       
        if (librariesWithProblem.size()!=0) {
        	System.out.println("Libraries with errors: ");
        	librariesWithProblem.forEach(System.out::println);
        }
        Commands.deleteProject("C:\\Users\\kolid\\eclipse-workspace\\project",projectName);
    }

    private static void getMethodsCalled() {
        //Setup Parser and SymbolSolver
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(Paths.get(project.getProjectPath()));
        List<SourceRoot> sourceRoots = projectRoot.getSourceRoots();
        try {
            createSymbolSolver(project.getProjectPath());
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
        methodCall.visit(compilationUnit, allMethodsCalledByProject);
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
                                                    new JavaFile(cu.getResult().get(), cu.getResult().get().getStorage().get().getPath().toString().replace("\\", "/").replace(project.getProjectPath(), "").substring(1),
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