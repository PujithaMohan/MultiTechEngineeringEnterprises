package com.multitech.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double price;
    private String description;
    private String imageName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id",referencedColumnName = "category_id")
    private Category category;

}
