package ru.bykova.project.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/")
public class BankBookController {

    private final BankBookService bankBookService;

    public BankBookController(BankBookService bankBookService) {
        this.bankBookService = bankBookService;
    }

    @GetMapping("/bank-book/all")
    public List<BankBookDto> getAllBankBooksInStorage() {
        return bankBookService.getAllBankBooksInStorage();
    }

    @GetMapping("/bank-book/by-user-id/{userId}")
    public ResponseEntity getAllBankBookByUserId(@PathVariable Integer userId) {
        try {
            List<BankBookDto> allBankBookByUserId = bankBookService.getAllBankBookByUserId(userId);
            return ResponseEntity.ok().body(allBankBookByUserId);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @GetMapping("/bank-book/{bankBookId}")
    public ResponseEntity getBankBookByBankBookId(@PathVariable Integer bankBookId) {
        try {
            BankBookDto bankBookByBankBookId = bankBookService.getBankBookByBankBookId(bankBookId);
            return ResponseEntity.ok().body(bankBookByBankBookId);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @PostMapping("/bank-book")
    public ResponseEntity createBankBook(@RequestBody BankBookDto bankBookDto) {
        log.info("bankBookDto={}", bankBookDto);
        try {
            BankBookDto bankBook = bankBookService.createBankBook(bankBookDto);
            return ResponseEntity.ok().body(bankBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @PutMapping("/bank-book")
    public ResponseEntity updateBankBook(@RequestBody BankBookDto bankBookDto) {
        log.info("bankBookDto={}", bankBookDto);
        try {
            BankBookDto bankBook = bankBookService.updateBankBook(bankBookDto);
            return ResponseEntity.ok().body(bankBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @DeleteMapping("/bank-book/{bankBookId}")
    public ResponseEntity deleteBankBookByBankBookId(@PathVariable Integer bankBookId) {
        try {
            bankBookService.deleteBankBookByBankBookId(bankBookId);
            String message = String.format("BankBook with id=%s successfully deleted!", bankBookId);
            return ResponseEntity.ok().body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }

    @DeleteMapping("/bank-book/by-user-id/{userId}")
    public ResponseEntity deleteAllBankBooksByUserId(@PathVariable Integer userId) {
        try {
            bankBookService.deleteAllBankBooksByUserId(userId);
            String message = String.format("All bankBooks for userId=%s successfully deleted!", userId);
            return ResponseEntity.ok().body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(ErrorUtils.generate(e.getMessage()));
        }
    }
}
