package javaLibraryUtilization;

import callgraph.InvestigatorFacade;
import callgraph.infrastructure.entities.MethodCallSet;
import callgraph.infrastructure.entities.MethodDecl;
import domain.MethodOfLibrary;
import domain.Project;
import javaLibraryUtilization.models.*;
import utils.Commands;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javaLibraryUtilization.MethodsGetter.getMethodsCalled;

public class StartAnalysis {

    public static Project project;
    public static List<String> allMethodsCalledByProject = new ArrayList<>();
    public static List<String> allMethodsCalledByProjectNew = new ArrayList<>();
    public static InvestigatorForNOM investigatorForNOM;
    public static List<String> classList = new ArrayList<>();
    public static MethodsGetter methodsGetter;
    public static List<String> testArray = new ArrayList<>();
    public static List<String> testArrayNew = new ArrayList<>();
    public static List<String> listForAllTheDirectClasses = new ArrayList<>();
    public List<LibraryDTO> listOfLibrariesPDO = new ArrayList<>();

    public ProjectModuleDTO startAnalysisOfEach(String s, String projectName, String sha) throws IOException {

        allMethodsCalledByProjectNew.clear();
        allMethodsCalledByProject.clear();

        project = new Project(s);
        Commands.methodForMvnCleanCommand(project.getProjectPath());
        Commands.getJarDependenciesForInitParsing(project.getProjectPath());

        methodsGetter = new MethodsGetter(project.getProjectPath());
        try{
            getMethodsCalled();
        }
        catch (StackOverflowError e) {
            System.err.println("This version couldnt be analyzed!");
            e.printStackTrace();
        }

        System.out.println(allMethodsCalledByProject);

        //to check for duplicate values
        for (String k : allMethodsCalledByProject) {
            if (!allMethodsCalledByProjectNew.contains(k)) {
                allMethodsCalledByProjectNew.add(k.toString());
            }
        }
        listOfLibrariesPDO.clear();

        // List all files of Target
        Path path;
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            path= Paths.get(project.getProjectPath() + "/target/dependency");
        }
        else {
            path= Paths.get(project.getProjectPath() + "\\target\\dependency");
        }
        try (Stream<Path> subPaths = Files.walk(path, 1)) {
                List<String> librariesInProject = subPaths
                    .map(Objects::toString)
                    .collect(Collectors.toList());
                System.out.println(librariesInProject);
            librariesInProject.remove(0);

            int countForNUL = 0;

            for (String value : librariesInProject) {

                List<MethodDetailsDTO> methodDetailsDTOList = new ArrayList<>();
                classList.clear();
                listForAllTheDirectClasses.clear();

                //paronomastisPUCD number of all Classes of this Library
                int paronomastisPUCD = 1;
                int paronomastisLIUF = 0;
                int numberOfUsedMethods = 0;
                int arithmitisLIUF = 0;
                //count used for NUL - Number of Used Libraries
                int count = 0;
                int paronomastisLDUF = 0;

                if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                    Commands.makeFolder(project.getProjectPath() + "/target/dependency", value);
                }
                else {
                    Commands.makeFolder(project.getProjectPath() + "\\target\\dependency", value);
                }

                //get all methods of the file
                LibUtil m = new LibUtil(value+"new");
                List<MethodOfLibrary> allMethodsOfLibrary = new ArrayList<>();
                allMethodsOfLibrary = m.getMethodsOfLibrary();

                investigatorForNOM = new InvestigatorForNOM(value + "new");
                investigatorForNOM.getHashMap().forEach((k, e) -> System.out.println("key: " + k + "    v: " + e));
                paronomastisPUCD = investigatorForNOM.getHashMap().size();

                testArray.clear();
                testArrayNew.clear();

                // check if it exists in our list of methods
                for (String meth : allMethodsCalledByProjectNew) {

                    for (MethodOfLibrary j : allMethodsOfLibrary) {

                        if (j.toString().contains(meth)) {

                            //calculate arithmiti PUCD (WITHOUT tracing)
                            String temp1 = j.getQualifiedSignature().toString().replaceAll("\\(.*\\)", "");
                            temp1 = temp1.replace(temp1.substring
                                    (temp1.lastIndexOf(".")),"");
                            if (!listForAllTheDirectClasses.contains(temp1)) {
                                listForAllTheDirectClasses.add(temp1);
                            }

                            //count used for NUL - Number of Used Libraries
                            count = 1;
                            numberOfUsedMethods++;

                            //CALLGRAPH
                            InvestigatorFacade facade = new InvestigatorFacade(value.toString() + "new",
                                    j.getFilePath(), j.getMethodDeclaration());
                            Set<MethodCallSet> methodCallSets = facade.start();

                            if (investigatorForNOM.getHashMap().containsKey(getClassName(j.getQualifiedSignature()))) {
                                paronomastisLIUF = paronomastisLIUF + investigatorForNOM.getHashMap().get(getClassName(j.getQualifiedSignature()));
                            }

                            if (!(methodCallSets.size() == 0)) {
                                for (MethodDecl element : methodCallSets.stream().findFirst().get().getMethodCalls()) {
                                    String temp = getClassName(element.getQualifiedName());
                                }

                                for (String str : classList) {
                                    if (investigatorForNOM.getHashMap().containsKey(str)) {
                                        paronomastisLIUF = paronomastisLIUF + investigatorForNOM.getHashMap().get(str);
                                        methodsGetter = new MethodsGetter(str);
                                        paronomastisLDUF += allMethodsCalledByProject.size();
                                    }
                                }
                            }

                            if (methodCallSets.stream().findFirst().isPresent()) {

                                List <CallDTO> callDTOList = new ArrayList<>();

                                for ( MethodCallSet element : methodCallSets) {
                                    for (MethodDecl el : element.getMethodCalls()) {

                                        callDTOList.add(new CallDTO(
                                                el.getFilePath().toString(),
                                                el.getPackageName().toString(),
                                                el.getQualifiedName().toString(),
                                                el.getPreviousMethodString().toString()));
                                    }
                                }

                                methodDetailsDTOList.add(new MethodDetailsDTO(methodCallSets.
                                        stream().findFirst().get().getMethodDeclaration().qualifiedName, callDTOList));

                                for (MethodDecl md : methodCallSets.stream().findFirst().get().getMethodCalls()) {
                                    if (!testArray.contains(md.qualifiedName)) {
                                        testArray.add(md.getQualifiedName());
                                    }
                                }
                            }
                            printResults(methodCallSets);
                        }
                    }
                }

                arithmitisLIUF = testArray.size();

                if (paronomastisLIUF == 0) {
                    paronomastisLIUF = 1;
                }
                if (paronomastisLDUF == 0) {
                    paronomastisLDUF = 1;
                }

                //LDUF
                //ο αριθμητής να είναι ο αριθμός των μεθόδων που χρησιμοποιήθηκαν από τις κλάσεις
                //προς τον αριθμό των public μεθόδων των συγκεκριμένων κλάσεων - getMethodsCalled?

                    listOfLibrariesPDO.add(new LibraryDTO(value,
                            ((listForAllTheDirectClasses.size() * 1.0) / paronomastisPUCD) * 100,
                            ((classList.size() * 1.0) / paronomastisPUCD) * 100,
                            ((numberOfUsedMethods * 1.0) / paronomastisLDUF) * 100,
                            ((arithmitisLIUF * 1.0) / paronomastisLIUF) * 100,
                            methodDetailsDTOList));

                //count used for NUL - Number of Used Libraries
                if (count == 1) {
                    countForNUL++;
                }
            }

            ProjectModuleDTO projectModuleDTO = new ProjectModuleDTO(projectName,countForNUL,listOfLibrariesPDO);

            return projectModuleDTO;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
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

    //not getting the dependency of dependency ...
    public static String getOnlyDependenciesOfAnalysedProject(String nameOfDependency) {

        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            nameOfDependency = nameOfDependency.replace(nameOfDependency.substring(0, nameOfDependency.lastIndexOf(('/'))), "");
        }
        else {
            nameOfDependency = nameOfDependency.replace(nameOfDependency.substring(0, nameOfDependency.lastIndexOf(('\\'))), "");
        }
        String[] parts = nameOfDependency.substring(1).split("-");
        String temp = String.join(",",parts);
        temp = temp.replace(",","");

        return "maria test";
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