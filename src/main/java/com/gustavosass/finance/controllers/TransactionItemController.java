package com.gustavosass.finance.controllers;

import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.mapper.TransactionItemMapper;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.service.TransactionItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction Item")
public class TransactionItemController {

    @Autowired
    private TransactionItemService transactionItemService;

    @Autowired
    private TransactionItemMapper transactionItemMapper;

    @GetMapping("{idTransaction}/item")
    public ResponseEntity<List<TransactionItemDTO>> findAllById(@PathVariable Long idTransaction){
        return ResponseEntity.ok(transactionItemService.findAllById(idTransaction).stream().map(transactionItemMapper::toDto).toList());
    }

    @GetMapping("{idTransaction}/item/{id}")
    public ResponseEntity<TransactionItemDTO> findById(@PathVariable Long idTransaction, @PathVariable Long id){
        return ResponseEntity.ok(transactionItemMapper.toDto(transactionItemService.findById(idTransaction, id)));
    }

    @PutMapping("{idTransaction}/item/{id}")
    public ResponseEntity<TransactionItemDTO> update(@PathVariable Long idTransaction, @PathVariable Long id, @RequestBody TransactionItemDTO transactionItemDTO){
        TransactionItem transactionItem = transactionItemMapper.toEntity(transactionItemDTO);
        transactionItem = transactionItemService.update(idTransaction, id, transactionItem);
        return ResponseEntity.ok(transactionItemMapper.toDto(transactionItem));
    }
}