package com.feng.lin.restful.fast.freemaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class CodeMaker implements FileCreator {
	private Logger logger = LoggerFactory.getLogger(CodeMaker.class);
	private Configuration configuration = new Configuration();
	private static final String ENCODE = "utf-8";

	@Override
	public void createFile(String templateName, Map map, String fileName, String path, String extensions)
			throws Exception {
		logger.info(path + fileName + "." + extensions);

		Template template = configuration.getTemplate(templateName, ENCODE);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Writer writer = new OutputStreamWriter(new FileOutputStream(path + fileName + "." + extensions));
		template.process(map, writer);
		writer.close();
	}

	@Override
	public void ready(String ftlDir) throws IOException {
		configuration.setDirectoryForTemplateLoading(new File(ftlDir));

	}

}
