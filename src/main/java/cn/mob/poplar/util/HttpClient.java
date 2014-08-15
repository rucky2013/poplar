package cn.mob.poplar.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpClient {
    private static final String BOUNDARY = "LamfireFormBoundaryucJiylDzwZWyoOSF";
    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());
    private final Map<String, String> requestHeaderMap = new HashMap<String, String>();
    private HttpURLConnection conn;
    private String charset = "UTF-8";
    private String contentType = ContentType.application_x_www_form_urlencoded;
    private String method = "GET";
    private int connectTimeOut = 30000;
    private int readTimeOut = 30000;
    private StringBuffer queryBuffer;
    private DataOutputStream output = null;

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;

    }

    public void addPostParameter(String name, String value) {
        if (queryBuffer == null) {
            queryBuffer = new StringBuffer();
            queryBuffer.append(name + "=" + URLUtils.encode(value, charset));
            return;
        }
        queryBuffer.append("&");
        queryBuffer.append(name + "=" + URLUtils.encode(value, charset));
    }

    public void post() throws IOException {
        if (queryBuffer == null) {
            throw new RuntimeException("The post parameter is empty.please first invoke 'addPostParameter(String name,String value)' try again.");
        }
        this.post(queryBuffer.toString().getBytes());
    }

    public void post(byte[] bytes) throws IOException {
        if (output == null) {
            throw new RuntimeException("method [" + this.method + "] output stream has not opened.");
        }

        output.write(bytes);
        output.flush();

    }

    public void setRequestHeader(String key, String value) {
        this.requestHeaderMap.put(key, value);
    }

    public void setConnectTimeout(int millis) {
        this.connectTimeOut = millis;
    }

    public void setReadTimeout(int millis) {
        this.readTimeOut = millis;
    }

    public void open(String url) throws IOException {
        open(new URL(url));
    }

    public void open(URL url) throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2");
        conn.setReadTimeout(readTimeOut);
        conn.setConnectTimeout(connectTimeOut);
        conn.setUseCaches(false);
        conn.setDoInput(true);

        boolean isGetMethod = "GET".equalsIgnoreCase(method);
        if (isGetMethod) {
            conn.setDoOutput(false);
            conn.setInstanceFollowRedirects(false);
        } else {
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", charset);
            conn.setRequestProperty("Content-Type", contentType);
        }

        for (Map.Entry<String, String> entry : requestHeaderMap.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 打开连接
        conn.connect();

        if (!isGetMethod) {
            //打开输出流
            output = new DataOutputStream(conn.getOutputStream());
        }
    }

    public byte[] read() throws IOException {
        InputStream input = getInputStream();
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        IOUtils.copy(input, result);
        // 读取完成
        return result.toByteArray();
    }

    public InputStream getInputStream() throws IOException {
        return conn.getInputStream();
    }

    public OutputStream getOutputStream() {
        return output;
    }

    public String readAsString() throws IOException {
        return readAsString(charset);
    }

    public String readAsString(String encoding) throws IOException {
        byte[] content = read();
        if (content == null) return null;
        return new String(content, encoding);
    }

    public String getResponseHeader(String key) {
        return conn.getHeaderField(key);
    }

    public Map<String, List<String>> getResponseHeaders() {
        return conn.getHeaderFields();
    }

    public String getContentEncoding() {
        return conn.getContentEncoding();
    }

    public int getContentLength() {
        return conn.getContentLength();
    }

    public int getResponseCode() throws IOException {
        return conn.getResponseCode();
    }

    public String getResponseMessage() throws IOException {
        return conn.getResponseMessage();
    }

    public void sendMultipartFormItem(String formItemName, String value) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\r\n--" + BOUNDARY + "\r\n");
        buffer.append("Content-Disposition: form-data; name=\"" + formItemName + "\"\r\n");
        buffer.append("Content-Type: text/plain;charset=" + charset + "\r\n\r\n");
        buffer.append(value);

        output.write(buffer.toString().getBytes());
        output.flush();
    }

    public void sendMultipartFileAsByteArray(String formItemName, String fileName, byte[] bytes) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\r\n--" + BOUNDARY + "\r\n");
        buffer.append("Content-Disposition: form-data; name=\"" + formItemName + "\"; filename=\"" + fileName + "\"\r\n");
        buffer.append("Content-Type: application/octet-stream\r\n\r\n");

        output.write(buffer.toString().getBytes());
        output.write(bytes);
        output.flush();
    }

    public void sendMultipartFile(String formItemName, File file) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            sendMultipartFileAsStream(formItemName, file.getName(), fis);
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    public void sendMultipartFileAsStream(String formItemName, String fileName, InputStream input) throws IOException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\r\n--" + BOUNDARY + "\r\n");
        buffer.append("Content-Disposition: form-data; name=\"" + formItemName + "\"; filename=\"" + fileName + "\"\r\n");
        buffer.append("Content-Type: application/octet-stream\r\n\r\n");
        output.write(buffer.toString().getBytes());
        IOUtils.copy(input, output);
        output.flush();

    }

    public void sendMultipartFinish() {
        String endstr = "\r\n--" + BOUNDARY + "--\r\n";
        byte[] endbytes = endstr.getBytes();
        try {
            output.write(endbytes);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void close() {
        IOUtils.closeQuietly(output);
        conn.disconnect();
    }

    public static class ContentType {
        public static final String application_x_www_form_urlencoded = "application/x-www-form-urlencoded";
        public static final String multipart_form_data = "multipart/form-data; boundary=" + BOUNDARY;

    }

}
