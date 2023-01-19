package website.kourosh.gamestore.videogame.version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
    @NotBlank(message = "Version Can Not Be Blank")
    private String version;

    @Column
    @NotNull(message = "Version Release Date Can Not Be Null")
    @PastOrPresent(message = "Version Release Date Can Not Be In Future")
    private LocalDate releaseDate;
}
