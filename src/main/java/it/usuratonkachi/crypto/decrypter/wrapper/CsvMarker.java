package it.usuratonkachi.crypto.decrypter.wrapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CsvMarker {

    @Getter @Setter
    private boolean headerFound = false;
    @Getter
    private int pathToFilePosition = -1;
    @Getter
    private int encryptedPasswordPosition = -1;
    @Getter
    private int publicKeyPosition = -1;

    public static CsvMarker defaultMarker() {
        return CsvMarker.builder()
                .pathToFilePosition(0)
                .encryptedPasswordPosition(1)
                .publicKeyPosition(2)
                .headerFound(false)
                .build();
    }

}
