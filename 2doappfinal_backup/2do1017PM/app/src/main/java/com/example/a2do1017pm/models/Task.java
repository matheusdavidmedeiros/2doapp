package com.example.a2do1017pm.models;

public class Task {
    private int id;
    private String name;
    private String description;

    // Construtor vazio
    public Task() {
    }

    // Construtor completo
    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Construtor sem ID (usado para adicionar novas tarefas)
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters e Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
