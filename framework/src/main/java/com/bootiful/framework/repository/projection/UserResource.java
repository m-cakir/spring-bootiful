package com.bootiful.framework.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import static com.bootiful.framework.utils.StringUtil.SDF_DATE;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

public interface UserResource {

    long getId();

    String getUsername();

    @JsonFormat(shape = STRING, pattern = SDF_DATE)
    Date getUpdateTime();
}
