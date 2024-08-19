package site.offload.cache.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.cache.config.RedisRepository;

@Component
@RequiredArgsConstructor
public class MemberStatusCacheService {

    private final RedisRepository redisRepository;
    private static final String DEFAULT_MEMBER_STATUS_KEY_PREFIX = "member_status:";

    public void saveMemberStatus(final String memberStatus, final Long memberId) {
        redisRepository.save(DEFAULT_MEMBER_STATUS_KEY_PREFIX + memberId.toString(), memberStatus);
    }

    public String find(final Long memberId) {
        return redisRepository.find(DEFAULT_MEMBER_STATUS_KEY_PREFIX + memberId.toString());
    }
}
