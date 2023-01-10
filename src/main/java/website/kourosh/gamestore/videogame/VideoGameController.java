package website.kourosh.gamestore.videogame;

import org.springframework.beans.factory.annotation.Autowired;
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


}
