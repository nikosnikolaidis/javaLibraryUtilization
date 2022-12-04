package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Commands {

    //Get all the dependencies in order to parse all the project
    public static void getJarDependenciesForInitParsing(String projectPath) throws IOException {
        //For Linux
        if(!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; mvn dependency:copy-dependencies");
            File err2 = new File("err2.txt");
            pbuilder2.redirectError(err2);
            Process p2 = pbuilder2.start();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String line2; 
            while ((line2 = reader2.readLine()) != null) {
               System.out.println(line2);
            }
            BufferedReader reader3 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
            while ((line2 = reader3.readLine()) != null) {
               System.out.println(line2);
            }
        } 
    
        //For Windows
        else {
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && "+
                    "mvn dependency:copy-dependencies -Dclassifier=sources -Dverbose" + "\"");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            String line1;
            while ((line1 = reader1.readLine()) != null) {
                System.out.println(line1);
            }
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
            String line2;
            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
            }
        }
        System.out.println();
    }
    public static void method2(String projectPath) throws IOException {
    	//for windows
    	 Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && "+
                 "mvn clean" + "\"");
         BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
         String line1;
         while ((line1 = reader1.readLine()) != null) {
             System.out.println(line1);
         }
         BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
         String line2;
         while ((line2 = reader2.readLine()) != null) {
             System.out.println(line2);
         }
    }
    public static void unJar(String projectPath,String fileName) throws IOException {
    	//for windows
    	Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && "+
                "jar xf " + fileName);
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            System.out.println(line1);
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            System.out.println(line2);
            //Main.librariesWithProblem.add(projectPath);
        }
    }
    
    public static void makeFolder(String projectPath,String fileName) throws IOException {
    	//for windows
    	Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && "+
                "mkdir " + fileName + "new\\src\\main");
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            System.out.println(line1);
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            System.out.println(line2);	
        }	
        projectPath =  fileName +"new\\src\\main";
        Commands.unJar(projectPath, fileName);
    }
    public static void FolderForCloneProject(String projectPath,String gitUrl) throws IOException {
    	//for windows
    	Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && "+
                "mkdir " + "project");
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            System.out.println(line1);
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            System.out.println(line2);	
        }
        Commands.cloneProject(projectPath,gitUrl);
    }
    public static void cloneProject(String projectPath,String gitUrl) throws IOException {
    	//for windows
    	Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + "\\project" + " && "+
                "git clone " + gitUrl);
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            System.out.println(line1);
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            System.out.println(line2);	
        }	
    }
    
    public static void deleteProject(String projectPath) throws IOException {
    	//for windows
    	Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath  + " && "+
                "rmdir /s /q " + projectPath+"\\project");
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
        String line1;
        while ((line1 = reader1.readLine()) != null) {
            System.out.println(line1);
        }
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
        String line2;
        while ((line2 = reader2.readLine()) != null) {
            System.out.println(line2);	
        }	
    }
    

}