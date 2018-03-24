package com.ycyx28.study.client.service;

import com.ycyx28.study.api.IHelloWorld;
import com.ycyx28.study.api.dto.Hello;
import com.ycyx28.study.api.response.ServiceResponse;

public class HelloWordClientService {
	
	private IHelloWorld helloWorld ;

	public void setHelloWorld(IHelloWorld helloWorld) {
		this.helloWorld = helloWorld;
	}
	
	public void sayHello(Hello hello){
		ServiceResponse response = helloWorld.sayHello(hello);
		System.out.println(response);
	}

}
