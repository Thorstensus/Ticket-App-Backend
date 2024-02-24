package org.gfa.avusfoxticketbackend.dtos;

public class CreateNewsRequestDTO {
    private String title;

    private String content;

    public CreateNewsRequestDTO() {
    }

    public CreateNewsRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
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
        return "CreateNewsRequestDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
