package website.kourosh.gamestore.videogame;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import website.kourosh.gamestore.videogame.version.Version;

import java.time.LocalDate;
import java.util.List;
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
    @NotNull
    private LocalDate releaseDate;

    @Column
    @PositiveOrZero
    @Min(value = 1, message = "The Minimum Price For A Video Game Is 1 US Dollar")
    @Max(value = 100, message = "The Maximum Price For A Video Game Is 100 US Dollars")
    private Float price;

    @Column
    @NotNull
    private Set<Genre> genres;

    @Column
    @NotNull
    private Set<Platform> platforms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "videoGameName")
    @NotNull
    private List<Version> versions;

    public void setVersions(List<Version> versions) {
        this.versions = versions;
        versions.forEach(version -> version.setVideoGameName(this.getName()));
    }

    enum Genre {
        ACTION, RPG, HORROR, RACING // TODO add all genres
    }

    enum Platform {
        PS4, PS5, XBOX_SERIES_X, WINDOWS, MAC, LINUX // TODO add all platforms
    }
}
