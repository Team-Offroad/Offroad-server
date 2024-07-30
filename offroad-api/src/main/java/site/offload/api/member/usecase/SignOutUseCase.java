package site.offload.api.member.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.member.dto.request.SignOutRequest;
import site.offload.cache.config.RedisRepository;

@Service
@RequiredArgsConstructor
public class SignOutUseCase {

    private final RedisRepository redisRepository;

    public void execute(SignOutRequest request) {
        redisRepository.delete(request.refreshToken());
    }
}
