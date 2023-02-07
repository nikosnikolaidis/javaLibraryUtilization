package javaLibraryUtilization.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
    public static List<String> allMethodsCalledByProject = new ArrayList<>();
    public static List<String> allMethodsCalledByProjectNew = new ArrayList<>();
    public static List<String> librariesWithProblem = new ArrayList<>();
    public static List<methodsDetails> methodsDetailsList = new ArrayList<methodsDetails>();
    public static List<ProjectDTO> projectDTOlist=new ArrayList<ProjectDTO>();
    private Library Library;
    public static List<Library> listOfLibrariesPDO = new ArrayList<Library>();
    public static ProjectDTO ProjectDTO;
    public static InvestigatorForNOM investigatorForNOM;
    public static List<String> classList = new ArrayList<>();
    public static List<String> classListNew = new ArrayList<>();
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

        methodsDetailsList.clear();
        listOfLibrariesPDO.clear();

       // List all files of Target 
        Path path = Paths.get(project.getProjectPath() + "\\target\\dependency");
        try (Stream <Path> subPaths = Files.walk(path,1)) {
        	 List<String> librariesInProject = subPaths
        			 .map(Objects::toString)
        			 .collect(Collectors.toList());
        	 librariesInProject.remove(0);

            int countForNUL=0;

            //need all the used classes of a library (size of a table)
            //need all the classes of a library (size of ?)

        	for (int i =0; i<librariesInProject.size();i++){

                //paronomastisPUC number of all Classes of this Library
                int paronomastisPUC=1;

                int paronomastisLUF=0;

                int numberOfUsedMethods=0;
                int arithmitisLUF=0;
                int paronomastis=0;
                //count used for NUL - Number of Used Libraries
                int count=0;

        		Commands.makeFolder(project.getProjectPath()+ "\\target\\dependency", librariesInProject.get(i).toString());
        		//get all methods of the file
        		LibUtil m = new LibUtil(librariesInProject.get(i).toString()+"new");
                List<MethodOfLibrary> allMethodsOfLibrary= new ArrayList<>();
                allMethodsOfLibrary = m.getMethodsOfLibrary();

                investigatorForNOM = new InvestigatorForNOM(librariesInProject.get(i).toString()+"new");
                investigatorForNOM.getHashMap().forEach((k, e) -> System.out.println("key: " + k + "    v: " + e));
                paronomastisPUC=investigatorForNOM.getHashMap().size();

                // check if it exists in our list of methods
            	for (String meth : allMethodsCalledByProjectNew) {
            		for (MethodOfLibrary j: allMethodsOfLibrary) {
	                	if (j.toString().contains(meth)) {
                            //count used for NUL - Number of Used Libraries
                            count=1;
                            numberOfUsedMethods++;

	                 		//CALLGRAPH
	                		InvestigatorFacade facade = new InvestigatorFacade(librariesInProject.get(i).toString()+"new",
	                					j.getFilePath(),j.getMethodDeclaration());
	                        Set<MethodCallSet> methodCallSets = facade.start();

                            if (investigatorForNOM.getHashMap().containsKey(getClassName(j.getQualifiedSignature()))){
                                paronomastis = investigatorForNOM.getHashMap().get(getClassName(j.getQualifiedSignature()));
                                paronomastisLUF = paronomastisLUF + paronomastis;}

                            //Not working properly
                            if (!(methodCallSets.size() == 0)) {
                                System.out.println("Inside");
                                for (MethodCallSet element : methodCallSets) {
                                    element.getMethodDeclaration().getQualifiedName();
                                    String temp = getClassName(element.getMethodDeclaration().getQualifiedName());


                                    if (!classListNew.contains(temp)){
                                        classListNew.add(temp);}
                                    else
                                        System.out.println("Already exists in classListNew");
                                    for (int q=0;q<classListNew.size();q++){
                                        if (investigatorForNOM.getHashMap().containsKey(q)) {
                                            paronomastis = investigatorForNOM.getHashMap().get(q);
                                            paronomastisLUF = paronomastisLUF + paronomastis;}
                                    }
                                }
                            }


                            if (methodCallSets.stream().findFirst().isPresent()){
                                int methodsCalledFromThiaCallTreeUsed = methodCallSets.stream().findFirst().get().getMethodCalls().size();
                                arithmitisLUF += methodsCalledFromThiaCallTreeUsed;}

                           methodsDetailsList.add(new methodsDetails(1,meth,
                                   librariesInProject.get(i) +"new", methodCallSets));
                            printResults(methodCallSets);}
                	}
                 }

                if (paronomastisLUF == 0) {
                    paronomastisLUF = 1;}

                //PUMC
                //ο αριθμητής να είναι ο αριθμός των μεθόδων που χρησιμοποιήθηκαν από τις κλάσεις
                //προς τον αριθμό των public μεθόδων των συγκεκριμένων κλάσεων - getMethodsCalled?

                System.out.println("Arithmitis LUF " + arithmitisLUF);
                System.out.println("paronomastis LUF " + paronomastisLUF);
                listOfLibrariesPDO.add(Library = new Library(librariesInProject.get(i),
                        arithmitisLUF / paronomastisLUF * 1.0,
                        classList.size() * 1.0 / paronomastisPUC,
                        1.0 * numberOfUsedMethods ));

                //count used for NUL - Number of Used Libraries
                if (count == 1){
                    countForNUL++;}

        	}
           ProjectDTO = new ProjectDTO("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName, methodsDetailsList,
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
    public static String getClassName(String methodNameForGetClass){
        //takes the qualified signature except the name of method in the end
        String temp="";
        if (methodNameForGetClass.contains("(")){
            //methodNameForGetClass.getQualifiedSignature()
            temp = methodNameForGetClass.replaceAll("\\(.*\\)", "");
            temp = temp.replace(temp.substring
                    (temp.lastIndexOf(".")),"");}
        if (!classList.contains(temp)) {
            classList.add(temp);
        }
        return  temp;
    }
}