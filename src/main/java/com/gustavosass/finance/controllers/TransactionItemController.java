package com.gustavosass.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.finance.dtos.CreateTransactionItemDTO;
import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.mapper.TransactionItemMapper;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.service.TransactionItemService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactionItem")
@Tag(name = "Transaction Item")
public class TransactionItemController {

    @Autowired
    private TransactionItemService transactionItemService;

    @Autowired
    private TransactionItemMapper transactionItemMapper;

    @GetMapping("/{idTransaction}/all")
    public ResponseEntity<List<TransactionItemDTO>> findAllById(@PathVariable Long idTransaction){
        return ResponseEntity.ok(transactionItemService.findAllByTransactionId(idTransaction).stream().map(transactionItemMapper::toDto).toList());
    }
    
    @GetMapping("/{idTransaction}/listAllPaid")
    public ResponseEntity<List<TransactionItemDTO>> listInstallmentsPaidByIdTransaction(@PathVariable Long idTransaction){
        return ResponseEntity.ok(transactionItemService.listInstallmentsPaidByIdTransaction(idTransaction).stream().map(transactionItemMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionItemDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(transactionItemMapper.toDto(transactionItemService.findById(id)));
    }
    
    @GetMapping
    public ResponseEntity<List<TransactionItemDTO>> findAll(){
        return ResponseEntity.ok(transactionItemService.findAll().stream().map((n) -> transactionItemMapper.toDto(n)).toList());
    }
    
    @PostMapping
	public ResponseEntity<TransactionItemDTO> create(@Valid @RequestBody CreateTransactionItemDTO createTransactionItemDto) {
    	TransactionItem transactionItem = transactionItemMapper.toEntity(createTransactionItemDto);
    	TransactionItem transactionItemCreated = transactionItemService.create(transactionItem);
		return ResponseEntity.ok(transactionItemMapper.toDto(transactionItemCreated));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TransactionItemDTO> update(@PathVariable Long id, @Valid @RequestBody TransactionItemDTO transactionItemDto) {
		TransactionItem transactionItem = transactionItemMapper.toEntity(transactionItemDto);
		TransactionItem transactionItemExists = transactionItemService.update(id, transactionItem);
		return ResponseEntity.ok(transactionItemMapper.toDto(transactionItemExists));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		transactionItemService.delete(id);
        return ResponseEntity.noContent().build();
	}

}
