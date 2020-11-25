package it.usuratonkachi.crypto.decrypter.runner;

import it.usuratonkachi.crypto.decrypter.crypt.Cryptography;
import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.service.CsvReader;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

import static it.usuratonkachi.crypto.decrypter.crypt.Cryptography.encrypt;

@Slf4j
@Service
public class CryptRunner extends CommonRunner {

    @Getter
    private final String extension = "encrypted";
    @Getter
    private final ModeEnum modeEnum = ModeEnum.CRYPT;
    @Getter(AccessLevel.PROTECTED)
    private final String helpMessage = "Command is java -jar decrypt.jar 0 <path_to_csv>\nCsv must have pathToFile relative to execution path.";

    public CryptRunner(CsvReader csvReader) {
        super(csvReader);
    }

    @Override
    public CsvWrapper run(CsvWrapper csvWrapper) {
        try {
            Cryptography crypt = new Cryptography();
            File toEncrypt = new File(csvWrapper.getPathToFile());
            String destinationName = extendFilename(csvWrapper);
            File toDecrypt = new File(destinationName);
            return encrypt(toEncrypt, toDecrypt, crypt);
        } catch (Exception ex) {
            log.error("Errore durante elaborazione " + csvWrapper.toString(), ex);
            return null;
        }
    }

}
