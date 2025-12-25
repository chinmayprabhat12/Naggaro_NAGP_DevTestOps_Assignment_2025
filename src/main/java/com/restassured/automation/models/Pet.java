package com.restassured.automation.models;

import java.util.List;
import java.util.Arrays;

public class Pet {
	private Long id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private List<Tag> tags;
	private String status;

	public static class Category {
		private Long id;
		private String name;

		public Category() {
		}

		public Category(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class Tag {
		private Long id;
		private String name;

		public Tag() {
		}

		public Tag(Long id, String name) {
			this.id = id;
			this.name = name;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	// Constructors
	public Pet() {
	}

	public Pet(Long id, String categoryName, String name, List<String> photoUrls, List<String> tagNames,
			String status) {
		this.id = id;
		this.category = new Category(1L, categoryName); // Create Category object
		this.name = name;
		this.photoUrls = photoUrls;

		// Convert tag names to Tag objects
		this.tags = Arrays.asList(new Tag(1L, String.join(",", tagNames)));
		this.status = status;
	}

	// Simple constructor for basic pets
	public Pet(Long id, String name, String status) {
		this.id = id;
		this.category = new Category(1L, "Pets");
		this.name = name;
		this.photoUrls = Arrays.asList("default.jpg");
		this.tags = Arrays.asList(new Tag(1L, "pet"));
		this.status = status;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}