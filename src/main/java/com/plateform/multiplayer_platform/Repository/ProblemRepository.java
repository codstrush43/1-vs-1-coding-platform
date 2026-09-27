package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
