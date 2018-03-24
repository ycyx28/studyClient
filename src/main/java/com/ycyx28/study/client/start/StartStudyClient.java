package com.ycyx28.study.client.start;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ycyx28.study.api.dto.Hello;
import com.ycyx28.study.client.context.SpringContext;
import com.ycyx28.study.client.service.HelloWordClientService;

public class StartStudyClient {
	private static Log log = LogFactory.getLog(StartStudyClient.class);

	private static volatile boolean running = true;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					SpringContext.stop();
					log.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Service stopped!");
				} catch (Throwable t) {
					log.error("Service stop error:" + t);
				}
				synchronized (StartStudyClient.class) {
					running = false;
					StartStudyClient.class.notify();
				}
			}
		});

		try {
			SpringContext.start();
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		
		HelloWordClientService  service  = SpringContext.getBean("helloWordClientService");
		Hello hello = new Hello();
		hello.setAge(22);
		hello.setName("yuchao");
		service.sayHello(hello);

		log.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Service started!");

		synchronized (StartStudyClient.class) {
			while (running) {
				try {
					StartStudyClient.class.wait();
				} catch (Throwable e) {
				}
			}
		}
	}

}
