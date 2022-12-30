package ru.gb.internetshop.cart.service;

import api.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.internetshop.cart.integrations.ProductServiceIntegration;
import ru.gb.internetshop.cart.utils.Cart;
import ru.gb.internetshop.cart.utils.CartItem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private Map<String,Cart> carts;
    private final ProductServiceIntegration productServiceIntegration;

    @PostConstruct
    public void init(){
        carts=new HashMap<>();
    }

    public Cart getCurrentCart(String cartId){
        if (!carts.containsKey(cartId)){
            Cart cart = new Cart();
            carts.put(cartId,cart);
        }
        return carts.get(cartId);
    }

    public void subToCart(String cartId,Long productId){
        for (CartItem i: getCurrentCart(cartId).getItems()){
            if (productId.equals(i.getProductId()) && i.getAmount()>1) {
                i.decrementAmount();
                break;
            }else if (productId.equals(i.getProductId())){
                getCurrentCart(cartId).remove(i);
                break;
            }
        }
    }

    public void removeItem(String cartId,Long productId){
        for (CartItem i: getCurrentCart(cartId).getItems()){
            if (productId.equals(i.getProductId())){
                getCurrentCart(cartId).remove(i);
                break;
            }
        }
    }
    public void addToCart(String cartId, Long productId){
        ProductDto p = productServiceIntegration.findById(productId);
        getCurrentCart(cartId).add(p);
    }

    public void clearCart(String cartId){
        getCurrentCart(cartId).clear();
    }

    public void mergeCarts(String username, String guestCartId){
        Cart userCart=getCurrentCart(username);
        Cart guestCart=getCurrentCart(guestCartId);
        if (guestCart.getItems().size()!=0){
            for (CartItem g:guestCart.getItems()){
                ProductDto productDto=new ProductDto();
                productDto.setId(g.getProductId());
                productDto.setTitle(g.getProductTitle());
                productDto.setPrice(g.getPrice());
                for (int i=0;i<g.getAmount();i++){
                    userCart.add(productDto);
                }
            }
        }
    }
}
