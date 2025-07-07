package com.otur.otur.vo;

import com.otur.otur.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentUser {
    private boolean authenticated;
    private User user;
}
