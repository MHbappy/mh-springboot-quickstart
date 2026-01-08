package com.bappy.application.passport.controller;

import com.bappy.application.common.dto.ApiResponse;
import com.bappy.application.passport.entity.PassportInfo;
import com.bappy.application.passport.service.PassportInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passport-info")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Passport Info", description = "Passport information management endpoints")
public class PassportInfoController {

    private final PassportInfoService passportInfoService;

    @PostMapping
    @Operation(summary = "Create passport info", description = "Create a new passport information record")
    public ResponseEntity<ApiResponse<PassportInfo>> createPassportInfo(@RequestBody PassportInfo passportInfo) {
        log.info("Request to create passport info for employee: {}", passportInfo.getEmployeeId());
        PassportInfo createdPassportInfo = passportInfoService.createPassportInfo(passportInfo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Passport info created successfully", createdPassportInfo));
    }

    @GetMapping
    @Operation(summary = "Get all passport info", description = "Retrieve a list of all passport information records")
    public ResponseEntity<ApiResponse<List<PassportInfo>>> getAllPassportInfos() {
        log.info("Request to fetch all passport info");
        List<PassportInfo> passportInfos = passportInfoService.getAllPassportInfos();
        return ResponseEntity.ok(ApiResponse.success("Passport info fetched successfully", passportInfos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get passport info by ID", description = "Retrieve passport information by its ID")
    public ResponseEntity<ApiResponse<PassportInfo>> getPassportInfoById(@PathVariable Long id) {
        log.info("Request to fetch passport info with id: {}", id);
        PassportInfo passportInfo = passportInfoService.getPassportInfoById(id);
        return ResponseEntity.ok(ApiResponse.success("Passport info fetched successfully", passportInfo));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get passport info by employee ID", description = "Retrieve all passport information records for a specific employee")
    public ResponseEntity<ApiResponse<List<PassportInfo>>> getPassportInfosByEmployeeId(@PathVariable Long employeeId) {
        log.info("Request to fetch passport info for employee id: {}", employeeId);
        List<PassportInfo> passportInfos = passportInfoService.getPassportInfosByEmployeeId(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Passport info fetched successfully", passportInfos));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update passport info", description = "Update an existing passport information record")
    public ResponseEntity<ApiResponse<PassportInfo>> updatePassportInfo(@PathVariable Long id, @RequestBody PassportInfo passportInfo) {
        log.info("Request to update passport info with id: {}", id);
        PassportInfo updatedPassportInfo = passportInfoService.updatePassportInfo(id, passportInfo);
        return ResponseEntity.ok(ApiResponse.success("Passport info updated successfully", updatedPassportInfo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete passport info", description = "Delete a passport information record by specific ID")
    public ResponseEntity<ApiResponse<Void>> deletePassportInfo(@PathVariable Long id) {
        log.info("Request to delete passport info with id: {}", id);
        passportInfoService.deletePassportInfo(id);
        return ResponseEntity.ok(ApiResponse.success("Passport info deleted successfully"));
    }
}
