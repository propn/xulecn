package org.json.simple.compliance;

import junit.framework.TestCase;

import org.json.compliance.EncoderImpl;
import org.json.test.compliance.DataGenerator;
import org.json.test.compliance.DecoderValidator;
import org.json.test.compliance.std.JSONDecoder;
import org.json.test.compliance.std.JSONEncoder;

public class DecoderTest extends TestCase {
	private DecoderValidator validator;
	private JSONDecoder testee;
	private DataGenerator dataGen;
	private JSONEncoder riEncoder;

	public void setUp() {
		validator = new DecoderValidator();
		riEncoder = new EncoderImpl();

		dataGen = new DataGenerator();
		dataGen.maxNodeCount = 200;
		dataGen.maxLevel = 40;
		dataGen.maxSizePerLevel = 40;
	}

	public void testRandomJSONText() throws Exception {
		int batchNum = 100;
		int maxBatch = 10;

		testee = new org.json.simple.compliance.DecoderImpl();
		System.out.println(testee.getClass().getName() + ":");

		for (int i = 0; i < maxBatch * batchNum; i++) {
			Object graph = dataGen.generateGraph();
			String sample = riEncoder.toJSONString(graph);

			try {
				boolean result = validator.isValidated(sample, testee);
				if (!result) {
					System.out.println("==Failed. Sample: ==");
					System.out.println(sample);
				}
				assertTrue(result);
			} catch (Exception e) {
				System.out.println("==Failed. Sample: ==");
				System.out.println(sample);
			}

			if ((i + 1) % batchNum == 0) {
				System.out.println("Passed samples: " + (i + 1));
			}
		}
	}

	public void testRandomJSONTextStream() throws Exception {
		int batchNum = 100;
		int maxBatch = 10;

		testee = new org.json.simple.compliance.StreamDecoderImpl();
		System.out.println(testee.getClass().getName() + ":");

		for (int i = 0; i < maxBatch * batchNum; i++) {
			Object graph = dataGen.generateGraph();
			String sample = riEncoder.toJSONString(graph);

			try {
				boolean result = validator.isValidated(sample, testee);
				if (!result) {
					System.out.println("==Failed. Sample: ==");
					System.out.println(sample);
				}
				assertTrue(result);
			} catch (Exception e) {
				System.out.println("==Failed. Sample: ==");
				System.out.println(sample);
				throw e;
			}
			if ((i + 1) % batchNum == 0) {
				System.out.println("Passed samples: " + (i + 1));
			}
		}
	}
}
