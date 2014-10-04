package com.sunriseprojects.memorizer.core.console;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.CharEncoding;
import org.apache.poi.util.IOUtils;

import com.sunriseprojects.memorizer.core.actions.StartQAPositiveAction;
import com.sunriseprojects.memorizer.core.dao.ContextDAO;
import com.sunriseprojects.memorizer.core.dao.QuestionDAO;
import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;
import com.sunriseprojects.memorizer.core.utils.QAUtils;

import junit.framework.TestCase;


public class TestFilesList extends TestCase {

	public void testA() throws Exception {


		File folder = new File("C:\\excel");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
		    
	}
	
	
	
}
