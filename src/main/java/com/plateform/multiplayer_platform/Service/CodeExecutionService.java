package com.plateform.multiplayer_platform.Service;

import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.SubmissionExecutionResult;
import com.plateform.multiplayer_platform.Entity.Problem;

public interface CodeExecutionService {
    boolean execute(Problem problem,String code,String language) throws Exception;
}  
