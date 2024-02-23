package com.example.hellojdbc.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
public class UncheckedTest {

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }
    @Test
    void unchecked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }

    public static class Service {
        Repository repository = new Repository();

        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("언체크 예외, message={}", e.getMessage(), e);
            }
        }

        public void callThrow() {
            repository.call();
        }
    }

    public static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }
}
