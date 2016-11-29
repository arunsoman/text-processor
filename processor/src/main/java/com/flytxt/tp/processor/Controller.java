package com.flytxt.tp.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


public class Controller {
	@Autowired
	Processor processor;

	@RequestMapping(value = "/reload")
	public String reload() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				processor.stopFileReads();
				try {
					processor.init();
				} catch (Exception e) {
					// TODO log this
					e.printStackTrace();
				}

			}
		});
		thread.start();
		return "OK";
	}

	@RequestMapping(value = "/stop")
	public String stop() {
		processor.stopFileReads();
		return "OK";
	}
}
