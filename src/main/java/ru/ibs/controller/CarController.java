package ru.ibs.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ibs.entity.Car;
import ru.ibs.service.CarService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "api/v1/", produces = "application/json")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public List<Car> getAllCars(HttpServletRequest request) {
        return carService.findAll(request);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
        return carService.findById(id)
                .map(car -> ResponseEntity.ok().body(car))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car savedCar = carService.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}