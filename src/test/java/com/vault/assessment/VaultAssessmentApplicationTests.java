package com.vault.assessment;

import com.vault.assessment.exception.VelocityException;
import com.vault.assessment.reader.VelocityDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static com.vault.assessment.util.Constants.*;

@SpringBootTest
class VaultAssessmentApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(VaultAssessmentApplicationTests.class);

	@Autowired
	VelocityDataLoader loader;

	@Test
	void contextLoads() {
		File f1 = new File(OUTPUT_FILE);// OUTFILE
		File f2 = new File(OUTPUT_FILE_EXPECTED);// OUTPUT_FILE_EXPECTED
		try(FileReader fR1 = new FileReader(f1);
			FileReader fR2 = new FileReader(f2);
			BufferedReader reader1 = new BufferedReader(fR1);
			BufferedReader reader2 = new BufferedReader(fR2)) {
			loader.readAndProcessVelocityData(INPUT_FILE);

			String line1 = reader1.readLine();
			String line2 = reader2.readLine();

			boolean areEqual = true;
			int lineNum = 1;

			while (line1 != null || line2 != null) {
				if (line1 == null || line2 == null) {
					areEqual = false;
					break;
				} else if (!line1.equalsIgnoreCase(line2)) {
					areEqual = false;
					break;
				}
				line1 = reader1.readLine();
				line2 = reader2.readLine();
				lineNum++;
			}
			if (areEqual) {
				LOGGER.info("Two files have same content.");
			} else {
				LOGGER.error("Two files have different content. They differ at line " + lineNum);
				LOGGER.error("File1 has " + line1 + " and File2 has " + line2 + " at line " + lineNum);
			}
		} catch (Exception e) {
			LOGGER.error("Error while processing the velocity data: "+e.getCause());
		}
	}

}
