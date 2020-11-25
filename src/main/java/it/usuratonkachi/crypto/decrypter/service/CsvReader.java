package it.usuratonkachi.crypto.decrypter.service;

import it.usuratonkachi.crypto.decrypter.config.Configuration;
import it.usuratonkachi.crypto.decrypter.enumeration.HeadersEnum;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvMarker;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CsvReader {

    private final String ERR_MGS_TEMPLATE = "Errore durante lettura CSV %s: %s";

    private final Configuration configuration;

    public Stream<CsvWrapper> readCsv(Path csvPath){
        try {
            CsvMarker csvMarker = parseHeader(csvPath);
            return Files.lines(csvPath)
                    .skip(csvMarker.isHeaderFound() ? 1 : 0)
                    .map(line -> line.split(configuration.getSeparator()))
                    .map(parts -> CsvWrapper.builder()
                            .pathToFile(parts[csvMarker.getPathToFilePosition()].trim())
                            .encryptedPassword(parts.length > csvMarker.getEncryptedPasswordPosition() ? parts[csvMarker.getEncryptedPasswordPosition()].trim(): "")
                            .publicKey(parts.length > csvMarker.getPublicKeyPosition() ? parts[csvMarker.getPublicKeyPosition()].trim() : "")
                            .build()
                    );
        } catch (IOException e) {
            throw new RuntimeException(String.format(ERR_MGS_TEMPLATE, csvPath, e.getMessage()), e);
        }
    }

    private CsvMarker parseHeader(Path csvPath) {
        try {
            return Files.lines(csvPath)
                    .limit(1)
                    .filter(
                            line -> line.contains(HeadersEnum.PATH_TO_FILE.getCsvView(configuration))
                    )
                    .map(line -> line.split(configuration.getSeparator()))
                    .filter(parts -> parts.length > 0)
                    .map(parts ->
                            CsvMarker.builder()
                                    .pathToFilePosition(getHeaderPosition(HeadersEnum.PATH_TO_FILE, parts, 0))
                                    .encryptedPasswordPosition(getHeaderPosition(HeadersEnum.ENCRYPTED_PASSWORD, parts, 1))
                                    .publicKeyPosition(getHeaderPosition(HeadersEnum.PUBLIC_KEY, parts, 2))
                                    .build()
                    )
                    .map(csvMarker -> {
                        if (csvMarker.getPathToFilePosition() != -1) {
                            csvMarker.setHeaderFound(true);
                            return csvMarker;
                        } else {
                            return CsvMarker.defaultMarker();
                        }
                    })
                    .findFirst()
                    .orElseGet(CsvMarker::defaultMarker)
                    ;
        } catch (IOException e) {
            throw new RuntimeException(String.format(ERR_MGS_TEMPLATE, csvPath, e.getMessage()), e);
        }
    }

    private int getHeaderPosition(HeadersEnum headerEnum, String[] headers, int defaultValue) {
        for (int counter = 0; counter < headers.length; counter++) {
            if (headerEnum.getCsvView(configuration).equalsIgnoreCase(headers[counter].trim()))
                return counter;
        }
        return defaultValue;
    }

}
