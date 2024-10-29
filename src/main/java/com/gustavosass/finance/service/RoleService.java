package com.gustavosass.finance.service;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.Role;
import com.gustavosass.finance.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(RoleEnum name){
        return roleRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    public Role create(Role role){
        return roleRepository.save(role);
    }
}
