package com.plateform.multiplayer_platform.Entity.composite_key;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MatchProblemScoreId implements Serializable {

    private Long user;
    private Long match;
    private Long problem;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MatchProblemScoreId)) return false;

        MatchProblemScoreId that = (MatchProblemScoreId) obj;

        return Objects.equals(user, that.user)
                && Objects.equals(match, that.match)
                && Objects.equals(problem, that.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, match, problem);
    }
}
