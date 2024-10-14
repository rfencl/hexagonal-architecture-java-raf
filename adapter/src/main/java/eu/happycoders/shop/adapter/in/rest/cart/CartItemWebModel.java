package eu.happycoders.shop.adapter.in.rest.cart;

import eu.happycoders.shop.model.cart.CartItem;
import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;

/**
 * Model class for returning a shopping cart line item via REST API.
 *
 * @author Sven Woltmann
 */
public record CartItemWebModel(String productId, String productName, Money price, int quantity) {

  public static CartItemWebModel fromDomainModel(CartItem item) {
    Product product = item.product();
    return new CartItemWebModel(
        product.id().value(), product.name(), product.price(), item.quantity());
  }
}
