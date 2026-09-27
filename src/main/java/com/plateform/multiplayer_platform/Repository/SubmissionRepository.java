package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long>{

}
