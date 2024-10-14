package eu.happycoders.shop.adapter.in.rest.cart;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.money.Money;
import java.util.List;

/**
 * Model class for returning a shopping cart via REST API.
 *
 * @author Sven Woltmann
 */
public record CartWebModel(List<CartItemWebModel> items, int numberOfItems, Money subTotal) {

  static CartWebModel fromDomainModel(Cart cart) {
    return new CartWebModel(
        cart.items().stream().map(CartItemWebModel::fromDomainModel).toList(),
        cart.numberOfItems(),
        cart.subTotal());
  }
}
