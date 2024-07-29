package site.offload.api.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import site.offload.api.exception.UnAuthorizedException;
import site.offload.enums.response.ErrorMessage;

@Component
public class PrincipalHandler {
    private static final String ANONYMOUS_USER = "anonymousUser";

    public static Long getMemberIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        isPrincipalNull(principal);
        return Long.valueOf(principal.toString());
    }

    public static void isPrincipalNull(
            final Object principal
    ) {
        if (principal.toString().equals(ANONYMOUS_USER)) {
            throw new UnAuthorizedException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION);
        }
    }
}
