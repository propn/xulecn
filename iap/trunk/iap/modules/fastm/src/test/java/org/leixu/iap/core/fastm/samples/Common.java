package org.leixu.iap.core.fastm.samples;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.Parser;

public class Common {
	public static void write(ITemplate template, Object model, String file)
		throws Exception{

		FileWriter fw = new FileWriter(file);
		PrintWriter printer = new PrintWriter(fw);
		template.write(model, printer);
		fw.close();
	}

	public static void write(String srcFile, Object model, String file)
		throws Exception{
		
		ITemplate template = Parser.parse(srcFile);
		write(template, model, file);
	}
}
