package com.plateform.multiplayer_platform.DTOs;

import com.plateform.multiplayer_platform.Enum.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionExecutionResult {

    private SubmissionStatus status;
    private long passedTestCases;
    private long totalTestCases;

}
