package eu.happycoders.shop.model.cart;

import eu.happycoders.shop.model.customer.CustomerId;
import eu.happycoders.shop.model.money.Money;
import eu.happycoders.shop.model.product.Product;
import eu.happycoders.shop.model.product.ProductId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A shopping cart of a particular customer, containing several line items.
 *
 * @author Sven Woltmann
 */
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Cart {

  @Getter private final CustomerId id; // cart ID = customer ID

  private final Map<ProductId, CartItem> items = new LinkedHashMap<>();

  public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException {
    items
        .computeIfAbsent(product.id(), ignored -> new CartItem(product))
        .increaseQuantityBy(quantity, product.itemsInStock());
  }

  public List<CartItem> items() {
    return List.copyOf(items.values());
  }

  public int numberOfItems() {
    return items.values().stream().mapToInt(CartItem::quantity).sum();
  }

  public Money subTotal() {
    return items.values().stream().map(CartItem::subTotal).reduce(Money::add).orElse(null);
  }
}
