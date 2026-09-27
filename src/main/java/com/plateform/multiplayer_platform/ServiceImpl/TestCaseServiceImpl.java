package com.plateform.multiplayer_platform.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.TestCaseDto;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.TestCase;
import com.plateform.multiplayer_platform.Repository.ProblemRepository;
import com.plateform.multiplayer_platform.Repository.TestCaseRepository;
import com.plateform.multiplayer_platform.Service.TestCaseService;
import org.modelmapper.ModelMapper;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TestCase addTestCase(TestCaseDto testCaseDto) {

        long problem_id=testCaseDto.getProblem_id();

        Problem problem = problemRepository.findById(problem_id).orElse(null);

        if(problem == null)
        {
            throw new RuntimeException("Problem not found with id: " + problem_id);
        }

        TestCase testCase = modelMapper.map(testCaseDto,TestCase.class);
        testCase.setProblem(problem);

        return testCaseRepository.save(testCase);

    }

}
