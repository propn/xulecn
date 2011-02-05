package org.json.test.compliance;

import junit.framework.TestCase;

import org.json.test.compliance.std.JSONDecoder;

public class DecoderTestExample extends TestCase {
	private DecoderValidator validator;
	private JSONDecoder testee;
	private DataGenerator dataGen;

	public void setUp() {
		validator = new DecoderValidator();
		testee = new org.json.simple.compliance.DecoderImpl();

		dataGen = new DataGenerator();
		dataGen.maxNodeCount = 200;
		dataGen.maxLevel = 40;
		dataGen.maxSizePerLevel = 40;
	}

	public void testRandomJSONText() throws Exception {
		String sample = dataGen.generateJSONString();
		System.out.println("==sample==");
		System.out.println(sample);

		boolean result = validator.isValidated(sample, testee);
		System.out.println("==result: " + (result ? "passed" : "failed")
				+ " ==");
		assertTrue(result);
	}
}
