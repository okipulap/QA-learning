package apiTests.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PetRequest{

	@JsonProperty("photoUrls")
	private List<String> photoUrls;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("category")
	private Category category;

	@JsonProperty("tags")
	private List<TagsItem> tags;

	@JsonProperty("status")
	private String status;

	public void setPhotoUrls(List<String> photoUrls){
		this.photoUrls = photoUrls;
	}

	public List<String> getPhotoUrls(){
		return photoUrls;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public Category getCategory(){
		return category;
	}

	public void setTags(List<TagsItem> tags){
		this.tags = tags;
	}

	public List<TagsItem> getTags(){
		return tags;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private final PetRequest request = new PetRequest();

		public Builder id(Long id) {
			request.id = id;
			return this;
		}

		public Builder name(String name) {
			request.name = name;
			return this;
		}

		public Builder category(Category category) {
			request.category = category;
			return this;
		}

		public Builder tags(List<TagsItem> tags) {
			request.tags = tags;
			return this;
		}

		public Builder status(String status) {
			request.status = status;
			return this;
		}

		public Builder photoUrls(List<String> photoUrls) {
			request.photoUrls = photoUrls;
			return this;
		}

		public PetRequest build() {
			return request;
		}
	}
}
