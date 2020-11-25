package it.usuratonkachi.crypto.decrypter.runner;

import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.service.CsvReader;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class FullDemoRunner extends CommonRunner {

    @Getter
    private final ModeEnum modeEnum = ModeEnum.FULLDEMO;

    @Getter(AccessLevel.PROTECTED)
    private final String helpMessage = "Command is java -jar decrypt.jar 2 <path_to_csv>\nCsv must have pathToFile relative to execution path.";

    private final CryptRunner cryptRunner;
    private final DecryptRunner decryptRunner;

    public FullDemoRunner(CsvReader csvReader, CryptRunner cryptRunner, DecryptRunner decryptRunner) {
        super(csvReader);
        this.cryptRunner = cryptRunner;
        this.decryptRunner = decryptRunner;
    }

    @Override
    public CsvWrapper run(CsvWrapper csvWrapper) {
        csvWrapper = cryptRunner.run(csvWrapper);
        return decryptRunner.run(csvWrapper);
    }

    @Override
    public String getExtension() {
        throw new RuntimeException("GetExtension not implemented");
    }
}
