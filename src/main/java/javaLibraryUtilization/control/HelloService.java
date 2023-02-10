package javaLibraryUtilization.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import callgraph.InvestigatorFacade;
import callgraph.infrastructure.entities.MethodCallSet;
import callgraph.infrastructure.entities.MethodDecl;
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
    public static List<MethodsDetails> methodsDetailsList = new ArrayList<MethodsDetails>();
    public static List<ProjectDTO> projectDTOlist=new ArrayList<ProjectDTO>();
    private Library Library;
    public static List<Library> listOfLibrariesPDO = new ArrayList<Library>();
    public static ProjectDTO ProjectDTO;
    public static InvestigatorForNOM investigatorForNOM;
    public static List<String> classList = new ArrayList<>();
    public static MethodsGetter methodsGetter;
    public void projectAnalysis(String projectURLfromEndpoint) throws IOException {

        String projectName= projectURLfromEndpoint.split("/")[projectURLfromEndpoint.split("/").length-1].replace(".git", "");

    	//file project insert the project you want to startAnalysis
        project=new Project("C:\\Users\\kolid\\eclipse-workspace\\project\\" + projectName);
    	Commands.cloneProject("C:\\Users\\kolid\\eclipse-workspace\\project", projectURLfromEndpoint);

        //mvn clean command
        Commands.methodForMvnCleanCommand(project.getProjectPath());
    	Commands.getJarDependenciesForInitParsing(project.getProjectPath());

        //getMethodsCalled(project);
        methodsGetter = new MethodsGetter (project.getProjectPath());
        methodsGetter.getMethodsCalled();
        System.out.println(allMethodsCalledByProject);
        
        //to check for duplicate values
        for(String k: allMethodsCalledByProject) {
        	if (!allMethodsCalledByProjectNew.contains(k)) {
        		allMethodsCalledByProjectNew.add(k.toString());
            }
        }
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

        	for (int i =0; i<librariesInProject.size();i++) {

                //paronomastisPUC number of all Classes of this Library
                classList.clear();
                int paronomastisPUC=1;
                int paronomastisLUF=0;
                int numberOfUsedMethods=0;
                int arithmitisLUF=0;
                int paronomastis=0;
                //count used for NUL - Number of Used Libraries
                int count=0;
                int paronomastisPUMC=0;

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

                            if (investigatorForNOM.getHashMap().containsKey(getClassName(j.getQualifiedSignature()))) {
                                paronomastis = investigatorForNOM.getHashMap().get(getClassName(j.getQualifiedSignature()));
                                paronomastisLUF = paronomastisLUF + paronomastis;
                            }

                            //Not working properly
                            if (!(methodCallSets.size() == 0)) {
                                for (MethodDecl element : methodCallSets.stream().findFirst().get().getMethodCalls()) {
                                    String temp = getClassName(element.getQualifiedName());
                                }
                                for (String str: classList) {
                                    if (investigatorForNOM.getHashMap().containsKey(str)) {
                                        paronomastis = investigatorForNOM.getHashMap().get(str);
                                        paronomastisLUF = paronomastisLUF + paronomastis;
                                        methodsGetter = new MethodsGetter(str);
                                        paronomastisPUMC = paronomastisPUMC + allMethodsCalledByProject.size();
                                    }
                                }
                            }

                            if (methodCallSets.stream().findFirst().isPresent()) {
                                int methodsCalledFromThiaCallTreeUsed = methodCallSets.stream().findFirst().get().getMethodCalls().size();
                                arithmitisLUF += methodsCalledFromThiaCallTreeUsed;
                            }

                           methodsDetailsList.add(new MethodsDetails(1,meth,
                                   librariesInProject.get(i) +"new", methodCallSets));
                            printResults(methodCallSets);

                        }
                	}
                 }

                if (paronomastisLUF == 0) {
                    paronomastisLUF = 1;}

                //PUMC
                //ο αριθμητής να είναι ο αριθμός των μεθόδων που χρησιμοποιήθηκαν από τις κλάσεις
                //προς τον αριθμό των public μεθόδων των συγκεκριμένων κλάσεων - getMethodsCalled?

                listOfLibrariesPDO.add(Library = new Library(librariesInProject.get(i),
                        (arithmitisLUF * 1.0) / paronomastisLUF,
                        (classList.size() * 1.0) / paronomastisPUC,
                        (numberOfUsedMethods * 1.0) / paronomastisPUMC));

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
    public static String getClassName(String methodNameForGetClass) {
        //takes the qualified signature except the name of method in the end
        String temp="";

        //methodNameForGetClass.getQualifiedSignature()
        temp = methodNameForGetClass.replaceAll("\\(.*\\)", "");
        temp = temp.replace(temp.substring
                    (temp.lastIndexOf(".")),"");
       if (!classList.contains(temp)) {
            classList.add(temp);
       }
        return  temp;
    }
}