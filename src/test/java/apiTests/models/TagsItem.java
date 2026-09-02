package apiTests.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Integer id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private final TagsItem item = new TagsItem();

		public Builder id(Integer id) {
			item.id = id;
			return this;
		}

		public Builder name(String name) {
			item.name = name;
			return this;
		}

		public TagsItem build() {
			return item;
		}
	}
}
