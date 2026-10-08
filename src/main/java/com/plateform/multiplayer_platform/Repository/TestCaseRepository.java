package com.plateform.multiplayer_platform.Repository;

import java.lang.classfile.ClassFile.Option;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.TestCase;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProblemId(Long problemId);
}
