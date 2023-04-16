package racingcar.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import racingcar.dto.GameResultDto;
import racingcar.dto.GameWinnerDto;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

@Component
public class GameResultJdbcDAO implements GameResultDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameResultJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(GameResultDto resultDto) {
        String sql = "insert into GAME_RESULT (winners, trial_count) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"game_id"});
            preparedStatement.setString(1, resultDto.getWinners());
            preparedStatement.setInt(2, resultDto.getTrialCount());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<GameWinnerDto> selectAllGame() {
        String sql = "select game_id,winners from GAME_RESULT";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new GameWinnerDto(rs.getInt("game_id"), rs.getString("winners")));
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from GAME_RESULT";
        jdbcTemplate.update(sql);
    }
}