/*package com.golfsprint.GolfSprint.Delivery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.golfsprint.GolfSprint.Shop.Product;
import com.golfsprint.GolfSprint.User.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="delivery")
public class Delivery {
    @Id
    @SequenceGenerator(
            name="delivery_sequence",
            sequenceName = "delivery_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "delivery_sequence"
    )
    @Column(name = "delivery_id")
    private Long deliveryId;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
    private String address;
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id",referencedColumnName = "product_id")
    private Product product;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;
    private Boolean delivered = false;

    public Delivery(User user, String address, Product product){
        this.user = user;
        this.address = address;
        this.product = product;
        this.time = LocalDateTime.now();
    }
    public Delivery(String address){
        this.address = address;
        this.time = LocalDateTime.now();
    }
}
*/