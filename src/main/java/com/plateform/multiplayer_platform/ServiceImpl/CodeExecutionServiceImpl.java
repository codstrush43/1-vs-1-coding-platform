package com.plateform.multiplayer_platform.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Entity.TestCase;
import com.plateform.multiplayer_platform.Enum.SubmissionStatus;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;
import com.plateform.multiplayer_platform.Service.TestCaseService;

@Service 
public class CodeExecutionServiceImpl implements CodeExecutionService{

    @Autowired 
    private TestCaseService testCaseService;


    @Override 
    public SubmissionStatus execute(Submission submission) throws Exception{
            
        String compilingContainerName="";
        String runningContainerName="";
        String activeContainerName="";
        String fileName="";
        String imageName="";
        String compilingCommand="";
        String runningCommand="";
        Path tempDir = Files.createTempDirectory("executor");

        switch(submission.getLanguage())
        {
            case("cpp") : compilingContainerName="cpp-compiler"+UUID.randomUUID();
                          runningContainerName="cpp-runner"+UUID.randomUUID();
                          imageName="cpp-executor";
                          fileName="main.cpp";
                          compilingCommand="g++ /app/main.cpp -o /app/main";
                          runningCommand="/app/main < /app/input.txt";
                          break;

            case("java") :  compilingContainerName="java-compiler"+UUID.randomUUID();
                            runningContainerName="java-runner"+UUID.randomUUID();
                            imageName="java-executor";
                            fileName="Main.java";
                            compilingCommand = "javac -d /app /app/Main.java";
                            runningCommand = "java -cp /app Main < /app/input.txt";
                            break;

            case("python") : compilingContainerName="python-compiler"+UUID.randomUUID();
                            runningContainerName="python-runner"+UUID.randomUUID();
                            imageName="python-executor";
                            fileName="main.py";
                            compilingCommand =null;
                            runningCommand = "python3 /app/main.py < /app/input.txt";
                            break;
            
            case("javascript") :compilingContainerName = "js-compiler" + UUID.randomUUID();
                                runningContainerName = "js-runner" + UUID.randomUUID();
                                imageName = "java-script-executor";
                                fileName = "main.js";
                                compilingCommand = null;
                                runningCommand = "node /app/main.js < /app/input.txt";
                                break;
            default : throw new Exception("select appropriate language!");
        }

        Path sourceFile=tempDir.resolve(fileName);
        activeContainerName=null;
        try{
        Files.writeString(sourceFile,submission.getCode());
        
        List<TestCase> testCases=testCaseService.findByProblemId(submission.getProblem().getId());
        activeContainerName=compilingContainerName;
        if(compilingCommand!=null)
        {
            ProcessBuilder compileProcessBuilder = new ProcessBuilder(
                "docker",
                "run",
                "--name",compilingContainerName,
                "--rm",
                "-v",
                tempDir.toAbsolutePath() + ":/app",
                imageName,
                "bash",
                "-c",
                compilingCommand
            );

            Process compileProcess = compileProcessBuilder.start();

            Boolean compileExitCode = compileProcess.waitFor(10,TimeUnit.SECONDS);
            if(compileExitCode)
            {
                activeContainerName=null;
            }
            if(!compileExitCode)
            {
                try{
                    Process killProcess = new ProcessBuilder(
                        "docker","kill",compilingContainerName
                    ).start();
                    if(!killProcess.waitFor(3,TimeUnit.SECONDS))
                    {
                        killProcess.destroyForcibly();
                    }
                }
                finally{
                    compileProcess.destroyForcibly();
                }
                compileProcess.waitFor();
                return SubmissionStatus.COMPILATION_TIME_LIMIT_EXCEEDED;
            }

            if(compileProcess.exitValue()!=0)
            {
                String error=new String(compileProcess.getErrorStream().readAllBytes());
                return SubmissionStatus.COMPILATION_ERROR;
            }
        }
        
        for(TestCase testCase : testCases)
        {
            Path inputFile = tempDir.resolve("input.txt");
            Files.writeString(inputFile,testCase.getInput());
            activeContainerName=runningContainerName;

            ProcessBuilder processBuilder=new ProcessBuilder(
                "docker",
                "run",
                "--name",runningContainerName,
                "--rm",
                "-v",
                tempDir.toAbsolutePath()+":/app",
                imageName,
                "bash",
                "-c",
                runningCommand
            );

            Process process = processBuilder.start();

            boolean exitcode=process.waitFor(2,TimeUnit.SECONDS);
            if(!exitcode)
            {
                try
                {
                    Process killProcess = new ProcessBuilder(
                        "docker","kill",runningContainerName
                    ).start();
                    if(!killProcess.waitFor(3,TimeUnit.SECONDS))
                    {
                        killProcess.destroyForcibly();
                    }
                }
                finally{
                    process.destroyForcibly();
                }
                process.waitFor();
                return SubmissionStatus.TIME_LIMIT_EXCEEDED;
            }
            
            if(process.exitValue()!=0)
            {
                String error = new String(process.getErrorStream().readAllBytes());
            return SubmissionStatus.RUNTIME_ERROR;
            }

            String output=new String(process.getInputStream().readAllBytes());

            System.out.println(output+"/"+testCase.getExpectedOutput());

            if(!output.trim().equals(testCase.getExpectedOutput().trim()))
            {
                return SubmissionStatus.WRONG_ANSWER;
            }
            activeContainerName=null;
        }
            return SubmissionStatus.ACCEPTED;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return SubmissionStatus.INTERNAL_ERROR;
        }
        finally{

            if(activeContainerName!=null)
            {
                try{
                Process killProcess = new ProcessBuilder(
                    "docker","kill",activeContainerName
                ).start();
                if(!killProcess.waitFor(3,TimeUnit.SECONDS))
                {
                    killProcess.destroyForcibly();
                    killProcess.waitFor();
                }
                }
                catch(Exception e)
                {
                    System.out.println("fail to stop active container : "+e.getMessage());
                }
            }

            try (Stream<Path> paths = Files.walk(tempDir)) {
                paths.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (Exception e) {
                            System.err.println(
                                "Failed to delete: " + path + " - " + e.getMessage()
                            );
                        }
                    });

            } catch (Exception e) {
                System.err.println("Cleanup failed: " + e.getMessage());
            }
        }
                
    }
}