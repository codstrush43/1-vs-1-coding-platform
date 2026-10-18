package com.plateform.multiplayer_platform.Controller.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.SubmissionRequest;
import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;
import com.plateform.multiplayer_platform.Service.ProblemService;
import com.plateform.multiplayer_platform.Service.SubmissionService;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    
    @Autowired
    private SubmissionService submissionService;
    
        @PostMapping("/submit")
        public ResponseEntity<?> execute(@RequestBody SubmissionRequest submissionRequest) {

            try {
                Submission submission =submissionService.saveSubmission(submissionRequest);
                return ResponseEntity.ok(submission);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError()
                        .body("Failed to process submission");
            }
        }

}
