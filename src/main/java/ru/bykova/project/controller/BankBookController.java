package ru.bykova.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bykova.project.model.BankBookDto;
import ru.bykova.project.service.BankBookService;
import ru.bykova.project.utils.ErrorUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class BankBookController {

    private final BankBookService bankBookService;

    public BankBookController(BankBookService bankBookService) {
        this.bankBookService = bankBookService;
    }

    @GetMapping("/bank-book/all")
    public ResponseEntity getAllBankBooksInStorage() {
        List<BankBookDto> allBankBooksInStorage = bankBookService.getAllBankBooksInStorage();
        return CollectionUtils.isEmpty(allBankBooksInStorage) ?
                ResponseEntity.ok().body(ErrorUtils.generate(ErrorUtils.NOT_FOUND)) :
                ResponseEntity.ok().body(allBankBooksInStorage);
    }

    @GetMapping("/bank-book/{bankBookId}")
    public ResponseEntity getBankBookByBankBookId(@PathVariable Integer bankBookId) {
        try {
            BankBookDto bankBookByBankBookId = bankBookService.getBankBookByBankBookId(bankBookId);
            return ResponseEntity.ok().body(bankBookByBankBookId);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @PostMapping("/bank-book")
    public ResponseEntity createBankBook(@RequestBody BankBookDto bankBookDto) {
        log.info("bankBookDto={}", bankBookDto);
        try {
            BankBookDto bankBook = bankBookService.createBankBook(bankBookDto);
            return ResponseEntity.ok().body(bankBook);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @PutMapping("/bank-book")
    public ResponseEntity updateBankBook(@RequestBody BankBookDto bankBookDto) {
        log.info("bankBookDto={}", bankBookDto);
        try {
            BankBookDto bankBook = bankBookService.updateBankBook(bankBookDto);
            return ResponseEntity.ok().body(bankBook);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @DeleteMapping("/bank-book/{bankBookId}")
    public ResponseEntity deleteBankBookByBankBookId(@PathVariable Integer bankBookId) {
        try {
            bankBookService.deleteBankBookByBankBookId(bankBookId);
            String message = String.format("BankBook with id=%s successfully deleted!", bankBookId);
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }
}
