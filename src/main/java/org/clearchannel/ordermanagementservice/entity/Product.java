package org.clearchannel.ordermanagementservice.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "product name is required")
	@Basic(optional = false)
	private String name;
	private String description;
	private double price;
	private int quantity;

	public Product() {

	}

	public static class ProductBuilder {

		private String name;
		private String description;
		private double price;
		private int quantity;

		public ProductBuilder(String name, double price) {
			this.name = name;
			this.price = price;

		}

		public ProductBuilder quantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public ProductBuilder description(String description) {
			this.description = description;
			return this;
		}

		public Product build() {
			return new Product(this);
		}

	}

	public Product(ProductBuilder builder) {

		this.name = builder.name;
		this.price = builder.price;
		this.description = builder.description;
		this.quantity = builder.quantity;

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

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	//setter
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + "]";
	}
	
	

}
