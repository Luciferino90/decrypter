package it.usuratonkachi.crypto.decrypter.enumeration;

import it.usuratonkachi.crypto.decrypter.config.Configuration;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Function;

public enum HeadersEnum {

    PATH_TO_FILE(Configuration::getPathToFileHeader),
    ENCRYPTED_PASSWORD(Configuration::getEncryptedPasswordHeader),
    PUBLIC_KEY(Configuration::getPublicKeyHeader);

    @Getter(AccessLevel.PUBLIC)
    private Function<Configuration, String> csvView;

    HeadersEnum(Function<Configuration, String> csvView) {
        this.csvView = csvView;
    }

    public String getCsvView(Configuration configuration) {
        return csvView.apply(configuration);
    }
    public static HeadersEnum getFromValue(Configuration configuration, String csvView){
        return Arrays.stream(HeadersEnum.values())
                .filter(v -> v.getCsvView().apply(configuration).equalsIgnoreCase(csvView))
                .findFirst()
                .orElse(null);
    }

}
