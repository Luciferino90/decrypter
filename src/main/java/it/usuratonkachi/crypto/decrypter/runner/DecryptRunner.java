package it.usuratonkachi.crypto.decrypter.runner;

import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.service.CsvReader;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import static it.usuratonkachi.crypto.decrypter.crypt.Cryptography.decrypt;

@Service
public class DecryptRunner extends CommonRunner {

    @Getter
    private final ModeEnum modeEnum = ModeEnum.DECRYPT;
    @Getter(AccessLevel.PROTECTED)
    private final String helpMessage = "Command is java -jar decrypt.jar 1 <path_to_csv>\nCsv must have pathToFile relative to execution path, encryptedPassword and b64PublicKey. Headers are optional";
    @Getter
    private final String extension = "decrypt";

    public DecryptRunner(CsvReader csvReader) {
        super(csvReader);
    }

    @Override
    public CsvWrapper run(CsvWrapper csvWrapper) {
        String destinationName = extendFilename(csvWrapper);
        return decrypt(csvWrapper.getEncryptedPassword(), csvWrapper.getPathToFile(), csvWrapper.getPublicKey(), destinationName);
    }

}
