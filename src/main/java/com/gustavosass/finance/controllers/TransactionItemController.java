package com.gustavosass.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.mapper.TransactionItemMapper;
import com.gustavosass.finance.service.TransactionItemService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transactionItem/")
@Tag(name = "Transaction Item")
public class TransactionItemController {

    @Autowired
    private TransactionItemService transactionItemService;

    @Autowired
    private TransactionItemMapper transactionItemMapper;

    @GetMapping("{idTransaction}/item")
    public ResponseEntity<List<TransactionItemDTO>> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(transactionItemService.findAllByTransactionId(id).stream().map(transactionItemMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionItemDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(transactionItemMapper.toDto(transactionItemService.findById(id)));
    }

}
