package com.plateform.multiplayer_platform.Entity.composite_key;

import java.io.Serializable;
import java.util.Objects;


public class PlayerScoreId implements Serializable {

    private Long user;
    private Long match;

    public PlayerScoreId() {}

    public PlayerScoreId(Long userId, Long matchId) {
        this.user = userId;
        this.match = matchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerScoreId)) return false;

        PlayerScoreId that = (PlayerScoreId) o;

        return Objects.equals(user, that.user) &&
               Objects.equals(match, that.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, match);
    }
}
