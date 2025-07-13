package com.unesp.ms_financial_management.config;

import com.unesp.ms_financial_management.model.Charge;
import com.unesp.ms_financial_management.model.Resident;
import com.unesp.ms_financial_management.model.ResidentType;
import com.unesp.ms_financial_management.repository.ChargeRepository;
import com.unesp.ms_financial_management.repository.ResidentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializerConfig {

    @Bean
    CommandLineRunner initDatabase(ResidentRepository residentRepository, ChargeRepository parcelaRepository) {
        return args -> {
            Resident resident1 = residentRepository.findById(3L).get();

            if(!parcelaRepository.findByResidentId(resident1.getId()).isEmpty()){
                System.out.println("resident já possui parcelas");
                return;
            }

            // Criar parcelas vinculadas ao resident1
            // Parcela 1
            Charge parcela1 = new Charge();
            parcela1.setValor(new BigDecimal("100.00"));
            parcela1.setVencimento(LocalDate.now().plusDays(30));
            parcela1.setResident(resident1);

            //Parcela 2
            Charge parcela2 = new Charge();
            parcela2.setValor(new BigDecimal("200.00"));
            parcela2.setVencimento(LocalDate.now().plusDays(30));
            parcela2.setResident(resident1);

            // Criar Lista de parcela
            List<Charge> parcelas = new ArrayList<>();
            parcelas.add(parcela1);
            parcelas.add(parcela2);

            // Associar parcelas ao resident1
            resident1.setParcelas(parcelas);

            // Salvar no banco de dados
            residentRepository.save(resident1);
        };
    }
}
