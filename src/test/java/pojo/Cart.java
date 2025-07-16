package pojo;

import java.util.List;

public class Cart {
	private int id;
	private int userId;
	private List<Product> products;

	// Constructor
	public Cart (int id, int userId, List<Product> products) {
		this.id = id;
		this.userId = userId;
		this.products = products;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
