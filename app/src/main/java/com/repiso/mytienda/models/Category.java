package com.repiso.mytienda.models;

public class Category {
    int id;
    String name, icon, color, description;

    public Category() {
    }

    /**
     *
     * @param id id
     * @param name name
     * @param icon icon
     * @param color color
     * @param description description
     */
    public Category(int id, String name, String icon, String color, String description) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
