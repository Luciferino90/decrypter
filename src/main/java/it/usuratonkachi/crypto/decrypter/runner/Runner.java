package it.usuratonkachi.crypto.decrypter.runner;

import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;

import java.util.stream.Stream;

public interface Runner {

    public void run(String pathToCsv);
    public void run(Stream<CsvWrapper> csvWrapper);
    public CsvWrapper run(CsvWrapper csvWrapper);
    public void help();
    public ModeEnum getModeEnum();

}
