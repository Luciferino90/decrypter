package it.usuratonkachi.crypto.decrypter.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.stream.Collectors;

@UtilityClass
public class FilesystemUtils {

    public String extendFilename(String filenpath, String extension) {
        String[] parts = filenpath.split("\\.");
        return Arrays.stream(parts).limit(parts.length - 1).collect(Collectors.joining(".")) + "." + extension + "." + parts[parts.length -1];
    }

}
