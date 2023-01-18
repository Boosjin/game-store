package website.kourosh.gamestore.videogame;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service
class VideoGameService {

    private final VideoGameRepository videoGameRepository;

    @Autowired
    VideoGameService(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }

    @Transactional
    ResponseEntity<String> addNewVideoGame(VideoGame videoGame) {
        Optional<VideoGame> videoGameOptional = videoGameRepository.findVideoGameByName(videoGame.getName());
        if (videoGameOptional.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("A Video Game With This Name Already Exist");

        boolean illegalVersionDate = videoGame.getVersions()
                .stream()
                .anyMatch(version -> version.getReleaseDate().isBefore(videoGame.getReleaseDate()));
        if (illegalVersionDate)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Versions' Release Date Can Not Be Prior To Video Game's Release Date");

        Set<String> duplicateVersions = new TreeSet<String>();
        videoGame.getVersions().forEach(version -> duplicateVersions.add(version.getVersion()));
        if (videoGame.getVersions().size() > duplicateVersions.size())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Duplicate Versions Are Not Allowed");

        videoGameRepository.save(videoGame);
        return ResponseEntity.ok("Video Game Added");
    }

    @Transactional
    ResponseEntity<?> getVideoGames() {
        List<VideoGame> videoGames = videoGameRepository.findAll();
        if (videoGames.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Games Were Found In Database");

        return ResponseEntity.ok(videoGames);
    }

    @Transactional
    ResponseEntity<?> getVideoGame(String name) {
        Optional<VideoGame> videoGameOptional = videoGameRepository.findVideoGameByName(name);
        if (videoGameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Game With This Name Was Found");

        return ResponseEntity.ok(videoGameOptional.get());
    }

    @Transactional
    ResponseEntity<?> getVideoGamesByGenre(Set<VideoGame.Genre> genres) {
        if (genres.size() == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("At Least One Genre Must Be Sent");

        List<VideoGame> videoGames = videoGameRepository.findAll();
        videoGames = videoGames.stream()
                .filter(videoGame -> videoGame.getGenres().containsAll(genres))
                .toList();

        if (videoGames.size() == 0 && genres.size() == 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Games With This Genre Were Found");
        else if (videoGames.size() == 0 && genres.size() > 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Games With These Genres Were Found");

        return ResponseEntity.ok(videoGames);
    }

    @Transactional
    ResponseEntity<?> getVideoGamesByPlatform(Set<VideoGame.Platform> platforms) {
        if (platforms.size() == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("At Least One Platform Must Be Sent");

        List<VideoGame> videoGames = videoGameRepository.findAll();
        videoGames = videoGames.stream()
                .filter(videoGame -> videoGame.getPlatforms().containsAll(platforms))
                .toList();

        if (videoGames.size() == 0 && platforms.size() == 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Games With This Platform Were Found");
        else if (videoGames.size() == 0 && platforms.size() > 1)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Games With These Platforms Were Found");

        return ResponseEntity.ok(videoGames);
    }

    @Transactional
    ResponseEntity<String> patchVideoGame(String name, VideoGame videoGame) {
        Optional<VideoGame> videoGameOptional = videoGameRepository.findVideoGameByName(name);
        if (videoGameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Game With This Name Was Found");

        if (videoGame.getPrice() != null) videoGameOptional.get().setPrice(videoGame.getPrice());

        if (videoGame.getGenres() != null)
            if (videoGame.getGenres().size() > 0)
                videoGameOptional.get().setGenres(videoGame.getGenres());
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You Can Not Send An Empty Array Of Genres");

        if (videoGame.getPlatforms() != null)
            if (videoGame.getPlatforms().size() > 0)
                videoGameOptional.get().setPlatforms(videoGame.getPlatforms());
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You Can Not Send An Empty Array Of Platforms");

        if (videoGame.getVersions() != null) {
            if (videoGame.getVersions().size() > 0) {
                boolean illegalVersionDate = videoGame.getVersions()
                        .stream()
                        .anyMatch(version -> version.getReleaseDate().isBefore(videoGameOptional.get().getReleaseDate()));
                if (illegalVersionDate) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Versions' Release Date Can Not Be Prior To Video Game's Release Date");
                }

                Set<String> duplicateVersions = new TreeSet<String>();
                videoGame.getVersions().forEach(version -> duplicateVersions.add(version.getVersion()));
                if (videoGame.getVersions().size() > duplicateVersions.size())
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Duplicate Versions Are Not Allowed");

                videoGameOptional.get().setVersions(videoGame.getVersions());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You Can Not Send An Empty Array Of Versions");
            }
        }

        return ResponseEntity.ok("Video Game Updated");
    }

    @Transactional
    ResponseEntity<String> deleteVideoGame(String name) {
        Optional<VideoGame> videoGameOptional = videoGameRepository.findVideoGameByName(name);
        if (videoGameOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No Video Game With This Name Was Found");

        videoGameRepository.deleteById(name);
        return ResponseEntity.ok("Video Game Deleted");
    }

    // TODO refactor code and remove duplicate code
}
