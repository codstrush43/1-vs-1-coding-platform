package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.DTOs.SubmissionRequest;
import com.plateform.multiplayer_platform.Entity.Submission;

public interface SubmissionService {
    Submission saveSubmission(SubmissionRequest submissionRequest);
}
