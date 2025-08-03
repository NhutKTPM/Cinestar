package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.ConcessionEntity;
import com.da3.MovieTicket.entity.GiftCardEntity;
import com.da3.MovieTicket.entity.SeatEntity;
import com.da3.MovieTicket.model.Cart;
import com.da3.MovieTicket.model.CartConcessionItem;
import com.da3.MovieTicket.model.CartGiftCardUsage;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class CartService {
    private Cart cart = new Cart();

    public void addSeat(SeatEntity seat) {
        if (!cart.getSelectedSeats().contains(seat)) {
            cart.getSelectedSeats().add(seat);
        }
    }

    public void removeSeat(SeatEntity seat) {
        cart.getSelectedSeats().remove(seat);
    }

    public void addConcession(ConcessionEntity concession) {
        // Check if concession already exists in cart
        CartConcessionItem existingItem = cart.getConcessionItems().stream()
                .filter(item -> item.getConcession().getConcessionId()
                        .equals(concession.getConcessionId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            CartConcessionItem newItem = new CartConcessionItem();
            newItem.setConcession(concession);
            newItem.setQuantity(1);
            cart.getConcessionItems().add(newItem);
        }
    }

    public void removeConcession(ConcessionEntity concession) {
        cart.getConcessionItems().removeIf(item ->
                item.getConcession().getConcessionId().equals(concession.getConcessionId()));
    }

//    public void updateConcessionQuantity(Long concessionId, int quantity) {
//        cart.getConcessionItems().stream()
//                .filter(item -> item.getConcession().getConcessionId().equals(concessionId))
//                .findFirst()
//                .ifPresent(item -> {
//                    if (quantity <= 0) {
//                        cart.getConcessionItems().remove(item);
//                    } else {
//                        item.setQuantity(quantity);
//                    }
//                });
//    }

    public void decreaseConcessionQuantityByOne(Long concessionId) {
        cart.getConcessionItems().stream()
                .filter(item -> item.getConcession().getConcessionId().equals(concessionId))
                .findFirst()
                .ifPresent(item -> {
                    int quantity = item.getQuantity() - 1;
                    if (quantity <= 0) {
                        cart.getConcessionItems().remove(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                });
    }

    public void addGiftCard(GiftCardEntity giftCard) {
        CartGiftCardUsage usage = new CartGiftCardUsage();
        usage.setGiftCard(giftCard);
        if (giftCard.getCurrentBalance() <= cart.getTotal()) {
            usage.setUsedAmount(giftCard.getCurrentBalance());
        } else {
            usage.setUsedAmount(cart.getTotal());
        }
        cart.getGiftCardUsages().add(usage);
    }

    public void removeGiftCard(GiftCardEntity giftCard) {
        // Check if the total started with zero
        boolean totalStartedWithZero = cart.getTotal() == 0;
        
        cart.getGiftCardUsages().removeIf(usage ->
                usage.getGiftCard().getGiftCardId().equals(giftCard.getGiftCardId()));
        
        // If the remaining gift cards in the cart still have unused balance, use them
        if (totalStartedWithZero) {
            for (CartGiftCardUsage usage : cart.getGiftCardUsages()) {
                if (usage.getGiftCard().getCurrentBalance() - usage.getUsedAmount() <= cart.getTotal()) {
                    usage.setUsedAmount(usage.getGiftCard().getCurrentBalance());
                } else {
                    usage.setUsedAmount(usage.getUsedAmount() + cart.getTotal());
                }
                if (cart.getTotal() == 0) { break; }
            }
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        cart = new Cart();
    }

}
