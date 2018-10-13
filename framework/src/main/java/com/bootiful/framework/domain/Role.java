package com.bootiful.framework.domain;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Role extends BaseModel {

    private String name;

}
