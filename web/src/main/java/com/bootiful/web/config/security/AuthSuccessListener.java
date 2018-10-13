package com.bootiful.web.config.security;

import com.bootiful.framework.domain.User;
import com.bootiful.framework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.bootiful.web.util.SessionKey.CURRENT_USER;

@Component
public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Transactional
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username);
        if (user != null) {
            String strDetail = null;
            Object details = event.getAuthentication().getDetails();
            if (details != null) {
                if (details instanceof WebAuthenticationDetails)
                    strDetail = "IP: " + ((WebAuthenticationDetails) details).getRemoteAddress() +
                            " SessionID: " + ((WebAuthenticationDetails) details).getSessionId();
                else
                    strDetail = details.toString();
            }

            httpSession.setAttribute(CURRENT_USER, user);

            user.setLastLoginDetails(strDetail);
            user.setLastLoginTime(new Date());
            user.setLoginFailuresCount(0);

            userRepository.save(user);
        }
    }

}
