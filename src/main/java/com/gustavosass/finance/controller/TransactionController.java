package com.gustavosass.finance.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.finance.dtos.CreateTransactionDTO;
import com.gustavosass.finance.dtos.TransactionDTO;
import com.gustavosass.finance.dtos.UpdateTransactionDTO;
import com.gustavosass.finance.mapper.TransactionMapper;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionMapper transactionMapper;

	@GetMapping
	public ResponseEntity<List<TransactionDTO>> getAll() {
		List<TransactionDTO> transactionDto = transactionService.findAll().stream().map(transactionMapper::toDto).collect(Collectors.toList());
		return ResponseEntity.ok(transactionDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
		TransactionDTO transactionDto = transactionMapper.toDto(transactionService.findById(id));
		return ResponseEntity.ok(transactionDto);
	}

	@PostMapping
	public ResponseEntity<TransactionDTO> create(@Valid @RequestBody CreateTransactionDTO createTransactionDto) {
		Transaction transaction = transactionMapper.toEntity(createTransactionDto);
		Transaction transactionCreated = transactionService.create(transaction);
		return ResponseEntity.ok(transactionMapper.toDto(transactionCreated));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateTransactionDTO updateTransactionDTO) {
		Transaction transaction = transactionMapper.toEntity(updateTransactionDTO);
		Transaction transactionExists = transactionService.update(id, transaction);
		return ResponseEntity.ok(transactionMapper.toDto(transactionExists));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		transactionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
	}

}
