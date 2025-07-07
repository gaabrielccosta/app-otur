package com.otur.otur.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestVO {

    private String username;
    private String password;
    private boolean admin;
}
