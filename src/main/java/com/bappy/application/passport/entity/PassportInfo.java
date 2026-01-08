package com.bappy.application.passport.entity;

import com.bappy.application.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "passport_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "passport_type", nullable = false)
    private PassportType passportType;

    @Column(name = "passport_number", nullable = false, unique = true)
    private String passportNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "passport_status", nullable = false)
    private PassportStatus passportStatus;

    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;

    @Column(nullable = false)
    private String address;

    @Column(name = "passport_amount")
    private BigDecimal passportAmount;

    @Column(name = "passport_pages")
    private Integer passportPages;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
}
