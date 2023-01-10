package website.kourosh.gamestore.videogame;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("VideoGame")
final class VideoGameController {

    private final VideoGameService videoGameService;

    @Autowired
    private VideoGameController(VideoGameService videoGameService) {
        this.videoGameService = videoGameService;
    }

    @PostMapping
    private ResponseEntity<String> addNewVideoGame(@Valid @RequestBody VideoGame videoGame){
        return videoGameService.addNewVideoGame(videoGame);
    }
}
