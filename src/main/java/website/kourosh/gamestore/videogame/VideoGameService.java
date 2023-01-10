package website.kourosh.gamestore.videogame;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class VideoGameService {

    private final VideoGameRepository videoGameRepository;

    @Autowired
    VideoGameService(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }

    @Transactional
    ResponseEntity<String> addNewVideoGame(VideoGame videoGame) {
        Optional<VideoGame> videoGameOptional = videoGameRepository.findVideoGamesByName(videoGame.getName());
        if (videoGameOptional.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("A Video Game With This Name Already Exist");

        videoGameRepository.save(videoGame);
        return ResponseEntity.ok("Video Game Added");
    }
}
