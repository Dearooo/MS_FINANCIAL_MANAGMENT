package com.unesp.ms_financial_management.repository;

import com.unesp.ms_financial_management.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findByResidentId(Long residentId);

    List<Charge> findByResidentIdAndVencimentoBetween(Long residentId, LocalDate dataInicio, LocalDate dataFim);
}
