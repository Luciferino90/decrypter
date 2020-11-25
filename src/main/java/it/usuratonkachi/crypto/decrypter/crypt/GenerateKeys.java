package it.usuratonkachi.crypto.decrypter.crypt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class GenerateKeys {

    private KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Costructor which generate the two keys for the safe word
     * @param keylength
     *                      Length of the keys
     * @throws NoSuchAlgorithmException
     *                      Low level exception
     * @throws NoSuchProviderException
     *                      Low level exception
     */
    GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
        this.createKeys();
    }

    /**
     * Method which will create keys for the safe word
     */
    private void createKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

}
