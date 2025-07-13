package com.unesp.ms_financial_management.repository;

import com.unesp.ms_financial_management.model.Charge;
import com.unesp.ms_financial_management.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
}
