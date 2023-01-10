package website.kourosh.gamestore.videogame;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
final class VideoGame {

    @Id
    @Column
    @NotBlank
    private String name;

    @Column
    @PastOrPresent
    private LocalDate releaseDate;

    @Column
    @PositiveOrZero
    private Float price;
}
