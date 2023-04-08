package com.multitech.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double price;
    private String description;
    private String imageName;
    private int categoryId;
}
