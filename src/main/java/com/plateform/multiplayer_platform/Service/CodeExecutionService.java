package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Enum.SubmissionStatus;

public interface CodeExecutionService {
    SubmissionStatus execute(Submission submission) throws Exception;
}  
