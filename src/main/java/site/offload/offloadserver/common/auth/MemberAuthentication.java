package site.offload.offloadserver.common.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import site.offload.offloadserver.db.member.entity.Member;

import java.util.Collection;

public class MemberAuthentication extends UsernamePasswordAuthenticationToken {
    public MemberAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities){
        super(principal, credentials, authorities);
    }

    public static MemberAuthentication createMemberAuthentication(Long memberId) {
        return new MemberAuthentication(memberId, null, null);
    }
}
