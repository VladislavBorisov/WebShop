package com.web.shop.domain;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private String name;
    private double price;
    private String description;
    private String category;
    private String image;
    private int amount;

    public Product() {
    }

    public Product(int productId, String name, double price, String description, String category,String image, int amount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.amount = amount;
    }

    public Product(String name, double price, String description, String category, String image, int amount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Product o = (Product) obj;

        if (productId != o.productId) {
            return false;
        }
        if (price != o.price) {
            return false;
        }
        if (amount != o.amount) {
            return false;
        }
        if (name == null) {
            if (o.name != null) {
                return false;
            }
        } else if (!name.equals(o.name)) {
            return false;
        }
        if (description == null) {
            if (o.description != null) {
                return false;
            }
        } else if (!description.equals(o.description)) {
            return false;
        }
        if (category == null) {
            if (o.category != null) {
                return false;
            }
        } else if (!category.equals(o.category)) {
            return false;
        }
        if (image == null) {
            if (o.image != null) {
                return false;
            }
        } else if (!image.equals(o.image)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        result = prime * result + productId;
        result = prime * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (description != null ? description.hashCode() : 0);
        result = prime * result + (category != null ? category.hashCode() : 0);
        result = prime * result + (image != null ? image.hashCode() : 0);
        result = prime * result + amount;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(": productId=" + productId);
        sb.append(", name=" + name);
        sb.append(", price=" + price);
        sb.append(", description=" + description);
        sb.append(", category=" + category);
        sb.append(", image=" + image);
        sb.append(", amount=" + amount);
        return sb.toString();
    }
}
