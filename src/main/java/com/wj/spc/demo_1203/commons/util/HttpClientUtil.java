package com.wj.spc.demo_1203.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * Created by liyaoqiang on 2018/12/05.
 */
public class HttpClientUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 发送GET请求，并将返回的JSON数据转化为对应的类型
     *
     * @param url
     * @param headerMap
     * @param prm
     * @return
     */
    public static String doGetWithContent(String url, Map<String, String> headerMap, Map<String, String> prm) {
        log.debug("发送get请求:" + url);
        HttpResponse response = doGet(url, headerMap, prm);
        String content = getResponseString(response);
        return content;
    }


    /**
     * httpClent 调用get方法
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static HttpResponse doGet(String url, Map<String, String> headerMap)  throws RuntimeException{
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        log.info("URL:" + url);
        HttpGet httpGet = new HttpGet(url);
        if (headerMap == null) {
            headerMap = new HashMap<String, String>();
        }
        httpGet.setHeaders(_parseHeadersFromMap(headerMap));

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("调用远程服务异常:", e);
            throw new RuntimeException("发送get请求时出现异常", e);
        }
        return response;
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param headerMap
     * @param prm
     * @return
     */
    public static HttpResponse doGet(String url, Map<String, String> headerMap, Map<String, String> prm) {
        String prmStr = _map2GetPrmStr(prm);
        if (prmStr != null) {
            url += ("?" + prmStr);
        }
        return doGet(url, headerMap);
    }

    private static Header[] _parseHeadersFromMap(Map<String, String> headerMap) {
        if (headerMap == null || headerMap.size() == 0) {
            return null;
        }
        List<Header> headerList = new ArrayList<>();
        Iterator<String> it = headerMap.keySet().iterator();
        Header header;
        String key;
        while (it.hasNext()) {
            key = it.next();
            header = new BasicHeader(key, headerMap.get(key));
            headerList.add(header);
        }
        Header[] headers = new Header[headerList.size()];

        return headerList.toArray(headers);
    }

    private static String _map2GetPrmStr(Map<String, String> prm) {
        if (prm == null || prm.size() == 0) {
            return null;
        }
        String prmStr = "", currentPrm = "";
        Set<String> prmKeySet = prm.keySet();
        for (String key : prmKeySet) {
            if (StringUtils.isBlank(prm.get(key))) {
                continue;
            }
            currentPrm = key + "=" + prm.get(key);
            if (prmStr.length() > 0) {
                currentPrm = "&" + currentPrm;
            }
            prmStr += currentPrm;
        }
        return prmStr;
    }

    /**
     * post请求
     *
     * @param url
     * @param values
     * @param headerMap
     * @return
     */
    public static HttpResponse doPost(String url, List<NameValuePair> values, Map<String, String> headerMap) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, Consts.UTF_8);
        httpPost.setEntity(entity);
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public static HttpResponse doPost(String url, InputStream inputStream, Map<String, String> headerMap, String fileName) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //以浏览器兼容模式运行，防止文件名乱码。
        HttpEntity reqEntity = null;
        try {
            reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("multipartFile", inputStream, ContentType.APPLICATION_OCTET_STREAM, fileName).setCharset(CharsetUtils.get("UTF-8")).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(reqEntity);
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public static HttpResponse doPost(String url, InputStream inputStream, Map<String, String> headerMap, String fileName, String paramName) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //以浏览器兼容模式运行，防止文件名乱码。
        HttpEntity reqEntity = null;
        try {
            reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody(paramName, inputStream, ContentType.APPLICATION_OCTET_STREAM, fileName).setCharset(CharsetUtils.get("UTF-8")).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(reqEntity);
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
//    public static HttpResponse doPost(String url,File file,Map<String,String> headerMap) {
//        System.out.println("file====="+file.getName()+"    existed"+file.exists());
//
//        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
//        CloseableHttpResponse httpResponse = null;
//        HttpPost httpPost = new HttpPost(url);
//        if(headerMap!=null)
//        {
//            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, String> entry = it.next();
//                httpPost.setHeader(entry.getKey(),entry.getValue());
//            }
//        }
//        MultipartEntity
//        FileBody fileBody = new FileBody(file);
//        //以浏览器兼容模式运行，防止文件名乱码。
//        HttpEntity reqEntity = null;
//        try {
//            reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                    .addBinaryBody("multipartFile", inputstream)
//                    .addPart("multipartFile", fileBody).setCharset(CharsetUtils.get("UTF-8")).build();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        httpPost.setEntity(reqEntity);
//        try {
//            httpResponse = httpClient.execute(httpPost);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return httpResponse;
//    }

    /**
     * 发送post请求（requestBodyEncoding：utf-8,requestBodyContentType：application/json）
     *
     * @param url
     * @param requestBody
     * @param headerMap
     * @return
     */
    public static HttpResponse doPost(String url, String requestBody, Map<String, String> headerMap) {
        log.info("httpclient:"+url+"requestBody:"+requestBody);
        return doPost(url, requestBody, Consts.UTF_8.displayName(), "application/json", headerMap);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param requestBody
     * @param requestBodyEncoding
     * @param requestBodyContentType
     * @param headerMap
     * @return
     */
    public static HttpResponse doPost(String url, String requestBody, String requestBodyEncoding, String requestBodyContentType, Map<String, String> headerMap) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        StringEntity entity = new StringEntity(requestBody, requestBodyEncoding);//解决中文乱码问题
        entity.setContentType(requestBodyContentType);
        httpPost.setEntity(entity);
        for (Header header : httpPost.getAllHeaders()) {
            System.out.println(header.toString());
        }
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            // throw new ProcessorException(Constants.ErrorStatusEnum.ERR_500,"解析返回值时出现异常",e);
            throw new RuntimeException(e);
        }
        System.out.println(httpResponse);
        return httpResponse;
    }

    /**
     * 发送put请求（requestBodyEncoding：utf-8,requestBodyContentType:application/json）
     *
     * @param url
     * @param requestBody
     * @param headerMap
     * @return
     */
    public static HttpResponse doPut(String url, String requestBody, Map<String, String> headerMap) {

        return doPut(url, requestBody, Consts.UTF_8.displayName(), "application/json", headerMap);
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param requestBody
     * @param requestBodyEncoding
     * @param requestBodyContentType
     * @param headerPrm
     * @return
     */
    public static HttpResponse doPut(String url, String requestBody, String requestBodyEncoding, String requestBodyContentType, Map<String, String> headerPrm) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse httpResponse = null;

        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeaders(_parseHeadersFromMap(headerPrm));

        StringEntity entity = new StringEntity(requestBody, requestBodyEncoding);
        entity.setContentType(requestBodyContentType);
        httpPut.setEntity(entity);

        try {
            httpResponse = httpClient.execute(httpPut);
        } catch (IOException e) {
            //throw new ProcessorException(Constants.ErrorStatusEnum.ERR_500,"发送put请求时出现异常",e);
            throw new RuntimeException(e);
        }
        return httpResponse;
    }

    /**
     * 发送delete请求
     *
     * @param url
     * @param headerMap
     * @return
     */
    public static HttpResponse doDelete(String url, Map<String, String> headerMap) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpResponse httpResponse = null;
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpDelete.setHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            httpResponse = httpClient.execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * 获取响应信息
     *
     * @param httpResponse
     * @return
     */
    public static String getResponseString(HttpResponse httpResponse) {
        try {
            return EntityUtils.toString(httpResponse.getEntity(), Consts.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据用户名密码计算基础认证字符串
     * 用户名或密码为空会抛出异常
     *
     * @param userName
     * @param password
     * @return
     */
    public static String getBasicAuthorizationStr(String userName, String password) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            throw new RuntimeException("系统异常");
        }
        return "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    public static String uploadFile(String url, String fileName, InputStream inputStream, Map<String, String> headerMap) {
        URL postUrl = null;
        try {
            postUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) postUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            connection.setRequestMethod("POST");
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
        }


        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");

        String BOUNDARY = "----------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        //第一部分
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
                + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        try {
            byte[] head = sb.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        byte[] head = new byte[0];
        try {
            head = sb.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        // 获得输出流
        OutputStream out = null;
        try {
            out = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 输出表头
        try {
            out.write(head);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 文件正文部分

        DataInputStream in = new DataInputStream(inputStream);
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        try {
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 结尾部分
        byte[] foot = new byte[0];// 定义最后数据分隔线
        try {
            foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            out.write(foot);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inStrm = null;
        try {
            inStrm = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String result = inputStream2String(inStrm);
        return result;
    }

    //inputstream转string
    private static String inputStream2String(InputStream is) {
        BufferedReader in = null;
        //指定读取输入流编码,utf-8防止中文乱码
        try {
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return buffer.toString();
    }

    /**
     * 超时机制的post 请求
     * @param url url
     * @param requestBody 请求体
     * @param requestBodyEncoding 编码
     * @param requestBodyContentType 返回
     * @param headerMap 头部
     * @return 请求返回
     */
    public static HttpResponse doPost(String url, String requestBody, String requestBodyEncoding, String requestBodyContentType, Map<String, String> headerMap, int socketTimeout, int connectTimeout) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = null;
        if (headerMap != null) {
            Iterator<Map.Entry<String, String>> it = headerMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        StringEntity entity = new StringEntity(requestBody, requestBodyEncoding);//解决中文乱码问题
        entity.setContentType(requestBodyContentType);
        httpPost.setEntity(entity);
        for (Header header : httpPost.getAllHeaders()) {
            log.info("请求URL{}的头部信息为{}", url,header.toString());
        }
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (Exception e) {
            log.error("请求URL{}异常,请求参数为{},异常信息{}", url, requestBody, e);
        }
        if (null != httpResponse){
            log.info("请求URL{}返回状态码为{}", url, httpResponse.getStatusLine().getStatusCode());
        } else {
            log.info("请求URL{}返回状态码为null", url);
        }
        return httpResponse;
    }

    /**
     * 带超时的Get请求
     * @param url 请求URL
     * @param headerMap 头部
     * @param prm 参数
     * @param socketTimeout 超时时间
     * @param connectTimeout 链接时间
     * @return 返回参数
     */
    public static HttpResponse doGet(String url, Map<String, String> headerMap, Map<String, String> prm, int socketTimeout, int connectTimeout) {
        String prmStr = _map2GetPrmStr(prm);
        if (prmStr != null) {
            url += ("?" + prmStr);
        }
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        log.info("URL:" + url);
        HttpGet httpGet = new HttpGet(url);
        if (headerMap == null) {
            headerMap = new HashMap<String, String>();
        }
        httpGet.setHeaders(_parseHeadersFromMap(headerMap));
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            log.error("调用远程URL{}服务异常:{}", url, e);
        }
        return response;
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param requestBody
     * @param requestBodyEncoding
     * @param requestBodyContentType
     * @param headerPrm
     * @return
     */
    public static HttpResponse doPut(String url, String requestBody, String requestBodyEncoding, String requestBodyContentType, Map<String, String> headerPrm, int socketTimeout, int connectTimeout) {
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();
        CloseableHttpResponse httpResponse = null;
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeaders(_parseHeadersFromMap(headerPrm));
        StringEntity entity = new StringEntity(requestBody, requestBodyEncoding);
        entity.setContentType(requestBodyContentType);
        httpPut.setEntity(entity);
        try {
            httpResponse = httpClient.execute(httpPut);
        } catch (IOException e) {
           log.error("发送请求{}异常 参数{},异常", url, requestBody, e);
        }
        return httpResponse;
    }
}
