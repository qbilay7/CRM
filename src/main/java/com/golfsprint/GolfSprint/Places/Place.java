/*package com.golfsprint.GolfSprint.Places;

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
@Table(name="place")
public class Place {
    @Id
    @SequenceGenerator(
            name="place_sequence",
            sequenceName = "place_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "place_sequence"
    )
    @Column(name = "place_id")
    private Long placeId;
    private String placeName;
    private String description;
    @Lob
    private Byte[] placeImage;
    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id",referencedColumnName = "company_id") // This assumes a column "company_id" in the User table
    private Company company;

    public Place(String placeName, String description, Byte[] placeImage, Company company){
        this.placeName = placeName;
        this.description = description;
        this.placeImage = placeImage;
        this.company = company;
    }
    public Place(String placeName, String description, Byte[] placeImage){
        this.placeName = placeName;
        this.description = description;
        this.placeImage = placeImage;
    }
    public Place(String placeName, String description) {
        this.placeName = placeName;
        this.description = description;
    }
    public Place(String placeName, String description, Company company) {
        this.placeName = placeName;
        this.description = description;
        this.company = company;
    }
}
*/