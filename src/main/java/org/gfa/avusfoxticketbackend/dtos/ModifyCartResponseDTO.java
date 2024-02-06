package org.gfa.avusfoxticketbackend.dtos;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ModifyCartResponseDTO extends ResponseDTO {
  private List<CartProductDTO> cartProducts;

  public ModifyCartResponseDTO() {}

  public ModifyCartResponseDTO(List<CartProductDTO> cartProduct) {
    this.cartProducts = cartProduct;
  }

  public List<CartProductDTO> getCartProduct() {
    return cartProducts;
  }

  public void setCartProduct(List<CartProductDTO> cartProduct) {
    this.cartProducts = cartProduct;
  }

  @Override
  public String toString() {
    return "ModifyCartResponseDTO{" + "cartProducts=" + cartProducts + '}';
  }
}
