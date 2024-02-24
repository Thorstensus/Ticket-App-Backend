package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class CreateNewsResponseDTO extends ResponseDTO {

  private Long id;

  private String title;

  private String content;

  public CreateNewsResponseDTO() {
  }

  public CreateNewsResponseDTO(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "CreateNewsResponseDTO{"
         + "id="
         + id
         + ", title='"
         + title
         + '\''
         + ", content='"
         + content
         + '\''
         + '}';
  }
}
