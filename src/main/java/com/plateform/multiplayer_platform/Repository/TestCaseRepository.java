package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
