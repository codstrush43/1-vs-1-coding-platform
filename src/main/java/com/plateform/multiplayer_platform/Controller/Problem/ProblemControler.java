package com.plateform.multiplayer_platform.Controller.Problem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateform.multiplayer_platform.DTOs.ProblemDto;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Service.ProblemService;

@RestController
@RequestMapping("/api/problems")
public class ProblemControler {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/add")
    public ResponseEntity<Problem> addProblem(@RequestBody ProblemDto problemDto) {
        Problem problem = problemService.addProblem(problemDto);
        return ResponseEntity.ok(problem);
    }

}
