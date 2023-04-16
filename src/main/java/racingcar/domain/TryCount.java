package racingcar.domain;

import racingcar.exception.BusinessArgumentException;
import racingcar.exception.ErrorCode;

public class TryCount {
    private static final int MIN_COUNT = 1;

    private final int count;

    public TryCount(int count) {
        validate(count);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    private void validate(int count) {
        if (count < MIN_COUNT) {
            throw new BusinessArgumentException(ErrorCode.INVALID_COUNT);
        }
    }
}
