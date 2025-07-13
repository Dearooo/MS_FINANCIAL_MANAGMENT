package com.unesp.ms_financial_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unesp.ms_financial_management.model.*;
import com.unesp.ms_financial_management.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class ParcelaSchedulerService {

    @Autowired
    private ResidentRepository residentRepository;
    
    @Autowired
    private ParcelaRepository parcelaRepository;

    /**
     * Executa no primeiro dia de cada mês às 01:00 AM
     */
    @Scheduled(cron = "0 0 1 1 * ?") 
    @Transactional
    public void gerarParcelasMensais() {
        System.out.println("Iniciando geração de parcelas mensais: " + LocalDate.now());
        
        // Buscar todos os residentes
        List<Resident> residentes = residentRepository.findAll();
        
        for (Resident resident : residentes) {
            gerarParcelasParaResidente(resident);
        }
        
        System.out.println("Geração de parcelas mensais concluída!");
    }
    
    private void gerarParcelasParaResidente(Resident resident) {
        
        LocalDate primeiroDiaMesAtual = LocalDate.now().withDayOfMonth(1);
        LocalDate primeiroDiaProximoMes = primeiroDiaMesAtual.plusMonths(1);
        
        List<Parcela> parcelasDoMes = parcelaRepository.findByResidentIdAndVencimentoBetween(
                resident.getId(), primeiroDiaMesAtual, primeiroDiaProximoMes.minusDays(1));
        
        if (!parcelasDoMes.isEmpty()) {
            System.out.println("Parcelas já geradas para o residente: " + resident.getId());
            return;
        }
        
        // Criar parcelas para o próximo mês
        List<Parcela> novasParcelas = new ArrayList<>();
        
        // Parcela 1 - Valor fixo de exemplo
        Parcela parcela1 = new Parcela();
        parcela1.setValor(new BigDecimal("100.00"));
        parcela1.setVencimento(LocalDate.now().plusMonths(1).withDayOfMonth(10)); // Vencimento dia 10 do próximo mês
        parcela1.setResident(resident);
        novasParcelas.add(parcela1);
        
        // Parcela 2 - Outro valor fixo de exemplo
        Parcela parcela2 = new Parcela();
        parcela2.setValor(new BigDecimal("200.00"));
        parcela2.setVencimento(LocalDate.now().plusMonths(1).withDayOfMonth(20)); // Vencimento dia 20 do próximo mês
        parcela2.setResident(resident);
        novasParcelas.add(parcela2);
        
        // Adicionar à lista de parcelas do residente
        List<Parcela> parcelasAtuais = resident.getParcelas();
        if (parcelasAtuais == null) {
            parcelasAtuais = new ArrayList<>();
        }
        parcelasAtuais.addAll(novasParcelas);
        resident.setParcelas(parcelasAtuais);
        
        // Salvar o residente com as novas parcelas
        residentRepository.save(resident);
        System.out.println("Parcelas geradas com sucesso para o residente: " + resident.getId());
    }
    
    // Método adicional para teste manual da geração de parcelas
    @Transactional
    public void gerarParcelasManuais() {
        gerarParcelasMensais();
    }
}
