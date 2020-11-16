package com.goodreads.search.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpClient {
	
	private static HttpClient sInstance;
	private OkHttpClient client = new OkHttpClient();

	public static HttpClient getInstance() {
		if (sInstance == null) {
			sInstance = new HttpClient();
		}
		return sInstance;
	}

	private HttpClient() {
	}

	public String get(String url, String apiToken) throws IOException {
		Request request = getRequest(url, apiToken);
		String jsonData;
		//this is try-with-resources and since Response implements Closeable, this is meant to autoclose connections
		//also reduces amount of code required in try-catch-finally-nullcheck-close etc
		//only those objects which implement AutoCloseable can be opened inside the block
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code: " + response);
			}

			jsonData = response.body().string();
		}
		return jsonData;
	}


	private Request getRequest(String url, String token) {
		return new Request.Builder().url(url).build();
	}
	
	public OkHttpClient getClient() {
		return client;
	}

	public String get(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		String jsonData;
		//this is try-with-resources and since Response implements Closeable, this is meant to autoclose connections
		//also reduces amount of code required in try-catch-finally-nullcheck-close etc
		//only those objects which implement AutoCloseable can be opened inside the block
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code: " + response);
			}

			jsonData = response.body().string();
		}
		return jsonData;

	}
}
