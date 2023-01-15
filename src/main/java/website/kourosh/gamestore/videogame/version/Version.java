package website.kourosh.gamestore.videogame.version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@IdClass(VersionCompositeKey.class)
public final class Version {

    @Id
    @Column(columnDefinition = "VARCHAR(255)")
    @JsonIgnore
    private String videoGameName;

    @Id
    @NotBlank
    private String version;

    @Column
    @NotNull
    private LocalDate releaseDate;
}
