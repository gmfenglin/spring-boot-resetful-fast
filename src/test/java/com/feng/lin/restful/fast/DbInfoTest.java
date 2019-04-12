package com.feng.lin.restful.fast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.feng.lin.restful.fast.config.ProjectProperty;
import com.feng.lin.restful.fast.freemaker.Const;
import com.feng.lin.restful.fast.freemaker.DBInfo;
import com.feng.lin.restful.fast.freemaker.FileCreator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbInfoTest {
	@Autowired
	private DBInfo dBInfo;
	@Autowired
	private ProjectProperty projectProperty;
	@Autowired
	private FileCreator fileCreator;
	@Value("${fast.dir}")
	private String fastDir;

	@Test
	public void getDataTest() throws Exception {
		Map goalMap=new HashMap();
		goalMap.put(Const.APP_PACKAGE_TAG, projectProperty.getGroupId() + "." + projectProperty.getArtifactId());
		char [] packageChars=(projectProperty.getGroupId() + "." + projectProperty.getArtifactId()).toCharArray();
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<packageChars.length;i++) {
			if(packageChars[i]=='.') {
				sb.append('\\');
			}else {
				sb.append(packageChars[i]);
			}
			
		}
		String codeDir=sb.toString();
		String ftlDir=fileCreator.getClass().getClassLoader().getResource("").getPath();
		System.out.println(ftlDir);
		fileCreator.ready(ftlDir+"/ftl/code/");
		fileCreator.createFile("abstractModel.ftl", goalMap, "AbstractModel", fastDir+codeDir+"\\dao\\model\\", "java");
		List<Map<String, Object>> data=dBInfo.getData();
		
		data.stream().forEach((map)->{
			try {
				fileCreator.createFile("model.ftl",map,map.get(Const.MODEL_TAG).toString(), fastDir+codeDir+"\\dao\\model\\","java");
				fileCreator.createFile("controller.ftl",map,map.get(Const.MODEL_TAG).toString()+"Controller", fastDir+codeDir+"\\web\\controller\\","java");
				fileCreator.createFile("mapper.ftl",map,map.get(Const.MODEL_TAG).toString()+"Mapper", fastDir+codeDir+"\\dao\\mapper\\","java");
				fileCreator.createFile("service.ftl",map,map.get(Const.MODEL_TAG).toString()+"ServiceImp", fastDir+codeDir+"\\service\\imp\\","java");
				fileCreator.createFile("serviceInterface.ftl",map,map.get(Const.MODEL_TAG).toString()+"Service", fastDir+codeDir+"\\service\\","java");
				fileCreator.createFile("xmlMapper.ftl",map,map.get(Const.MODEL_TAG).toString()+"Mapper", fastDir+"\\mapper\\","xml");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
