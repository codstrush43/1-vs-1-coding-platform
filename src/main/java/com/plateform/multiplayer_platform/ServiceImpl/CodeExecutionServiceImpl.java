package com.plateform.multiplayer_platform.ServiceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.TestCase;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;
import com.plateform.multiplayer_platform.Service.TestCaseService;

@Service 
public class CodeExecutionServiceImpl implements CodeExecutionService{

    @Autowired 
    private TestCaseService testCaseService;
    
    @Override 
    public boolean execute(Problem problem,String code,String language) throws Exception{
        
        Path tempDir = Files.createTempDirectory("cpp-execuro");
        Path sourceFile=tempDir.resolve("main.cpp");
        
        Files.writeString(sourceFile,code);
        
        List<TestCase> testCases=testCaseService.findByProblemId(problem.getId());
        
        ProcessBuilder compileProcessBuilder = new ProcessBuilder(
            "docker",
            "run",
            "--rm",
            "-v",
            tempDir.toAbsolutePath() + ":/app",
            "cpp-executor",
            "bash",
            "-c",
            "g++ /app/main.cpp -o /app/main"
        );

        Process compileProcess = compileProcessBuilder.start();

        int compileExitCode = compileProcess.waitFor();

        if(compileExitCode!=0)
        {
            return false;
        }

        for(TestCase testCase : testCases)
        {
            Path inputFile = tempDir.resolve("input.txt");
            Files.writeString(inputFile,testCase.getInput());

            ProcessBuilder processBuilder=new ProcessBuilder(
                "docker",
                "run",
                "--rm",
                "-v",
                tempDir.toAbsolutePath()+":/app",
                "cpp-executor",
                "bash",
                "-c",
                "/app/main < /app/input.txt"
            );

            Process process = processBuilder.start();

            String output=new String(process.getInputStream().readAllBytes());

            String error = new String(process.getErrorStream().readAllBytes());

            int exitcode=process.waitFor();

            System.out.println(output+"/"+testCase.getExpectedOutput()+"/"+error);

            if(exitcode!=0)
            {
                return false;
            }

            if(!output.trim().equals(testCase.getExpectedOutput().trim()))
            {
                return false;
            }
        }

        return true;
    }
}
