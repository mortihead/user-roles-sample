package ru.ibs.service;

import com.google.common.base.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.entity.Car;
import ru.ibs.entity.User;
import ru.ibs.enums.BrandEnum;
import ru.ibs.repository.CarRepository;
import ru.ibs.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static ru.ibs.service.UserService.USER_ID_HTTP_HEADER_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final CarRepository carRepository;

    public List<Car> findAll(HttpServletRequest request) {
        //  User user = userService.getUser(request);
        String uid = request.getHeader(USER_ID_HTTP_HEADER_NAME);
        if (Strings.isNullOrEmpty(uid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Anonymous access denied!");
        }
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with uid: " + uid));
        if (userService.isToyotaManager(user)) {
            return carRepository.findByBrand(BrandEnum.TOYOTA.getBrandName());
        } else if (userService.isNissanManager(user)) {
            return carRepository.findByBrand(BrandEnum.NISSAN.getBrandName());
        } else if (userService.isAdmin(user)) {
            return carRepository.findAll();
        } else return Collections.emptyList();
    }

    public Optional<Car> findById(Integer id) {
        return carRepository.findById(id);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void deleteById(Integer id) {
        carRepository.deleteById(id);
    }
}