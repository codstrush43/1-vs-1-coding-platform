package com.plateform.multiplayer_platform.ServiceImpl;

import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.SubmissionExecutionResult;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;

@Service 
public class CodeExecutionServiceImpl implements CodeExecutionService{
    
    @Override 
    public SubmissionExecutionResult execute(Problem problem,String code,String language){
        return null;
    }
}
