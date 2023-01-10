package website.kourosh.gamestore.videogame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class VideoGameService {

    private final VideoGameRepository videoGameRepository;

    @Autowired
    VideoGameService(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }
}
