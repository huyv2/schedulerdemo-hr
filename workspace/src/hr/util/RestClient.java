package hr.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {
	public String post(String schema, String host, String path, String data) throws Exception{
		URIBuilder builder = new URIBuilder();
		builder.setScheme(schema).setHost(host).
			setPath(path);
		URI uri = builder.build();
		
		HttpPost request = new HttpPost(uri);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		StringEntity input = new StringEntity(data);
		request.setEntity(input);
		HttpClientBuilder hcb = HttpClientBuilder.create();
		HttpClient client = hcb.build();
		HttpResponse response = client.execute(request);
		String msg=readIncomingData(response.getEntity().getContent());
		return msg;
	}
	
	public String post(String uriStr, String data) throws Exception{
		URI uri = new URI(uriStr);
		HttpPost request = new HttpPost(uri);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		StringEntity input = new StringEntity(data);
		request.setEntity(input);
		// huyvv add to set timeout parameters to avoid task pending when call http (*)
		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder.setConnectTimeout(15000);
		requestBuilder.setConnectionRequestTimeout(15000);
		requestBuilder.setSocketTimeout(15000);
		//
		HttpClientBuilder hcb = HttpClientBuilder.create();
		hcb.setDefaultRequestConfig(requestBuilder.build()); // huyvv add for (*)
		HttpClient client = hcb.build();
		HttpResponse response = client.execute(request);
		String msg=readIncomingData(response.getEntity().getContent());
		return msg;
	}
	
	private String readIncomingData(InputStream incomingData) throws Exception{
		String data="";
		StringBuilder strBuilder = new StringBuilder();
		InputStreamReader stream = new InputStreamReader(incomingData);
		BufferedReader in = new BufferedReader(stream);
		String line = null;
		while ((line = in.readLine()) != null) {
			strBuilder.append(line);
		}
		data=strBuilder.toString();
		stream.close();
		in.close();
		return data;	
	}
}
