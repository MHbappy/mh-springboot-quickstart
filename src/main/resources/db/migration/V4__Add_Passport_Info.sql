-- Create passport_info table
CREATE TABLE passport_info (
    id BIGSERIAL PRIMARY KEY,
    passport_type VARCHAR(50) NOT NULL,
    passport_number VARCHAR(100) NOT NULL UNIQUE,
    passport_status VARCHAR(50) NOT NULL,
    expire_date DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    passport_amount DECIMAL(19, 2),
    passport_pages INTEGER,
    employee_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_passport_employee FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Create indexes for performance
CREATE INDEX idx_passport_info_employee_id ON passport_info(employee_id);
CREATE INDEX idx_passport_info_passport_number ON passport_info(passport_number);
