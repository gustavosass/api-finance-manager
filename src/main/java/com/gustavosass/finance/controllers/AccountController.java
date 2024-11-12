package com.gustavosass.finance.controllers;

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

import com.gustavosass.finance.dtos.AccountDTO;
import com.gustavosass.finance.mapper.AccountMapper;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.service.AccountService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@Tag(name = "Account")
public class AccountController {

	@Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll(){
        List<AccountDTO> AccountsDto = accountService.findAll().stream().map(accountMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(AccountsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findById(@PathVariable Long id){
        AccountDTO AccountDTO = accountMapper.toDto(accountService.findById(id));
        return ResponseEntity.ok(AccountDTO);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDto) {
        Account Account = accountMapper.toEntity(accountDto);
        Account AccountCreated = accountService.create(Account);
        return ResponseEntity.ok(accountMapper.toDto(AccountCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@PathVariable Long id, @RequestBody AccountDTO AccountDTO){
        Account account = accountMapper.toEntity(AccountDTO);
        Account accountCreated = accountService.update(id, account);
        return ResponseEntity.ok(accountMapper.toDto(accountCreated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
