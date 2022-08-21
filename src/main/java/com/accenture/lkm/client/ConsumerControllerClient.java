package com.accenture.lkm.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerControllerClient {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "emp/controller/getDetailsClient", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getEmployee(){

		// String baseUrl = "http://localhost:8090/emp/controller/getDetails";
		List<ServiceInstance> instances = discoveryClient.getInstances("cst-employee-producer");
		ServiceInstance serviceInstance = instances.get(0); // IPs, Port
		String baseUrl = serviceInstance.getUri().toString();// http://ip:port/
		System.out.println(baseUrl);
		baseUrl = baseUrl + "/emp/controller/getDetails";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
		System.out.println(response.getBody());
		return response;
	}
}