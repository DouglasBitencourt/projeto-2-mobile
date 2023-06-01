package com.example.projeto2;
public class MenuItem {
    private String name;
    private String description;
    private double price;
    private boolean hasGluten;
    private int calories;
    private String imageURL;

    // Construtor, getters e setters

    // Exemplo de implementação dos métodos
    public MenuItem(String name, String description, double price, boolean hasGluten, int calories) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.hasGluten = hasGluten;
        this.calories = calories;
        this.imageURL = imageURL;
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

    public boolean hasGluten() {
        return hasGluten;
    }

    public int getCalories() {
        return calories;
    }

    public String getImageURL() {
        return imageURL;
    }
}
