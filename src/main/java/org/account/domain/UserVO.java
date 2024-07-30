package org.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserVO {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String gender;
    private String role;
    private String email;
    private String birthYear;
    private String region;

    public UserVO(String id) {
    }
}

