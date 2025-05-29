package com.recruiting.center.crm.configuration;

import com.recruiting.center.crm.entity.appuser.AppUser;
import com.recruiting.center.crm.repository.appuser.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;


@Configuration
@ComponentScan(value = "com/recruiting/center/crm")
@EnableCaching
public class AppConfiguration {

    @Value("${front.end.server}")
    private String frontEndServer;


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }


    @Bean
    public UserDetailsService userDetailsService(AppUserRepository appUserRepository) {
        return username -> {
            Optional<AppUser> user = appUserRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return new User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
        };
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Застосувати CORS до всіх шляхів, що починаються з /api/
                        .allowedOrigins(frontEndServer) // Дозволити запити з вашого Vue.js сервера розробки (змініть порт 8080, якщо ваш Vue dev-сервер використовує інший)
                        // .allowedOrigins("*") // АБО: Дозволити запити з будь-якого походження (менш безпечно для production)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Дозволені HTTP методи
                        .allowedHeaders("*") // Дозволені заголовки запиту
                        .allowCredentials(true) // Дозволити надсилання куків/авторизаційних заголовків
                        .maxAge(3600); // Час кешування результатів попереднього запиту OPTIONS
            }
        };
    }
}
