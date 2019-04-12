package com.feng.lin.restful.fast.freemaker;

import java.io.IOException;
import java.util.Map;

public interface FileCreator {
	void createFile(String templateName, Map map, String fileName, String path, String extensions) throws Exception;

	void ready(String ftlDir) throws IOException;
}
