package racingcar.dao;

import racingcar.dto.CarDto;

import java.util.List;

public interface ResultCarDao {
    void save(int gameId, List<CarDto> carDtoList);
}