package org.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountVO {
    private Long id;
    private String title;
    private Integer total;
    private Integer income;
    private Integer expense;
    private String category;
    private String description;
    private Date date;
    private String userId;


    public String getUserId() {
        return userId;
    }

}
