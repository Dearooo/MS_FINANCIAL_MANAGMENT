package com.unesp.ms_financial_management.controller;

import com.unesp.ms_financial_management.controller.dto.ChargeDTO;
import com.unesp.ms_financial_management.model.Charge;
import com.unesp.ms_financial_management.repository.ChargeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChargeController {

    private final ChargeRepository parcelaRepository;

    public ChargeController(ChargeRepository parcelaRepository) {
        this.parcelaRepository = parcelaRepository;
    }

    @GetMapping("/parcelas")
    @PreAuthorize("hasRole('RESIDENT')")
    public List<ChargeDTO> getParcelas(@AuthenticationPrincipal Jwt jwt) {
        Long residentId = Long.parseLong(jwt.getClaim("sub"));

        return parcelaRepository.findByResidentId(residentId).stream()
                .map(p -> new ChargeDTO(p.getValor(), p.getVencimento()))
                .toList();
    }
}
