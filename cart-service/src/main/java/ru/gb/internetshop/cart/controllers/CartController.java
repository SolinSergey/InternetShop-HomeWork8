package ru.gb.internetshop.cart.controllers;

import api.CartDto;
import api.StringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.cart.converters.CartConverter;
import ru.gb.internetshop.cart.service.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId(){
        StringResponse newUUID=new StringResponse(UUID.randomUUID().toString());
        return newUUID;
    }

    @GetMapping("/{guestCardId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long productId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.addToCart(currentCardId,productId);
    }

    @GetMapping("/{guestCardId}/sub/{productId}")
    public void subProductFromCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long productId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.subToCart(currentCardId,productId);
    }

    @DeleteMapping("/{guestCardId}/remove/{id}")
    public void removeItem(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long id) {
        String currentCardId = selectCartId(username,guestCardId);
        cartService.removeItem(currentCardId,id);
    }

    @GetMapping("/{guestCardId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId){
        String currentCardId = selectCartId(username,guestCardId);
        CartDto cartDto=cartConverter.entityToCartDto(cartService.getCurrentCart(currentCardId));
        return cartDto;
    }

    @GetMapping("/{guestCardId}/clear")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.clearCart(currentCardId);
    }

    private String selectCartId(String username, String guestCartId) {
        if (username != null) {
            return username;
        }
        return guestCartId;
    }

    @GetMapping("/merge/{guestCartId}/{username}")
    public ResponseEntity mergeCart(@PathVariable String username, @PathVariable String guestCartId){
        cartService.mergeCarts(username,guestCartId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
