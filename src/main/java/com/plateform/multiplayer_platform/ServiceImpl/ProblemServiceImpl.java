package com.plateform.multiplayer_platform.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.ProblemDto;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Repository.ProblemRepository;
import com.plateform.multiplayer_platform.Service.ProblemService;

@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired 
    private ModelMapper modelMapper;

    @Override
    public Problem addProblem(ProblemDto problemDto) {
        Problem problem = modelMapper.map(problemDto, Problem.class);
        return problemRepository.save(problem);
    }

    @Override 
    public Problem findById(Long problemId)
    {
        return  problemRepository.findById(problemId).orElse(null);
    }
}
