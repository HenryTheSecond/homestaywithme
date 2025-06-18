package com.homestaywithme.app.application.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid.dto.HomestayDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.*;
import java.time.Duration;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        var config = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        var objectMapper = new ObjectMapper();

        // Default Configuration
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(15))
                .serializeValuesWith(fromSerializer(defaultSerializer(objectMapper)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withCacheConfiguration(Constant.HOMESTAY_CACHE, defaultConfig.serializeValuesWith(fromSerializer(homestayDtoSerializer(objectMapper))))
                .build();
    }

    private GenericJackson2JsonRedisSerializer defaultSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    private Jackson2JsonRedisSerializer<HomestayDto> homestayDtoSerializer(ObjectMapper objectMapper) {
        return new Jackson2JsonRedisSerializer<>(objectMapper, HomestayDto.class);
    }
}
