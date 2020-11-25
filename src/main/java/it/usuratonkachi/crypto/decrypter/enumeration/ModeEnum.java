package it.usuratonkachi.crypto.decrypter.enumeration;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;

public enum ModeEnum {

    CRYPT(0),
    DECRYPT(1),
    FULLDEMO(2);

    @Getter(AccessLevel.PUBLIC)
    private int value;

    private static final String ERROR_MSG_TEMPLATE = "No mode found for enum %s, try with `0` for CRYPT `1` for DECRYPT and `2` for FULLDEMO";

    ModeEnum(int value) {
        this.value = value;
    }

    public static ModeEnum getFromValue(int value){
        return Arrays.stream(ModeEnum.values())
                .filter(v -> v.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format(ERROR_MSG_TEMPLATE, value)));
    }

}
