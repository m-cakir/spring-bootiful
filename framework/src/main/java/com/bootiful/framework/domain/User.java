package com.bootiful.framework.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;


@Data
@Entity
public class User extends BaseModel {

    private String username;

    private String password;

    private boolean enabled;

    @ManyToOne
    private Role role;

    private int loginFailuresCount;

    private Date lastLoginTime;

    private String lastLoginDetails;

}
