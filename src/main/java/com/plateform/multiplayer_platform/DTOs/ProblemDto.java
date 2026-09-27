package com.plateform.multiplayer_platform.DTOs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data 
public class ProblemDto {

    private Long id;

    private String title;

    private String description;

    private String difficulty;

    private String inputFormat;

    private String outputFormat;

    private String constraints;
}
