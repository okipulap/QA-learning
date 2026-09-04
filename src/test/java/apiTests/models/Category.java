package apiTests.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Long id;

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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private final Category category = new Category();

		public Builder id(Long id) {
			category.id = id;
			return this;
		}

		public Builder name(String name) {
			category.name = name;
			return this;
		}

		public Category build() {
			return category;
		}
	}
}
