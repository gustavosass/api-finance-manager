package com.gustavosass.finance.seeder;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.service.UserService;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        if(userService.existsByUsername("admin")){
            return;
        }
        var user = new User();
        user.setFullName("Admin");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setRoles(Set.of(RoleEnum.SUPER_ADMIN));
        userService.create(user);
    }
}