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

import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Entity.TestCase;
import com.plateform.multiplayer_platform.Enum.SubmissionStatus;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;
import com.plateform.multiplayer_platform.Service.SubmissionService;
import com.plateform.multiplayer_platform.Service.TestCaseService;

@Service 
public class CodeExecutionServiceImpl implements CodeExecutionService{

    @Autowired 
    private TestCaseService testCaseService;

    @Autowired 
    private SubmissionService submissionService;
    
    @Override 
    public Submission execute(Submission submission) throws Exception{

        Path tempDir = Files.createTempDirectory("cpp-executor");
        Path sourceFile=tempDir.resolve("main.cpp");
        String activeContainerName=null;
        try{
        Files.writeString(sourceFile,submission.getCode());
        
        List<TestCase> testCases=testCaseService.findByProblemId(submission.getProblem().getId());

        String compilingContainerName="cpp-compiler"+UUID.randomUUID();
        activeContainerName=compilingContainerName;
        
        ProcessBuilder compileProcessBuilder = new ProcessBuilder(
            "docker",
            "run",
            "--name",compilingContainerName,
            "--rm",
            "-v",
            tempDir.toAbsolutePath() + ":/app",
            "cpp-executor",
            "bash",
            "-c",
            "g++ /app/main.cpp -o /app/main"
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
            submission.setStatus(SubmissionStatus.COMPILATION_TIME_LIMIT_EXCEEDED);
            return submissionService.saveUpdatedStatusSubmission(submission);
        }

        if(compileProcess.exitValue()!=0)
        {
            String error=new String(compileProcess.getErrorStream().readAllBytes());
            submission.setStatus(SubmissionStatus.COMPILATION_ERROR);
            return submissionService.saveUpdatedStatusSubmission(submission);
        }
        
        for(TestCase testCase : testCases)
        {
            Path inputFile = tempDir.resolve("input.txt");
            Files.writeString(inputFile,testCase.getInput());
            
            String runningContainerName="cpp-runner"+UUID.randomUUID();
            activeContainerName=runningContainerName;

            ProcessBuilder processBuilder=new ProcessBuilder(
                "docker",
                "run",
                "--name",runningContainerName,
                "--rm",
                "-v",
                tempDir.toAbsolutePath()+":/app",
                "cpp-executor",
                "bash",
                "-c",
                "/app/main < /app/input.txt"
            );

            Process process = processBuilder.start();

            boolean exitcode=process.waitFor(2,TimeUnit.SECONDS);
 System.out.println("su kare che laa");
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
               submission.setStatus(SubmissionStatus.TIME_LIMIT_EXCEEDED);
                return submissionService.saveUpdatedStatusSubmission(submission);
            }
            
            if(process.exitValue()!=0)
            {
                String error = new String(process.getErrorStream().readAllBytes());
                submission.setStatus(SubmissionStatus.RUNTIME_ERROR);
            return submissionService.saveUpdatedStatusSubmission(submission);
            }

            String output=new String(process.getInputStream().readAllBytes());

            System.out.println(output+"/"+testCase.getExpectedOutput());

            if(!output.trim().equals(testCase.getExpectedOutput().trim()))
            {
                submission.setStatus(SubmissionStatus.WRONG_ANSWER);
            return submissionService.saveUpdatedStatusSubmission(submission);
            }
            activeContainerName=null;
        }
            submission.setStatus(SubmissionStatus.ACCEPTED);
            return submissionService.saveUpdatedStatusSubmission(submission);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            submission.setStatus(SubmissionStatus.INTERNAL_ERROR);
            return submissionService.saveUpdatedStatusSubmission(submission);
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