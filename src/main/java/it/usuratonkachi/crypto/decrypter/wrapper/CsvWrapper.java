package it.usuratonkachi.crypto.decrypter.wrapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvWrapper {

    private String pathToFile;
    private String encryptedPassword;
    private String publicKey;

}
