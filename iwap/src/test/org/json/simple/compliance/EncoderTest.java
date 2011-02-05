package org.json.simple.compliance;

import org.json.compliance.EncoderImpl;
import org.json.test.compliance.DataGenerator;
import org.json.test.compliance.EncoderValidator;
import org.json.test.compliance.std.JSONEncoder;

import junit.framework.TestCase;

public class EncoderTest extends TestCase {
	private EncoderValidator validator;
	private JSONEncoder testee;
	private DataGenerator dataGen;
	private JSONEncoder riEncoder;

	public void setUp() {
		validator = new EncoderValidator();
		riEncoder = new EncoderImpl();

		dataGen = new DataGenerator();
		dataGen.maxNodeCount = 200;
		dataGen.maxLevel = 40;
		dataGen.maxSizePerLevel = 40;
	}

	public void testRandomJSONText() throws Exception {
		int batchNum = 100;
		int maxBatch = 10;

		testee = new org.json.simple.compliance.EncoderImpl();
		System.out.println(testee.getClass().getName() + ":");
		for (int i = 0; i < maxBatch * batchNum; i++) {
			Object graph = dataGen.generateGraph();
			String sample = riEncoder.toJSONString(graph);

			boolean result = validator.isValidated(sample, testee);
			if (!result) {
				System.out.println("==Failed. Sample: ==");
				System.out.println(sample);
			}
			assertTrue(result);

			if ((i + 1) % batchNum == 0) {
				System.out.println("Passed samples: " + (i + 1));
			}
		}
	}

	public void testRandomJSONTextStream() throws Exception {
		int batchNum = 100;
		int maxBatch = 10;

		testee = new org.json.simple.compliance.StreamEncoderImpl();
		System.out.println(testee.getClass().getName() + ":");
		for (int i = 0; i < maxBatch * batchNum; i++) {
			Object graph = dataGen.generateGraph();
			String sample = riEncoder.toJSONString(graph);

			boolean result = validator.isValidated(sample, testee);
			if (!result) {
				System.out.println("==Failed. Sample: ==");
				System.out.println(sample);
			}
			assertTrue(result);

			if ((i + 1) % batchNum == 0) {
				System.out.println("Passed samples: " + (i + 1));
			}
		}
	}
}
