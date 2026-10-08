package com.plateform.multiplayer_platform.Service;

import java.util.List;

import com.plateform.multiplayer_platform.DTOs.TestCaseDto;
import com.plateform.multiplayer_platform.Entity.TestCase;

public interface TestCaseService {
    public TestCase addTestCase(TestCaseDto testCaseDto);
    public List<TestCase> findByProblemId(Long problemId);
}
