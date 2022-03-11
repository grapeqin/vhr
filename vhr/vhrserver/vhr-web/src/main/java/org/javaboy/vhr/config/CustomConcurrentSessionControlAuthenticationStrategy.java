package org.javaboy.vhr.config;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class CustomConcurrentSessionControlAuthenticationStrategy  extends ConcurrentSessionControlAuthenticationStrategy {
    /**
     * @param sessionRegistry the session registry which should be updated when the
     *                        authenticated session is changed.
     */
    public CustomConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
        super(sessionRegistry);
    }


    @Override
    protected void allowableSessionsExceeded(List<SessionInformation> sessions, int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
        //修复ConcurrentSessionControlAuthenticationStrategy中计算maximumSessionsExceededBy的问题
        if(!sessions.isEmpty()){
            sessions.sort(Comparator.comparing(SessionInformation::getLastRequest));
            //把最新登陆的session移除掉
            sessions = sessions.subList(0,sessions.size()-1);
        }
        super.allowableSessionsExceeded(sessions,allowableSessions,registry);
    }
}
