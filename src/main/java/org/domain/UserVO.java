package org.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String gender;
    private String role;
    private String email;

    public UserVO(String id) {
    }
}

