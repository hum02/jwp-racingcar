package racingcar.db;

import org.springframework.stereotype.Repository;
import racingcar.dto.CarDto;
import racingcar.dto.GameResultDto;
import racingcar.dto.GameWinnerDto;
import racingcar.dto.response.GameResponse;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RacingGameJdbcRepository implements RacingGameRepository {
    private final GameResultDao gameResultDao;
    private final ResultCarDao resultCarDao;

    public RacingGameJdbcRepository(GameResultDao gameResultDao, ResultCarDao resultCarDao) {
        this.gameResultDao = gameResultDao;
        this.resultCarDao = resultCarDao;
    }

    @Override
    public void saveGame(GameResultDto resultDto) {
        int id = gameResultDao.save(resultDto);
        resultCarDao.save(id, resultDto.getRacingCars());
    }

    @Override
    public List<GameResponse> findAllGame() {
        List<GameResponse> gameResponses = new ArrayList<>();

        List<GameWinnerDto> gameWinners = gameResultDao.selectAllGame();
        for (GameWinnerDto gameWinner : gameWinners) {
            List<CarDto> cars = resultCarDao.findByGameId(gameWinner.getGameId());
            gameResponses.add(new GameResponse(gameWinner.getWinners(), cars));
        }

        return gameResponses;
    }

    @Override
    public void deleteAll() {
        gameResultDao.deleteAll();
    }
}