package com.plateform.multiplayer_platform.Controller.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.SubmissionRequest;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;
import com.plateform.multiplayer_platform.Service.ProblemService;
import com.plateform.multiplayer_platform.Service.SubmissionService;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    
    @Autowired
    private SubmissionService submissionService;

    @Autowired 
    private CodeExecutionService codeExecutionService;

    @Autowired 
    private ProblemService problemService;

    @PostMapping("/submit")
    public ResponseEntity<?> saveSubmission(@RequestBody SubmissionRequest submissionRequest)
    {
        return ResponseEntity.ok(submissionService.saveSubmission(submissionRequest));
    }

    @GetMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody SubmissionRequest submissionRequest)
    {   
        try{
            Problem problem=problemService.findById(submissionRequest.getProblemId());
            return ResponseEntity.ok(codeExecutionService.execute(problem,submissionRequest.getCode(),""));
        }
        catch(Exception e){}

        return ResponseEntity.ok("error");
    }

}
