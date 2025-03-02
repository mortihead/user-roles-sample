package ru.ibs.service;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.entity.User;
import ru.ibs.enums.RoleEnum;
import ru.ibs.repository.UserRepository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    public static final String USER_ID_HTTP_HEADER_NAME = "USER_UID";

    private final UserRepository userRepository;

    private LoadingCache<String, User> userCache;

    @PostConstruct
    public void initCache() {
        userCache = CacheBuilder.newBuilder()
                .maximumSize(100) // Максимальное количество записей в кэше
                .expireAfterWrite(1, TimeUnit.HOURS) // Время жизни записи в кэше
                .build(new CacheLoader<>() {
                    @Override
                    public User load(String uid) {
                        return userRepository.findByUid(uid)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with uid: " + uid));
                    }
                });
    }

    /**
     * Получение пользователя из кэша или базы данных.
     *
     * @param request Идентификатор пользователя.
     * @return Пользователь или null, если произошла ошибка.
     */
    public User getUser(HttpServletRequest request) {
        String userUid = request.getHeader(USER_ID_HTTP_HEADER_NAME);
        if (Strings.isNullOrEmpty(userUid))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Anonymous access denied!");
        try {
            return userCache.get(userUid);
        } catch (ExecutionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with uid: " + userUid + " not found!");
        }
    }

    /**
     * Проверяет, является ли пользователь менеджером Toyota.
     *
     * @param user пользователь.
     * @return true, если пользователь является менеджером Toyota, иначе false.
     */
    public boolean isToyotaManager(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> RoleEnum.MANAGER_TOYOTA.getRoleName().equals(role.getName()));
    }

    /**
     * Проверяет, является ли пользователь менеджером Nissan.
     *
     * @param user пользователь.
     * @return true, если пользователь является менеджером Nissan, иначе false.
     */
    public boolean isNissanManager(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> RoleEnum.MANAGER_NISSAN.getRoleName().equals(role.getName()));
    }

    /**
     * Проверяет, является ли пользователь админом.
     *
     * @param user пользователь.
     * @return true, если пользователь является админом, иначе false.
     */
    public boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> RoleEnum.ADMIN.getRoleName().equals(role.getName()));
    }


}
