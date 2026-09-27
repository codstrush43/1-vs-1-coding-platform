package com.plateform.multiplayer_platform.Controller.TestCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.TestCaseDto;
import com.plateform.multiplayer_platform.Entity.TestCase;
import com.plateform.multiplayer_platform.Service.TestCaseService;

@RestController
@RequestMapping("/api/test-cases")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("/add")
    public ResponseEntity<TestCase> addTestCase(@RequestBody TestCaseDto testCaseDto) {
        TestCase createdTestCase = testCaseService.addTestCase(testCaseDto);
        return ResponseEntity.ok(createdTestCase);
    }
}
