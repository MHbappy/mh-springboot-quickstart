package com.bappy.application.passport.repository;

import com.bappy.application.passport.entity.PassportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportInfoRepository extends JpaRepository<PassportInfo, Long> {
    List<PassportInfo> findByEmployeeId(Long employeeId);
}
