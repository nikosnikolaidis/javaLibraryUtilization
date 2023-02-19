package javaLibraryUtilization.control;

import callgraph.InvestigatorFacade;
import callgraph.infrastructure.entities.MethodCallSet;
import callgraph.infrastructure.entities.MethodDecl;
import domain.MethodOfLibrary;
import domain.Project;
import javaLibraryUtilization.LibUtil;
import utils.Commands;
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

import static javaLibraryUtilization.control.HelloService.home;
import static javaLibraryUtilization.control.MethodsGetter.getMethodsCalled;

public class StartAnalysis {

    public static Project project;
    public static List<String> allMethodsCalledByProject = new ArrayList<>();
    public static List<String> allMethodsCalledByProjectNew = new ArrayList<>();
    public static List<MethodsDetails> methodsDetailsList = new ArrayList<MethodsDetails>();
    private Library Library;
    public static List<Library> listOfLibrariesPDO = new ArrayList<Library>();
    public static ProjectDTO ProjectDTO;
    public static InvestigatorForNOM investigatorForNOM;
    public static List<String> classList = new ArrayList<>();
    public static MethodsGetter methodsGetter;
    public static List<String> testArray = new ArrayList<>();
    public static List<String> testArrayNew = new ArrayList<>();


    public void startAnalysisOfEach(Project project,String projectName) throws IOException {


        Commands.methodForMvnCleanCommand(project.getProjectPath());
        Commands.getJarDependenciesForInitParsing(project.getProjectPath());

        methodsGetter = new MethodsGetter(project.getProjectPath());
        getMethodsCalled();
        System.out.println(allMethodsCalledByProject);

        //to check for duplicate values
        for (String k : allMethodsCalledByProject) {
            if (!allMethodsCalledByProjectNew.contains(k)) {
                allMethodsCalledByProjectNew.add(k.toString());
            }
        }

        methodsDetailsList.clear();
        listOfLibrariesPDO.clear();

        // List all files of Target
        Path path = Paths.get(project.getProjectPath() + "\\target\\dependency");
        try (
                Stream<Path> subPaths = Files.walk(path, 1)) {
            List<String> librariesInProject = subPaths
                    .map(Objects::toString)
                    .collect(Collectors.toList());
            librariesInProject.remove(0);

            int countForNUL = 0;
            for (int i = 0; i < librariesInProject.size(); i++) {

                //paronomastisPUC number of all Classes of this Library
                classList.clear();
                int paronomastisPUC = 1;
                int paronomastisLUF = 0;
                int numberOfUsedMethods = 0;
                int arithmitisLUF = 0;
                //count used for NUL - Number of Used Libraries
                int count = 0;
                int paronomastisPUMC = 0;

                Commands.makeFolder(project.getProjectPath() + "\\target\\dependency", librariesInProject.get(i).toString());
                //get all methods of the file
                LibUtil m = new LibUtil(librariesInProject.get(i).toString() + "new");
                List<MethodOfLibrary> allMethodsOfLibrary = new ArrayList<>();
                allMethodsOfLibrary = m.getMethodsOfLibrary();

                investigatorForNOM = new InvestigatorForNOM(librariesInProject.get(i).toString() + "new");
                investigatorForNOM.getHashMap().forEach((k, e) -> System.out.println("key: " + k + "    v: " + e));
                paronomastisPUC = investigatorForNOM.getHashMap().size();

                testArray.clear();
                testArrayNew.clear();

                // check if it exists in our list of methods
                for (String meth : allMethodsCalledByProjectNew) {
                    for (MethodOfLibrary j : allMethodsOfLibrary) {
                        if (j.toString().contains(meth)) {

                            //count used for NUL - Number of Used Libraries
                            count = 1;
                            numberOfUsedMethods++;

                            //CALLGRAPH
                            InvestigatorFacade facade = new InvestigatorFacade(librariesInProject.get(i).toString() + "new",
                                    j.getFilePath(), j.getMethodDeclaration());
                            Set<MethodCallSet> methodCallSets = facade.start();

                            if (investigatorForNOM.getHashMap().containsKey(getClassName(j.getQualifiedSignature()))) {
                                paronomastisLUF = paronomastisLUF + investigatorForNOM.getHashMap().get(getClassName(j.getQualifiedSignature()));
                            }

                            if (!(methodCallSets.size() == 0)) {
                                for (MethodDecl element : methodCallSets.stream().findFirst().get().getMethodCalls()) {
                                    String temp = getClassName(element.getQualifiedName());
                                }
                                for (String str : classList) {
                                    if (investigatorForNOM.getHashMap().containsKey(str)) {
                                        paronomastisLUF = paronomastisLUF + investigatorForNOM.getHashMap().get(str);
                                        methodsGetter = new MethodsGetter(str);
                                        paronomastisPUMC += allMethodsCalledByProject.size();
                                    }
                                }
                            }

                            //not perfect
                            if (methodCallSets.stream().findFirst().isPresent()) {
                                for (MethodDecl md : methodCallSets.stream().findFirst().get().getMethodCalls()) {
                                    if (!testArray.contains(md.qualifiedName)) {
                                        testArray.add(md.getQualifiedName());
                                    }
                                }
                            }

                            methodsDetailsList.add(new MethodsDetails(1, meth,
                                    librariesInProject.get(i) + "new", methodCallSets));
                            printResults(methodCallSets);
                        }
                    }
                }

                arithmitisLUF = testArray.size();

                if (paronomastisLUF == 0) {
                    paronomastisLUF = 1;
                }
                if (paronomastisPUMC == 0) {
                    paronomastisPUMC = 1;
                }

                //PUMC
                //ο αριθμητής να είναι ο αριθμός των μεθόδων που χρησιμοποιήθηκαν από τις κλάσεις
                //προς τον αριθμό των public μεθόδων των συγκεκριμένων κλάσεων - getMethodsCalled?

                listOfLibrariesPDO.add(Library = new Library(librariesInProject.get(i),
                        ((arithmitisLUF * 1.0) / paronomastisLUF) * 100,
                        ((classList.size() * 1.0) / paronomastisPUC) * 100,
                        ((numberOfUsedMethods * 1.0) / paronomastisPUMC) * 100));

                //count used for NUL - Number of Used Libraries
                if (count == 1) {
                    countForNUL++;
                }
            }
            //System.gitProperty("user.dir")
            ProjectDTO = new ProjectDTO( home + "\\project\\" + projectName, methodsDetailsList,
                    countForNUL, listOfLibrariesPDO);

        } catch (
                IOException e) {
            e.printStackTrace();
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