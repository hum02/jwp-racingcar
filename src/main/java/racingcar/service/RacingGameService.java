package racingcar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import racingcar.dao.RacingGameDao;
import racingcar.domain.Car;
import racingcar.domain.Name;
import racingcar.domain.RacingGame;
import racingcar.domain.TryCount;
import racingcar.dto.CarDto;
import racingcar.dto.GameResultDto;
import racingcar.dto.response.GameResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RacingGameService {
    private static final String DELIMITER = ",";

    private final RacingGameDao racingGameDao;

    @Autowired
    public RacingGameService(RacingGameDao racingGameDao) {
        this.racingGameDao = racingGameDao;
    }

    public GameResponseDto play(String names, int tryCount) {
        RacingGame racingGame = new RacingGame(convertToNames(names));

        racingGame.moveCars(new TryCount(tryCount));

        String winners = decideWinners(racingGame);
        List<CarDto> resultCars = getResultCars(racingGame);

        racingGameDao.saveResult(new GameResultDto(tryCount, winners, resultCars));

        return new GameResponseDto(winners, resultCars);
    }

    private List<Name> convertToNames(String inputNames) {
        return Arrays.stream(inputNames.split(DELIMITER))
                .map(Name::new)
                .collect(Collectors.toList());
    }

    private String decideWinners(RacingGame racingGame) {
        return racingGame.decideWinners().getCars().stream()
                .map(Car::getName)
                .collect(Collectors.joining(DELIMITER));
    }

    private List<CarDto> getResultCars(RacingGame racingGame) {
        List<CarDto> carDtoList = new ArrayList<>();

        for (Car car : racingGame.getCars()) {
            carDtoList.add(new CarDto(car.getName(), car.getPosition()));
        }

        return carDtoList;
    }
}
