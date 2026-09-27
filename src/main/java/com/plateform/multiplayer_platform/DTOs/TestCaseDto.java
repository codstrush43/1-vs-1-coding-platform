package com.plateform.multiplayer_platform.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private long problem_id;
    private String input;
    private String expectedOutput;
    private boolean hidden;
}
