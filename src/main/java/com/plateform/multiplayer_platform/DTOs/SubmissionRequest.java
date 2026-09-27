package com.plateform.multiplayer_platform.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    private Long matchId;
    private Long problemId;
    private String language;
    private String code;
}
