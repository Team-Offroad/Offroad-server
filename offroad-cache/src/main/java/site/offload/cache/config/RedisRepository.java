package site.offload.cache.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void save(String key, String value, long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.MILLISECONDS);
    }

    public String find(String key) {
        return redisTemplate.opsForValue().get(key);
    }


}
