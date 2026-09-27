package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.DTOs.SubmissionExecutionResult;
import com.plateform.multiplayer_platform.Entity.Problem;

public interface CodeExecutionService {
    SubmissionExecutionResult execute(Problem problem,String code,String language);
}  
