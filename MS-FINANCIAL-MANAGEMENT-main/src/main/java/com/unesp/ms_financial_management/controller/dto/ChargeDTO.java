package com.unesp.ms_financial_management.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChargeDTO(BigDecimal valor, LocalDate vencimento) {}
