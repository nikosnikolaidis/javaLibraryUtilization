package javaLibraryUtilization.control;

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
import domain.Class;
import domain.JavaFile;
import domain.Project;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InvestigatorForNOM {

    private static HashMap<String,Integer> hashMap=new HashMap<>();

    public InvestigatorForNOM(Project project){

        //Setup Parser and SymbolSolver
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy().collect(Paths.get(project.getProjectPath()));
        List<SourceRoot> sourceRoots = projectRoot.getSourceRoots();
        try {
            createSymbolSolver(project.getProjectPath());
        } catch (IllegalStateException e) {
            return;
        }
        if (createFileSet(sourceRoots, project) == 0) {
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

    public static HashMap<String, Integer> getHashMap() {
        return hashMap;
    }

    private static void analyzeCompilationUnit(CompilationUnit compilationUnit) {
        VoidVisitor<HashMap<String, Integer>> methodCall = new InvestigatorForNOM.MethodCall();
        methodCall.visit(compilationUnit, hashMap);
    }
    private static class MethodCall extends VoidVisitorAdapter<HashMap<String,Integer>> {
        @Override
        public void visit(ClassOrInterfaceDeclaration javaClass, HashMap<String,Integer> collector) {
            super.visit(javaClass, collector);
            try {
                if (javaClass.getFullyQualifiedName().isPresent()) {
                    collector.put(javaClass.getFullyQualifiedName().get(), javaClass.getMethods().size());
                }
            } catch (Throwable t) {
                System.out.println("Problem with: "+javaClass.getName() );
            }
        }
    }

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

    private static int createFileSet(List<SourceRoot> sourceRoots, Project project) {
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
}
