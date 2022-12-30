package ru.gb.internetshop.core.controllers;

import api.DeliveryAdressDto;
import api.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.core.converters.OrderConverter;
import ru.gb.internetshop.core.services.OrderService;
//import ru.gb.internetshop.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username){
        return orderService.findUserOrders(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username, @RequestBody DeliveryAdressDto deliveryAdressDto) {
        System.out.println(deliveryAdressDto);
        orderService.createOrder(username,deliveryAdressDto);
    }
}
