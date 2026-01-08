-- Create employees table
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    father_name VARCHAR(255),
    is_married BOOLEAN,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_by BIGINT
);

-- Create indexes for performance
CREATE INDEX idx_employees_name ON employees(name);
CREATE INDEX idx_employees_gender ON employees(gender);
