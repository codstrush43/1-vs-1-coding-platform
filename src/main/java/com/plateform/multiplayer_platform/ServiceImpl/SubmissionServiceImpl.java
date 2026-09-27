package com.plateform.multiplayer_platform.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.SubmissionExecutionResult;
import com.plateform.multiplayer_platform.DTOs.SubmissionRequest;
import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.Submission;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Repository.ProblemRepository;
import com.plateform.multiplayer_platform.Repository.SubmissionRepository;
import com.plateform.multiplayer_platform.Service.SubmissionService;
import com.plateform.multiplayer_platform.Enum.SubmissionStatus;
import com.plateform.multiplayer_platform.Service.CodeExecutionService;

@Service
public class SubmissionServiceImpl implements SubmissionService{
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired 
    private CodeExecutionService codeExecutionService;

    @Override
    public Submission saveSubmission(SubmissionRequest submissionRequest) {

        Match match = matchRepository.findById(submissionRequest.getMatchId())
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + submissionRequest.getMatchId()));
        Problem problem = problemRepository.findById(submissionRequest.getProblemId()).orElseThrow(
            () -> new RuntimeException("Problem not found with id :"+ submissionRequest.getProblemId())
        );

        // SubmissionExecutionResult executionResult = codeExecutionService.execute(problem, submissionRequest.getCode(), submissionRequest.getLanguage());

        Submission submission = new Submission();
        submission.setMatch(match);
        submission.setProblem(problem);
        submission.setLanguage(submissionRequest.getLanguage());
        submission.setCode(submissionRequest.getCode());
        submission.setStatus(SubmissionStatus.PENDING);

        return submissionRepository.save(submission);
    }
}
