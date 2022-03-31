package ru.bykova.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bykova.project.model.TransactionStatus;
import ru.bykova.project.model.dto.TransactionDto;
import ru.bykova.project.service.TransactionService;
import ru.bykova.project.utils.ErrorUtils;

@Slf4j
@RestController
public class TransferController {

    private final TransactionService transactionService;

    public TransferController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PutMapping("/perform-transfer")
    public ResponseEntity performTransfer(@RequestBody TransactionDto transactionDto) {
        try {
            transactionService.transferBetweenBankBook(transactionDto.getSourceBankBookNumber(), transactionDto.getTargetBankBookNumber(), transactionDto.getAmount());
            return ResponseEntity.ok().body(TransactionStatus.SUCCESSFUL);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }
}
