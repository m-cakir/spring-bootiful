package com.bootiful.web.spec;

import com.bootiful.framework.domain.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "username", params = "username", spec = Like.class),
        @Spec(path = "enabled", params = "enabled", spec = Equal.class),
        @Spec(path = "role.id", params = "role", spec = Equal.class)
})
public interface UserSpec extends Specification<User> {
}
