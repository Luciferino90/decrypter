package it.usuratonkachi.crypto.decrypter.service;

import it.usuratonkachi.crypto.decrypter.enumeration.ModeEnum;
import it.usuratonkachi.crypto.decrypter.runner.Runner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final List<Runner> runners;

    private static final String ERROR_MSG_TEMPLATE = "" +
            "You can launch this script by\n " +
            "\tjava -jar decrypt.jar <mode> <path_to_csv>\n" +
            "where Mode can be:\n" +
            "\t\t- 0: Crypt Mode\n" +
            "\t\t- 1: Decrypt Mode\n" +
            "\t\t- 2: Fulldemo Mode\n" +
            "and path_to_csv must be relative/absolute path to a csv of the following format:\n" +
            "\t\tpathToFile: relative/absolute path to file to elaborate\n" +
            "\t\tencryptedPassword: Mode 1 only, received encryptedPassword\n" +
            "\t\tb64PublicKey: Mode 1 only, received public key in base 64\n";

    @Bean
    @Profile("!test")
    public CommandLineRunner runner() {
        return args -> {
            try {
                ModeEnum modeEnum = ModeEnum.getFromValue(Integer.parseInt(args[0]));
                runners.stream()
                        .filter(run -> run.getModeEnum().equals(modeEnum))
                        .findFirst()
                        .ifPresent(runner -> runner.run(args[1]));
            } catch (Exception ex){
                System.out.println(ERROR_MSG_TEMPLATE);
            }
        };
    }


}
