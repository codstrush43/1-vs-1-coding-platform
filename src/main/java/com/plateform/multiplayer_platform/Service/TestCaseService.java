package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.DTOs.TestCaseDto;
import com.plateform.multiplayer_platform.Entity.TestCase;

public interface TestCaseService {
    public TestCase addTestCase(TestCaseDto testCaseDto);
}
