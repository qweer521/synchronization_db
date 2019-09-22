package com.huawei.titan;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Configuration // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
@Slf4j
public class DBScheduleTask {

	@Autowired
	KettleExeServer kettleExeServer;

	// 3.添加定时任务
	@Scheduled(cron = "0/5 * * * * ?")
	// 或直接指定时间间隔，例如：5秒
	// @Scheduled(fixedRate=5000)
	private void configureTasks() {
		log.debug("执行静态定时任务时间: " + LocalDateTime.now());
		kettleExeServer.runTrans();
	}
}
