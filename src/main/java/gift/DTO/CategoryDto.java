package gift.DTO;

public class CategoryDto {

  private Long id;
  private String name;
  private String color;
  private String imageUrl;
  private String description;

  public CategoryDto() {
  }

  public CategoryDto(Long id, String name, String color, String imageUrl, String description){
    this.id=id;
    this.name=name;
    this.color=color;
    this.imageUrl=imageUrl;
    this.description=description;
  }

  public CategoryDto(String name, String color, String imageUrl, String description){
    this.name=name;
    this.color=color;
    this.imageUrl=imageUrl;
    this.description=description;
  }

  public void setId(Long id){this.id=id;}

  public Long getId(){return this.id;}

  public void setName(String name){this.name=name;}

  public String getName(){return this.name;}

  public void setColor(String color){this.color=color;}

  public String getColor(){return this.color;}

  public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}

  public String getImageUrl(){return this.imageUrl;}

  public void setDescription(String description){this.description=description;}

  public String getDescription(){return this.description;}
}
