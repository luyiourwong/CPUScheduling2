/**
 * 
 */
package com.luyiourwong.se.hw2test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.luyiourwong.se.hw2.MainCPUScheduling;

/**
 *
 */
@SuppressWarnings("unused")
class MainCPUSchedulingTest {
	
	static MainCPUScheduling instance;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("static-access")
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		instance = new MainCPUScheduling();
		instance.setInstance(instance);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	/*
	 * test
	 */

	@Test
	void scheduling() {
		/*
		 * values
		 */
		//File file;
		//instance.scheduling(file);
		
		/*
		 * actual
		 */
		
		/*
    	 * expected
    	 */
		
		/*
		 * test
		 */
		//fail("Not yet implemented");
		//assertEquals(expected, actual);
	}

}
