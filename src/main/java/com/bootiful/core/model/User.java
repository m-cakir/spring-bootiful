package com.bootiful.core.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getLoginFailuresCount() {
        return loginFailuresCount;
    }

    public void setLoginFailuresCount(int loginFailuresCount) {
        this.loginFailuresCount = loginFailuresCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginDetails() {
        return lastLoginDetails;
    }

    public void setLastLoginDetails(String lastLoginDetails) {
        this.lastLoginDetails = lastLoginDetails;
    }
}
