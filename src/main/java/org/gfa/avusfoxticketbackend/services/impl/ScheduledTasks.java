package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private final CartRepository cartRepository;

    @Autowired
    public ScheduledTasks(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendRemainder() {
        List<Cart> cartsInDatabase = cartRepository.findAll();
        Date currentTime = new Date(System.currentTimeMillis());
        for(Cart cart : cartsInDatabase) {
            if (((currentTime.getTime() - cart.getLastActivity().getTime()) / (60*1000)) > 1) {
                cart.setLastActivity(currentTime);
                cartRepository.save(cart);
                System.out.println(cart.getUser().getEmail());
            }
        }
    }
}
