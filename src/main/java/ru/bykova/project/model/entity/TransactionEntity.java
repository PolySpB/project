package ru.bykova.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction", schema = "bank")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "source_bank_book_id", nullable = false)
    private BankBookEntity sourceBankBookId;

    @OneToOne
    @JoinColumn(name = "target_bank_book_id", nullable = false)
    private BankBookEntity targetBankBookId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "initiation_date", nullable = false)
    private LocalDateTime initiationDate;

    @Column(name = "completion_date", nullable = false)
    private LocalDateTime completionDate;

    @OneToOne
    @JoinColumn(name = "status_id", nullable = false)
    private TransactionStatusEntity status;
}
