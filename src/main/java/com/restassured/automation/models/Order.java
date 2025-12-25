package com.restassured.automation.models;

public class Order {
	private Long id;
	private Long petId;
	private Integer quantity;
	private String status;
	private Boolean complete;

	// Constructors
	public Order() {
	}

	public Order(Long id, Long petId, Integer quantity, String status, Boolean complete) {
		this.id = id;
		this.petId = petId;
		this.quantity = quantity;
		this.status = status;
		this.complete = complete;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
}