package com.plateform.multiplayer_platform.Controller.Submission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.SubmissionRequest;
import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Service.SubmissionService;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    
    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/save")
    public ResponseEntity<?> saveSubmission(@RequestBody SubmissionRequest submissionRequest)
    {
        return ResponseEntity.ok(submissionService.saveSubmission(submissionRequest));
    }

}
