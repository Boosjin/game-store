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
    @NotBlank(message = "Id Can Not Be Blank")
    @Size(min = 1, max = 255, message = "Video Game Name Must Be Between 1 And 255 Characters")
    private String name;

    @Column
    @PastOrPresent(message = "Video Game's Release Date Can Not Be In Future")
    @NotNull(message = "Video Game's Release Date Can Not Be Null")
    private LocalDate releaseDate;

    @Column
    @PositiveOrZero(message = "Video Game's Price Can Not Be A Negative Value")
    @Max(value = 100, message = "The Maximum Price For A Video Game Is 100 US Dollars")
    private Float price;

    @Column
    @NotEmpty(message = "Genres Collection Can Not Be Empty")
    private Set<Genre> genres;

    @Column
    @NotEmpty(message = "Platforms Collection Can Not Be Empty")
    private Set<Platform> platforms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "videoGameName")
    @NotEmpty(message = "Versions Collection Can Not Be Empty")
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
