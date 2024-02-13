/**
 * 
 */
package com.dea42.aitools.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.service.ClassesServices;

import lombok.extern.slf4j.Slf4j;

/**
 * @author deabigt
 *
 */
@Slf4j
@Service
public class FileIO {

	@Autowired
	private ClassesServices classesService;

	public boolean genClassTree(String baseFolder) {
		log.warn("classesService:" + classesService);
		Page<Classes> classes = classesService.listAll(null);
		boolean rtn = true;
		int cnt = 0;
		for (Classes c : classes) {
			if (c.getActive() == 1) {
				Path dirPath = Paths.get(baseFolder, c.getCatagory(), c.getGrp(), c.getClassname());
				File dir = dirPath.toFile();
				if (!dir.exists()) {
					rtn = rtn & dir.mkdirs();
					if (dir.exists()) {
						cnt++;
					} else {
						log.error("Failed to create:" + dir.getAbsolutePath());
					}
				} else {
					log.debug("Already existed:" + dir.getAbsolutePath());
				}
			}
		}
		log.info("Created " + cnt + " folders");
		return rtn;
	}

}
