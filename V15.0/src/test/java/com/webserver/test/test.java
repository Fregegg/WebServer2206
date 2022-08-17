package com.webserver.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Freg
 * @time 2022/8/16  17:25
 */
public class test {
    private String uri;//抽象路径
    private String requestURI;//抽象路径中的请求部分(URI中？左边内容)
    public String queryString;//抽象路径中的参数部分
    public Map<String,String>parameters = new HashMap<>();//保存抽象路径中的每一组参数

    public test(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void parseURI(String uri){
        if (!uri.contains("\\?")){
            requestURI = uri;
        }else {
            String[] data = uri.split("\\?");
            System.out.println(data);
            requestURI = data[0];
            queryString = data[1];
            String[] query = queryString.split("&");
            for (int i = 0; i < query.length; i++) {
                String[] text = query[i].split("=");
                if (text.length==1){
                    parameters.put(text[0],"");
                }else {
                    parameters.put(text[0], text[1]);
                }
                /*String key = query[i].split("=")[0];
                if (query[i].split("=").length==1){
                    parameters.put(key,"");
                }
                String value = query[i].split("=")[1];

                parameters.put(key,value);*/
            }

        }
    }

    public static void main(String[] args) {
        test t = new test("/regUser?username=cqcqcq&password=123123&nickname=cq&age=23");
        System.out.println(t.uri);
        t.parseURI(t.uri);
        Set<Map.Entry<String, String>> e = t.parameters.entrySet();
        for (Map.Entry<String, String> es : e) {
            System.out.println(es.getKey()+"\t"+es.getValue());
        }
        System.out.println(t.parameters);
    }
}
