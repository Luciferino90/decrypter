package it.usuratonkachi.crypto.decrypter.runner;

import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.service.CsvReader;
import it.usuratonkachi.crypto.decrypter.utils.FilesystemUtils;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class CommonRunner implements Runner {

    private final CsvReader csvReader;

    protected abstract String getHelpMessage();
    public abstract ModeEnum getModeEnum();
    public abstract String getExtension();

    public void run(String pathToCsv) {
        run(readCsv(pathToCsv));
    }

    public void run(Stream<CsvWrapper> csvWrappers) {
        csvWrappers.map(this::run).forEach(csvWrapper -> System.out.println(csvWrapper.toString()));
    };

    public void help() {
        System.out.println(getHelpMessage());
    };

    protected Stream<CsvWrapper> readCsv(String csvPathString) {
        Path csvPath = Paths.get(csvPathString);
        return csvReader.readCsv(csvPath);
    }

    public String extendFilename(CsvWrapper csvWrapper) {
        return FilesystemUtils.extendFilename(csvWrapper.getPathToFile(), getExtension());
    }

}
