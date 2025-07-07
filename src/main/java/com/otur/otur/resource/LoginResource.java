package com.otur.otur.resource;

import com.otur.otur.service.LoginService;
import com.otur.otur.vo.CurrentUser;
import com.otur.otur.vo.LoginRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginResource {

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<CurrentUser> register(@RequestBody LoginRequestVO req) {
        CurrentUser resp = loginService.register(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<CurrentUser> login(@RequestBody LoginRequestVO req) {
        CurrentUser resp = loginService.login(req);
        return ResponseEntity.ok(resp);
    }
}
