/*package com.golfsprint.GolfSprint.Shop;

import com.golfsprint.GolfSprint.Company.Company;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "shop")
public class Product {
    @Id
    @SequenceGenerator(
            name="product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Column(name = "product_id")
    private Long productId;
    private String productName;
    private String description;
    private Integer price;
    private String currency;
    @Lob
    private String image;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id",referencedColumnName = "company_id") // This assumes a column "company_id" in the User table
    private Company company;
    private String category;

    public Product(String productName, String description, Integer price, String currency, String image, Company company, String category){
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.image = image;
        this.company = company;
        this.category = category;
    }
    public Product(String productName, String description, Integer price, String currency, String image, String category){
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.image = image;
        this.category = category;
    }
}
*/