/**
 * 
 */
package com.dea42.aitools.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author deabigt
 *
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileIOTest {

	@Autowired
	protected FileIO fileIO;

	/**
	 * Test method for
	 * {@link com.dea42.aitools.utils.FileIO#genClassTree(java.lang.String)}.
	 */
	@Test
	final void testGenClassTree() {
		assumeTrue(true);
		assertTrue(fileIO.genClassTree("target/new"));
		Path dirPath = Paths.get("target/new", "vehicle", "truck", "truck_tanker");
		File dir = dirPath.toFile();
		assertTrue(dir.exists());
	}

}
