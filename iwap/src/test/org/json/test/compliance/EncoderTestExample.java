package org.json.test.compliance;

import junit.framework.TestCase;

import org.json.test.compliance.std.JSONEncoder;

public class EncoderTestExample extends TestCase {
	private EncoderValidator validator;
	private JSONEncoder testee;
	private DataGenerator dataGen;

	public void setUp() {
		validator = new EncoderValidator();

		dataGen = new DataGenerator();
		dataGen.maxNodeCount = 200;
		dataGen.maxLevel = 40;
		dataGen.maxSizePerLevel = 40;
	}

	public void testRandomJSONText() throws Exception {
		testee = new org.json.simple.compliance.EncoderImpl();
		String sample = dataGen.generateJSONString();
		System.out.println("==sample==");
		System.out.println(sample);

		boolean result = validator.isValidated(sample, testee);
		System.out.println("==result: " + (result ? "passed" : "failed")
				+ " ==");
		assertTrue(result);
	}

	public void testRandomJSONTextStream() throws Exception {
		testee = new org.json.simple.compliance.StreamEncoderImpl();
		String sample = dataGen.generateJSONString();
		System.out.println("==stream sample==");
		System.out.println(sample);

		boolean result = validator.isValidated(sample, testee);
		System.out.println("==stream result: " + (result ? "passed" : "failed")
				+ " ==");
		assertTrue(result);
	}
}
