package com.paritask.api.assertions;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {

    public static void checkStatusCode(int actualCode, int expectedCode) {
        assertThat(actualCode)
                .withFailMessage(String.format("Actual status code is: %s, but expected is: %s", actualCode, expectedCode))
                .isEqualTo(expectedCode);
    }

}
