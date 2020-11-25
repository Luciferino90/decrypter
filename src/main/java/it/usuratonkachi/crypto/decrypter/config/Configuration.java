package it.usuratonkachi.crypto.decrypter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "configuration")
public class Configuration {

    private String separator;
    private String pathToFileHeader;
    private String encryptedPasswordHeader;
    private String publicKeyHeader;

}
