package com.skiff.test;

import com.skiff.common.core.util.AESUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class AESTest {

    @Test
    @SneakyThrows
    public void t1() {

        String appSecret = "zrZJA8OF636DIONtmBhqmxtV90fQmYeUSc1frOMFcSg=";

        String plainText = """
                {
                    "name": "5555是",
                    "age": 12
                }""";
        String decrypt = AESUtil.encrypt(plainText, AESUtil.decodeKeyFromBase64(appSecret));

        System.out.println("encrypt: " + decrypt);

    }
}
