package it.usuratonkachi.crypto.decrypter.crypt;

import it.usuratonkachi.crypto.decrypter.wrapper.CsvWrapper;
import lombok.extern.slf4j.Slf4j;
import org.encryptor4j.util.FileEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public class Cryptography
{
    private Cipher cipher;
    private String safeWord;
    private String encryptedSafeWord;
    private PublicKey publicKey;

    private static final String DECRYPT_ERR_MSG_TEMPLATE = "Error during decrypting for file %s: %s";

    public Cryptography()
            throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, IllegalBlockSizeException,
            UnsupportedEncodingException, BadPaddingException, InvalidKeyException
    {
        this.cipher = Cipher.getInstance("RSA");
        safeWord = safeWordGenerator();
        encryptSafeWord(safeWord);
    }

    public String encryptText(String msg, PrivateKey key) throws UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException
    {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
    }

    public String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException
    {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(msg)), "UTF-8");
    }

    private String safeWordGenerator()
    {
        PasswordGenerator passwordGenerator = new PasswordGenerator(52);
        return passwordGenerator.nextString();
    }

    private void encryptSafeWord(String safeWorld) throws NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException,
            UnsupportedEncodingException, BadPaddingException, InvalidKeyException
    {
        GenerateKeys gk = new GenerateKeys(512);
        encryptedSafeWord = encryptText(safeWorld, gk.getPrivateKey());
        publicKey = gk.getPublicKey();
    }

    public static CsvWrapper encrypt(File srcFile, File destFile, Cryptography crypt)
            throws GeneralSecurityException, IOException
    {
        PasswordGenerator passwordGenerator = new PasswordGenerator(52);
        String password = passwordGenerator.nextString();
        GenerateKeys gk = new GenerateKeys(512);
        String encryptedPassword = crypt.encryptText(password, gk.getPrivateKey());

        // Stampo chiavi
        byte[] encodedPublicKey = gk.getPublicKey().getEncoded();
        String b64PublicKey = java.util.Base64.getEncoder().encodeToString(encodedPublicKey);

        // Cripto il file zip
        FileEncryptor fe = new FileEncryptor(password);
        fe.encrypt(srcFile, destFile);
        return CsvWrapper.builder().pathToFile(destFile.getPath()).encryptedPassword(encryptedPassword).publicKey(b64PublicKey).build();
    }

    public static CsvWrapper decrypt(String safeWordEncrypted, String inputFile, String publicKey, String outputFile){
        Cryptography crypt;
        try {
            crypt = new Cryptography();
            byte[] publicBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            String decryptedPassword = crypt.decryptText(safeWordEncrypted, pubKey);
            FileEncryptor fe = new FileEncryptor(decryptedPassword);
            fe.decrypt(new File(inputFile), new File(outputFile));
            return CsvWrapper.builder()
                    .pathToFile(outputFile)
                    .encryptedPassword(safeWordEncrypted)
                    .publicKey(publicKey)
                    .build();
        } catch(Exception ex) {
            log.error(String.format(DECRYPT_ERR_MSG_TEMPLATE, inputFile, ex.getMessage()), ex);
            return null;
        }
    }

}
