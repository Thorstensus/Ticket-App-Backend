package org.gfa.avusfoxticketbackend.dtos;

import java.util.Objects;
import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ResponseStatusMessageDTO extends ResponseDTO {
  private String statusMessage;

  public ResponseStatusMessageDTO() {}

  public ResponseStatusMessageDTO(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  @Override
  public String toString() {
    return "ResponseStatusMessageDTO{" + "statusMessage='" + statusMessage + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ResponseStatusMessageDTO that)) {
      return false;
    }
    return Objects.equals(getStatusMessage(), that.getStatusMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStatusMessage());
  }
}
