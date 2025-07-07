package com.otur.otur.service;

import com.otur.otur.entity.User;
import com.otur.otur.repository.UserRepository;
import com.otur.otur.vo.CurrentUser;
import com.otur.otur.vo.LoginRequestVO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CurrentUser register(LoginRequestVO vo) {
        if (userRepo.findByUsername(vo.getUsername()).isPresent()) {
            return new CurrentUser(false, null);
        }
        User user = new User();
        user.setUsername(vo.getUsername());
        user.setPassword(passwordEncoder.encode(vo.getPassword()));
        user.setAdmin(vo.isAdmin());
        User saved = userRepo.save(user);

        return new CurrentUser(true, saved);
    }

    @Transactional
    public CurrentUser login(LoginRequestVO vo) {
        User user = userRepo.findByUsername(vo.getUsername())
                .orElse(null);
        if (user == null || !passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            return new CurrentUser(false, null);
        }

        return new CurrentUser(true, user);
    }
}
