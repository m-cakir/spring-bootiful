package com.bootiful.web.config.security;

import com.bootiful.framework.domain.User;
import com.bootiful.framework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class AuthFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();

        User user = userRepository.findByUsername(username);
        if (user != null) {

            int loginFailures = user.getLoginFailuresCount() + 1;
            if (loginFailures >= 5) {
                user.setEnabled(false);
            }

            String strDetail = null;
            Object details = event.getAuthentication().getDetails();
            if (details != null) {
                if (details instanceof WebAuthenticationDetails)
                    strDetail = "IP: " + ((WebAuthenticationDetails) details).getRemoteAddress() +
                            " SessionID: " + ((WebAuthenticationDetails) details).getSessionId();
                else
                    strDetail = details.toString();
            }

            user.setLastLoginDetails(strDetail);
            user.setLastLoginTime(new Date());
            user.setLoginFailuresCount(loginFailures);

            userRepository.save(user);
        }

    }
}
