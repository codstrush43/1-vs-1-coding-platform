package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.Entity.Submission;

public interface CodeExecutionService {
    Submission execute(Submission submission) throws Exception;
}  
