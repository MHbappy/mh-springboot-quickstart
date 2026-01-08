package com.bappy.application.passport.service;

import com.bappy.application.passport.entity.PassportInfo;
import com.bappy.application.passport.repository.PassportInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PassportInfoService {

    private final PassportInfoRepository passportInfoRepository;

    public PassportInfo createPassportInfo(PassportInfo passportInfo) {
        return passportInfoRepository.save(passportInfo);
    }

    @Transactional(readOnly = true)
    public List<PassportInfo> getAllPassportInfos() {
        return passportInfoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PassportInfo getPassportInfoById(Long id) {
        return passportInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passport Info not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PassportInfo> getPassportInfosByEmployeeId(Long employeeId) {
        return passportInfoRepository.findByEmployeeId(employeeId);
    }

    public PassportInfo updatePassportInfo(Long id, PassportInfo passportInfoDetails) {
        PassportInfo passportInfo = getPassportInfoById(id);

        passportInfo.setPassportType(passportInfoDetails.getPassportType());
        passportInfo.setPassportNumber(passportInfoDetails.getPassportNumber());
        passportInfo.setPassportStatus(passportInfoDetails.getPassportStatus());
        passportInfo.setExpireDate(passportInfoDetails.getExpireDate());
        passportInfo.setAddress(passportInfoDetails.getAddress());
        passportInfo.setPassportAmount(passportInfoDetails.getPassportAmount());
        passportInfo.setPassportPages(passportInfoDetails.getPassportPages());
        passportInfo.setEmployeeId(passportInfoDetails.getEmployeeId());

        return passportInfoRepository.save(passportInfo);
    }

    public void deletePassportInfo(Long id) {
        PassportInfo passportInfo = getPassportInfoById(id);
        passportInfoRepository.delete(passportInfo);
    }
}
