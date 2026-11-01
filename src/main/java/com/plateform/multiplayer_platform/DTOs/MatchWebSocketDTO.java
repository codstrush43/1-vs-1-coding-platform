package com.plateform.multiplayer_platform.DTOs;

import com.plateform.multiplayer_platform.Enum.SubmissionStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class MatchWebSocketDTO {
    private long match_id;
    private long player_id;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus verdict;
}
