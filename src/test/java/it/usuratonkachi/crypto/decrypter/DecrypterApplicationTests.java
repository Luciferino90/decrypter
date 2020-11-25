package it.usuratonkachi.crypto.decrypter;

import it.usuratonkachi.crypto.decrypter.service.CryptoService;
import it.usuratonkachi.crypto.decrypter.utils.FilesystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
@Slf4j
class DecrypterApplicationTests {

	@TempDir
	public Path tmpDir;
	@Autowired
	private CryptoService cryptoService;

	@Test
	void contextLoads() {
	}

	private MockedStatic<FilesystemUtils> mockedStatic;

	@BeforeEach
	private void registerFilesystemMock(){
		mockedStatic = mockStatic(FilesystemUtils.class);
		mockedStatic.when(() -> FilesystemUtils.extendFilename(any(), any())).thenReturn(tmpDir.resolve(UUID.randomUUID().toString()).toString());
	}

	@AfterEach
	private void unregisterFilesystemMock() {
		mockedStatic.close();
	}

	@Test
	public void crypt() {
		try {
			cryptoService.runner()
					.run("0", "src/test/resources/csv/crypt.csv");
		} catch (Exception ex) {
			log.error("Errore imprevisto " + ex.getMessage(), ex);
			Assertions.fail(ex);
		}
	}

	@Test
	public void decrypt() {
		try {
			cryptoService.runner()
					.run("1", "src/test/resources/csv/decrypt.csv");
		} catch (Exception ex) {
			log.error("Errore imprevisto " + ex.getMessage(), ex);
			Assertions.fail(ex);
		}
	}

	@Test
	public void fullDemo() {
		try {
			cryptoService.runner()
					.run("2", "src/test/resources/csv/fulldemo.csv");
		} catch (Exception ex) {
			log.error("Errore imprevisto " + ex.getMessage(), ex);
			Assertions.fail(ex);
		}
	}

}
