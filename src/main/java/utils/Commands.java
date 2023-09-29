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
                    "; mvn dependency:copy-dependencies -Dclassifier=sources -Dverbose");
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
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath+ " && "+
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
    public static void methodForMvnCleanCommand(String projectPath) throws IOException {
        //For Linux
        if(!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; mvn clean");
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
        else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
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
    }
    public static void unJar(String projectPath,String fileName) throws IOException {
        //For Linux
        if(!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; jar xf " + fileName);
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
        else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
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
            }
        }
    }
    
    public static void makeFolder(String projectPath,String fileName) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; mkdir -p "+fileName+"new/src/main");
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
            projectPath = fileName + "new/src/main";
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
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
            projectPath = fileName + "new\\src\\main";
        }
        Commands.unJar(projectPath, fileName);
    }
    public static void cloneProject(String projectPath,String gitUrl) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; git clone " + gitUrl);
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
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
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
    }
    public static String revParse(String projectPath,String sha) throws IOException {
        //For LinuxMVN
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; git rev-parse HEAD");
            File err2 = new File("err2.txt");
            pbuilder2.redirectError(err2);
            Process p2 = pbuilder2.start();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String line2;
            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
                sha =line2;
            }
            BufferedReader reader3 = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
            while ((line2 = reader3.readLine()) != null) {
                System.out.println(line2);
            }
        } else {
            //for windows
            System.out.println("project path"+projectPath);
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
                    "git rev-parse HEAD");
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            String line1;
            while ((line1 = reader1.readLine()) != null) {
                System.out.println(line1);
                sha = line1;
            }

            BufferedReader reader2 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
            String line2;
            while ((line2 = reader2.readLine()) != null) {
                System.out.println(line2);
            }
        }
            return sha;
    }
    
    public static void deleteProject(String projectPath,String projectName) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +"/project"+
                    "; rm -rf " + projectName);
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
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath +"\\project"+ " && " +
                    "rmdir /s /q " + projectName);
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
    public static void makeFolderForProject(String projectPath,String projectFromEndPoint) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; mkdir project");
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
            projectPath=projectPath+"/project";
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
                    "mkdir project");
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
            projectPath=projectPath+"\\project";
        }
        cloneProject(projectPath,projectFromEndPoint);
    }
    public static void checkoutSha(String projectPath,String sha) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; git checkout " + sha);
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
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
                    "git checkout " + sha);
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
    public static void mvnPackage(String projectPath) throws IOException {
        //For Linux
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            ProcessBuilder pbuilder2 = new ProcessBuilder("bash", "-c", "cd " + projectPath +
                    "; mvn package -DskipTests");
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
        } else {
            //for windows
            Process proc1 = Runtime.getRuntime().exec("cmd /c \"cd " + projectPath + " && " +
                    "mvn package -DskipTests");
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
}