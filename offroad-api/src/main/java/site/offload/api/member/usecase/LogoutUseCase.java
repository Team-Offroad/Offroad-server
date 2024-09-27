package site.offload.api.member.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.member.dto.request.LogoutRequest;
import site.offload.cache.config.RedisRepository;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final RedisRepository redisRepository;

    public void execute(LogoutRequest request) {
        redisRepository.delete(request.refreshToken());
    }
}
