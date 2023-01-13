package website.kourosh.gamestore.videogame;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter(AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
final class VideoGame {

    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    @NotBlank
    @Size(min = 1, max = 255, message = "Video Game Name Must Be Between 1 And 255 Characters")
    private String name;

    @Column
    @PastOrPresent
    private LocalDate releaseDate;

    @Column
    @PositiveOrZero
    @Min(value = 1, message = "The Minimum Price For A Video Game Is 1 US Dollar")
    @Max(value = 100, message = "The Maximum Price For A Video Game Is 100 US Dollars")
    private Float price;

    @Column
    private Set<Genre> genres;

    enum Genre {
        ACTION, RPG, HORROR, RACING // TODO add all genres
    }
}
