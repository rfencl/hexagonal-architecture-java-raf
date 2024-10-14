package eu.happycoders.shop.adapter.in.rest.cart;

import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

import eu.happycoders.shop.model.cart.Cart;
import eu.happycoders.shop.model.cart.CartItem;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public final class CartsControllerAssertions {

  private CartsControllerAssertions() {}

  public static void assertThatResponseIsCart(Response response, Cart cart) {
    assertThat(response.statusCode()).isEqualTo(OK.getStatusCode());

    JsonPath json = response.jsonPath();

    for (int i = 0; i < cart.items().size(); i++) {
      CartItem item = cart.items().get(i);

      String itemPrefix = "items[%d].".formatted(i);

      assertThat(json.getString(itemPrefix + "productId")).isEqualTo(item.product().id().value());
      assertThat(json.getString(itemPrefix + "productName")).isEqualTo(item.product().name());
      assertThat(json.getString(itemPrefix + "price.currency"))
          .isEqualTo(item.product().price().currency().getCurrencyCode());
      assertThat(json.getDouble(itemPrefix + "price.amount"))
          .isEqualTo(item.product().price().amount().doubleValue());
      assertThat(json.getInt(itemPrefix + "quantity")).isEqualTo(item.quantity());
    }

    assertThat(json.getInt("numberOfItems")).isEqualTo(cart.numberOfItems());

    if (cart.subTotal() != null) {
      assertThat(json.getString("subTotal.currency"))
          .isEqualTo(cart.subTotal().currency().getCurrencyCode());
      assertThat(json.getDouble("subTotal.amount"))
          .isEqualTo(cart.subTotal().amount().doubleValue());
    } else {
      assertThat(json.getString("subTotal")).isNull();
    }
  }
}
