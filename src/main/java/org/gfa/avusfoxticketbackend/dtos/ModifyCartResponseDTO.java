package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;


import java.util.List;

public class ModifyCartResponseDTO extends ResponseDTO {
  private List<CartProductDTO> cartProduct;

  public ModifyCartResponseDTO() {
  }

  public ModifyCartResponseDTO(List<CartProductDTO> cartProduct) {
    this.cartProduct = cartProduct;
  }

  public List<CartProductDTO> getCartProduct() {
    return cartProduct;
  }

  public void setCartProduct(List<CartProductDTO> cartProduct) {
    this.cartProduct = cartProduct;
  }

  @Override
  public String toString() {
    return "ModifyCartResponseDTO{" +
            "cartProduct=" + cartProduct +
            '}';
  }
}
