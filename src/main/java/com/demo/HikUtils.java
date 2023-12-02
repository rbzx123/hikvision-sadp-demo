package com.demo;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.*;


public class HikUtils {

    private final OkHttpClient httpClient;

    public HikUtils(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String get(String url) {
        System.out.println(String.format("url=[%s]", url));
        Request request = new Request.Builder().url(url).get().build();
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Response response = httpClient.newCall(request).execute();
                if (response.body() != null) {
                    String respXMl = response.body().string();
                    System.out.println(String.format("url=[%s],data=[\r\n%s\r\n]", url, respXMl));
                    return respXMl;
                }
                return null;
            }
        });

        new Thread(() -> {
            futureTask.run();
        }).start();

        try {
            return futureTask.get(3, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            System.out.println("GET请求失败:" + url + "\t" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String put(String url, String xmlData) {
        if (xmlData == null || xmlData.trim().length() == 0) {
            xmlData = "";
        }

        System.out.println(String.format("url=[%s],data=[\r\n%s\r\n]", url, xmlData));
        try {
            RequestBody requestBody = null;

            requestBody = RequestBody.create(xmlData, MediaType.parse("application/xml"));
            Request request = new Request.Builder().url(url).put(requestBody).build();

            FutureTask<Response> responseFutureTask = new FutureTask<>(() -> httpClient.newCall(request).execute());
            new Thread(() -> {
                responseFutureTask.run();
            }).start();
            Response response = responseFutureTask.get(3, TimeUnit.SECONDS);
            if (response != null && response.body() != null) {
                String respXMl = response.body().string();
                System.out.println(String.format("url=[%s],data=[\r\n%s\r\n]", url, respXMl));
                return respXMl;
            }
        } catch (Exception e) {
            System.out.println("PUT请求失败:" + url + "\t" + e.getMessage());
        }
        return null;
    }

    public String post(String url, String xmlData) {
        if (xmlData == null || xmlData.trim().length() == 0) {
            xmlData = "";
        }

        System.out.println(String.format("url=[%s],data=[\r\n%s\n]", url, xmlData));
        try {

            RequestBody requestBody = null;
            requestBody = RequestBody.create(xmlData, MediaType.parse("application/xml"));

            Request request = new Request.Builder().url(url).post(requestBody).build();
            FutureTask<Response> responseFutureTask = new FutureTask<>(() -> httpClient.newCall(request).execute());
            new Thread(() -> {
                responseFutureTask.run();
            }).start();
            Response response = responseFutureTask.get(3, TimeUnit.SECONDS);
            if (response != null && response.body() != null) {
                String respXMl = response.body().string();
                System.out.println(String.format("url=[%s],data=[\r\n%s\r\n]", url, respXMl));
                return respXMl;
            }
        } catch (Exception e) {
            System.out.println("POST请求失败:" + url + "\t" + e.getMessage());
        }
        return null;
    }
}
