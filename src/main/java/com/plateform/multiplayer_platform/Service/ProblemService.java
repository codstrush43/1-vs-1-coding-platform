package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.DTOs.ProblemDto;
import com.plateform.multiplayer_platform.Entity.Problem;

public interface ProblemService {
    public Problem addProblem(ProblemDto problemDto);
}
