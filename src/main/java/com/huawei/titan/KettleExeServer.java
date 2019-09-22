package com.huawei.titan;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KettleExeServer {

	@Value("${ktr.pathname}")
	private String filename;

	/**
	 * 
	 * @param filename
	 */
	public void runTrans() {
		try {
			System.out.println("filename-start-->" + filename);
			// filename = "D:" + File.separator + "tools" + File.separator
			// + "pdi-ce-7.1.0.0-12" + File.separator + "demo01"
			// + File.separator + "demo01.ktr";
			System.out.println("filename-end-->" + filename);
			KettleEnvironment.init();
			TransMeta transMeta = new TransMeta(filename);
			Trans trans = new Trans(transMeta);
			trans.prepareExecution(null);
			trans.startThreads();
			trans.waitUntilFinished();
			if (trans.getErrors() != 0) {
				log.error("Error");
			}
		} catch (KettleXMLException e) {
			log.error("KettleXMLException:" + e.getMessage());
			e.printStackTrace();
		} catch (KettleException e) {
			log.error("KettleException:" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void runJob(String jobname) {
		try {
			KettleEnvironment.init();
			// jobname 是Job脚本的路径及名称
			JobMeta jobMeta = new JobMeta(jobname, null);
			Job job = new Job(null, jobMeta);
			// 向Job 脚本传递参数，脚本中获取参数值：${参数名}
			// job.setVariable(paraname, paravalue);
			job.start();
			job.waitUntilFinished();
			if (job.getErrors() > 0) {
				System.out.println("decompress fail!");
			}
		} catch (KettleException e) {
			System.out.println(e);
		}
	}
	// public static void main(String[] args) {
	//
	// // String jobname =
	// // "F:\\ETL\\kettle\\例子\\测试\\复制多表\\copymanytablejob.kjb";
	// // runJob(jobname);
	// String filename = "D:" + File.separator + "tools" + File.separator
	// + "pdi-ce-7.1.0.0-12" + File.separator + "demo01"
	// + File.separator + "demo01.ktr";
	// runTrans(filename);
	// }
}
