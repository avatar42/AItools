/**
 * 
 */
package com.dea42.aitools.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dea42.aitools.UnitBase;

/**
 * @author deabigt
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileIOTest extends UnitBase {

	
	@Autowired
	protected FileIO fileIO;

	/**
	 * Test method for
	 * {@link com.dea42.aitools.utils.FileIO#genClassTree(java.lang.String)}.
	 */
	@Test
	public final void testGenClassTree() {
		assertTrue(fileIO.genClassTree("target/new"));
		Path dirPath = Paths.get("target/new", "vehicle", "truck", "truck_tanker");
		File dir = dirPath.toFile();
		assertTrue(dir.exists());
	}

}
