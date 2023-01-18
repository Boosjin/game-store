package website.kourosh.gamestore.videogame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface VideoGameRepository extends JpaRepository<VideoGame, String> {

    Optional<VideoGame> findVideoGameByName(String name);

}
