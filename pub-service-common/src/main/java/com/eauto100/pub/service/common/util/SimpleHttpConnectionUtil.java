package com.eauto100.pub.service.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * HTTP发送请求
 * @author justin.qu
 * Email: kangmin_qu@dell.com
 */
public class SimpleHttpConnectionUtil {

    /** The send method : POST. */
    public static final String SEND_METHOD_POST = "POST";

    /** The send method : GET. */
    public static final String SEND_METHOD_GET = "GET";

    /** The char set : UTF-8. */
    public static final String CHARSET_UTF8 = "UTF-8";

    /** The char set : GB2312. */
    public static final String CHARSET_GB2312 = "GB2312";

    /** The char set : GBK. */
    public static final String CHARSET_GBK = "GBK";

    /** HTTP request property : HTTP1.1 FORM. */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    /** HTTP request property : HTTP1.1 TEXT XML. */
    public static final String CONTENT_TYPE_TEXT_XML = "text/xml";

    /** HTTP request property : HTTP1.1 APPLICATION XML. */
    public static final String CONTENT_TYPE_APP_XML = "application/xml";
    
    static Logger logger = Logger.getLogger(SimpleHttpConnectionUtil.class.getName());

    /** The default char set : UTF-8 */
    private static final String DEFAULT_CHARSET = CHARSET_UTF8;

    /** The default connection time out seconds. */
    private static final long DEFAULT_CONNECT_TIME_OUT = 5;

    /** The default read time out seconds. */
    private static final long DEFAULT_READ_TIME_OUT = 30;

    /** The separator symbol. */
    private static final String SYMBOL_SEPERATOR = "&";

    /** The end line symbol. */
    private static final String SYMBOL_END_LINE = "\n";

    /** The request parameters symbol. */
    private static final String SYMBOL_REQUEST = "?";

    /** The blank string. */
    private static final String BLANK_STRING = "";

    /** The content type char set key. */
    private static final String CONTENT_TYPE_CHARSET_KEY = "charset=";

    /** The HTTP response code : OK */
    private static final int HTTP_OK = 200;

    /** 1 Second is equal to 1,000 millisecond. */
    private static final long MILLI_SECOND = 1000;

    /** HTTP request property : content type. */
    private static final String CONTENT_TYPE = "Content-type";

    /** HTTP request property : char set. */
    private static final String CONTENT_KEY_CHATSET = "; charset=";

    /**
     * Send the HTTP request and return the response string.<BR>
     * The default method is POST , char set is UTF-8 , connection time out is 5 seconds , read time out is 30 seconds.
     * 
     * @param sendUrl The request URL, it should begin with 'http://' or 'https://'. The sendUrl should not be null.
     * @param sendParameters The request key-value parameters map. It could be null or empty.
     * @return The response string.
     * @throws IOException
     */
    public static String sendHttpRequest(String sendUrl, Map<String, String> sendParameters) throws IOException {
        return sendHttpRequest(sendUrl, sendParameters, null, DEFAULT_CHARSET, DEFAULT_CONNECT_TIME_OUT,
                DEFAULT_READ_TIME_OUT);
    }

    /**
     * Send the HTTP request and return the response string.<BR>
     * The default connection time out is 5 seconds , read time out is 30 seconds.
     * 
     * @param sendUrl The request URL, it should begin with 'http://' or 'https://'. The sendUrl should not be null.
     * @param sendParameters The request key-value parameters map. It could be null or empty.
     * @param method The HTTP send message method. For example 'POST', 'GET'. Default is POST.
     * @param charset The char set for coding and encoding string when send request and receive response. For example
     *            'GBK', 'UTF-8'. Default is UTF-8.
     * @return The response string.
     * @throws IOException
     */
    public static String sendHttpRequest(String sendUrl, Map<String, String> sendParameters, String method,
            String charset) throws IOException {
        return sendHttpRequest(sendUrl, sendParameters, method, charset, DEFAULT_CONNECT_TIME_OUT,
                DEFAULT_READ_TIME_OUT);
    }

    /**
     * Send the HTTP request and return the response string.
     * 
     * @param sendUrl The request URL, it should begin with 'http://' or 'https://'. The sendUrl should not be null.
     * @param sendParameters The request key-value parameters map. It could be null or empty.
     * @param method The HTTP send message method. For example 'POST', 'GET'. Default is POST.
     * @param charset The char set for coding and encoding string when send request and receive response. For example
     *            'GBK', 'UTF-8'. Default is UTF-8.
     * @param connectTimeOutSeconds The connect time out in seconds. For example '10' means 10 seconds.
     * @param readTimeOutSeconds The read time out in seconds. For example '10' means 10 seconds.
     * @return The response string.
     * @throws IOException
     */
    public static String sendHttpRequest(String sendUrl, Map<String, String> sendParameters, String method,
            String charset, long connectTimeOutSeconds, long readTimeOutSeconds) throws IOException {
        // build send message.
        String sendMessage = buildMessage(null == sendParameters ? new HashMap<String, String>() : sendParameters,
                charset);
        return sendHttpRequest(sendUrl, sendMessage, CONTENT_TYPE_FORM, method, charset, connectTimeOutSeconds,
                readTimeOutSeconds);
    }

    /**
     * Send the HTTP request and return the response string.
     * 
     * @param sendUrl The request URL, it should begin with 'http://' or 'https://'. The sendUrl should not be null.
     * @param sendMessage The message for send. It could be null or empty.
     * @param contentType The content type of HTTP request head. The contentType should not be null.
     * @param method The HTTP send message method. For example 'POST', 'GET'. Default is POST.
     * @param charset The char set for coding and encoding string when send request and receive response. For example
     *            'GBK', 'UTF-8'. Default is UTF-8.
     * @return The response string.
     * @throws IOException
     */
    public static String sendHttpRequest(String sendUrl, String sendMessage, String contentType, String method,
            String charset) throws IOException {
        return sendHttpRequest(sendUrl, sendMessage, contentType, method, charset, DEFAULT_CONNECT_TIME_OUT,
                DEFAULT_READ_TIME_OUT);
    }

    /**
     * Send the HTTP request and return the response string.
     * 
     * @param sendUrl The request URL, it should begin with 'http://' or 'https://'. The sendUrl should not be null.
     * @param sendMessage The message for send. It could be null or empty.
     * @param contentType The content type of HTTP request head. The contentType should not be null.
     * @param method The HTTP send message method. For example 'POST', 'GET'. Default is POST.
     * @param charset The char set for coding and encoding string when send request and receive response. For example
     *            'GBK', 'UTF-8'. Default is UTF-8.
     * @param connectTimeOutSeconds The connect time out in seconds. For example '10' means 10 seconds.
     * @param readTimeOutSeconds The read time out in seconds. For example '10' means 10 seconds.
     * @return The response string.
     * @throws IOException
     */
    public static String sendHttpRequest(String sendUrl, String sendMessage, String contentType, String method,
            String charset, long connectTimeOutSeconds, long readTimeOutSeconds) throws IOException {
        // validate send URL.
        if (sendUrl == null || sendUrl.length() == 0) {
            throw new IOException("Request param error : sendUrl is null.");
        }
        if (method == null || method.trim().length() == 0) {
            if (sendMessage == null || sendMessage.trim().length() == 0) {
                method = SEND_METHOD_GET;
            } else {
                method = SEND_METHOD_POST;
            }
        }
        if (SEND_METHOD_POST.equals(method) && (sendMessage == null || sendMessage.length() == 0)) {
            throw new IOException("Request param error : sendMessage is null.");
        }
        if (contentType == null || contentType.length() == 0) {
            throw new IOException("Request param error : contentType is null.");
        }
        if (charset == null || charset.trim().length() == 0) {
            charset = CHARSET_UTF8;
        }
        // send request.
        HttpURLConnection conn = null;
        String responseString = null;
        Map<String, List<String>> responseHeader = null;
        OutputStream outStrm = null;
        DataOutputStream out = null;
        InputStream inStream = null;
        try {
            // make URL.
            URL url = buildURL(sendUrl, sendMessage, method);
            // make HTTP connection.
            conn = (HttpURLConnection) url.openConnection();
            initConnection(conn, method, contentType, charset, Long.valueOf(connectTimeOutSeconds * MILLI_SECOND),
                    Long.valueOf(readTimeOutSeconds * MILLI_SECOND));
            // send request.
            conn.connect();
            if (method != null && SEND_METHOD_POST.equals(method) && sendMessage != null) {
                outStrm = conn.getOutputStream();
                out = new DataOutputStream(outStrm);
                logger.info("sendMessage: "+sendMessage);
                // out.writeBytes(sendMessage);
                out.write(sendMessage.getBytes("GBK"));
                out.flush();
            }
            // build response string.
            int respCode = conn.getResponseCode();
            String responseCharset = charset;
            if (conn.getContentType() != null && conn.getContentType().indexOf(CONTENT_TYPE_CHARSET_KEY) > 0) {
                responseCharset = conn.getContentType().substring(
                        conn.getContentType().indexOf(CONTENT_TYPE_CHARSET_KEY) + CONTENT_TYPE_CHARSET_KEY.length());
                responseCharset = formatCharset(responseCharset);
            }
            responseHeader = conn.getHeaderFields();
            
            if (respCode != HTTP_OK) {
                inStream = conn.getErrorStream();
                String responseMessage = readInputStream(inStream, responseCharset);
                throw new IOException("Http connection fail : [" + respCode + "] " + conn.getResponseMessage() + "\n"
                        + responseMessage);
            } else {
                inStream = conn.getInputStream();
            }
            responseString = readInputStream(inStream, responseCharset);
        } catch (IOException e) {
            throw new IOException("IOException: " + e + "\nURL = " + sendUrl + "\nParameter = "
                    + sendMessage.toString() + "\nResponse Header = " + responseHeader);
        } finally {
            if(outStrm != null){
                outStrm.close();
            }
            if(out != null){
                out.close();
            }
            if(inStream != null){
                inStream.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
            
        }
        return responseString;
    }

    /**
     * Read the input stream and build the message string.
     * 
     * @param inStream
     * @param charset
     * @return
     * @throws IOException
     */
    private static String readInputStream(InputStream inputStream, String charset) throws IOException {
        String response = null;
        if (inputStream != null) {
            try {
                BufferedInputStream bufferedinputstream = new BufferedInputStream(inputStream);
                InputStreamReader isr = new InputStreamReader(bufferedinputstream, charset);
                BufferedReader in = new BufferedReader(isr);
                StringBuffer buff = new StringBuffer();
                String readLine = null;
                String endLine = SYMBOL_END_LINE;
                while ((readLine = in.readLine()) != null) {
                    buff.append(readLine).append(endLine);
                }
                if (buff.length() > 0) {
                    response = buff.substring(0, buff.length() - 1);
                } else {
                    response = buff.toString();
                }
            } catch (UnsupportedEncodingException e) {
                throw new IOException("Unsupported Encoding Exception: " + e);
            } catch (IOException e) {
                throw new IOException("IOException: " + e);
            }
        } else {
            throw new IOException("Error Input Parameter inputStream is null.");
        }
        return response;
    }

    /**
     * Build URL for POST and GET method.
     * 
     * @param URL
     * @param message
     * @param method
     * @return
     * @throws IOException
     */
    private static URL buildURL(String urlString, String message, String method) throws IOException {
        URL url = null;
        try {
            if (SEND_METHOD_GET.equals(method)) {
                if (message == null || message.length() == 0) {
                    url = new URL(urlString);
                } else if (urlString.indexOf(SYMBOL_REQUEST) > -1) {
                    url = new URL(urlString + SYMBOL_SEPERATOR + message);
                } else {
                    url = new URL(urlString + SYMBOL_REQUEST + message);
                }
            } else if (SEND_METHOD_POST.equals(method)) {
                url = new URL(urlString);
            } else {
                throw new IOException("Error Send Method Parameter: " + method);
            }
        } catch (MalformedURLException e) {
            throw new IOException("Create URL Error: " + e);
        }

        return url;
    }

    /**
     * Initial the connection for POST and GET method.
     * 
     * @param connection
     * @param method
     * @param contentType
     * @param charset
     * @param connectTimeOut
     * @param readTimeOut
     * @throws IOException
     */
    private static void initConnection(HttpURLConnection connection, String method, String contentType, String charset,
            Long connectTimeOut, Long readTimeOut) throws IOException {
        Map<String, String> properties = new HashMap<String, String>();
        if (method != null && SEND_METHOD_POST.equals(method)) {
            if (contentType != null && contentType.trim().length() > 0) {
                properties.put(CONTENT_TYPE, contentType);
                if (charset != null && charset.trim().length() > 0) {
                    properties.put(CONTENT_TYPE, properties.get(CONTENT_TYPE) + CONTENT_KEY_CHATSET + charset);
                }
            }
        }
        initConnection(connection, method, properties, connectTimeOut, readTimeOut);
    }

    /**
     * Initial the connection for POST and GET method.
     * 
     * @param connection
     * @param method
     * @param requestProperties
     * @param connectTimeOut
     * @param readTimeOut
     * @throws IOException
     */
    private static void initConnection(HttpURLConnection connection, String method,
            Map<String, String> requestProperties, Long connectTimeOut, Long readTimeOut) throws IOException {
        if (connection != null) {
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setDoInput(true);
            if (method != null && method.trim().length() > 0) {
                try {
                    connection.setRequestMethod(method);
                } catch (ProtocolException e) {
                    throw new IOException("Set Request Method Error: " + e);
                }
                if (SEND_METHOD_POST.equals(method)) {
                    connection.setDoOutput(true);
                }
            } else {
                throw new IOException("Error Method Parameter: " + method);
            }
            if (requestProperties != null && requestProperties.size() > 0) {
                String key, value;
                for (Map.Entry<String, String> tempEntry : requestProperties.entrySet()) {
                    key = tempEntry.getKey();
                    value = tempEntry.getValue();
                    connection.setRequestProperty(key, value);
                }
            }
            if (connectTimeOut != null && readTimeOut != null) {
                System.setProperty("sun.net.client.defaultConnectTimeout", connectTimeOut.toString());
                System.setProperty("sun.net.client.defaultReadTimeout", readTimeOut.toString());
                connection.setConnectTimeout(connectTimeOut.intValue());
                connection.setReadTimeout(readTimeOut.intValue());
            }
            connection.setRequestProperty("User-Agent", "Mozilla/MSIE");
        } else {
            throw new IOException("Error HttpURLConnection Parameter connection is null.");
        }
    }

    /**
     * Build string from parameters map.
     * 
     * @param parameters The request key-value parameters map. It could be null or empty.
     * @param charset The char set for coding and encoding string when send request and receive response. For example
     *            'GBK', 'UTF-8'. Default is UTF-8.
     * @return String's format is [key1][symbolEqual][value1]{[separator][key2][symbolEqual][value2]...}.
     * @throws IOException
     */
    private static String buildMessage(Map<String, String> parameters, String charset) throws IOException {
        if (parameters == null) {
            parameters = new HashMap<String, String>();
        }
        if (charset == null || charset.length() == 0) {
            charset = DEFAULT_CHARSET;
        }
        StringBuffer postStringBuffer = new StringBuffer();
        // try {
        String value;
        for (Map.Entry<String, String> tempEntry : parameters.entrySet()) {
            value = tempEntry.getValue();
            value = value == null ? BLANK_STRING : value;
            // postStringBuffer.append(URLEncoder.encode(key, charset)); //TODO
            // postStringBuffer.append(key);
            // postStringBuffer.append(SYMBOL_EQUAL);
            postStringBuffer.append(value);
            // postStringBuffer.append(URLEncoder.encode(value, charset));/
            postStringBuffer.append(SYMBOL_SEPERATOR);
        }
        // } catch (UnsupportedEncodingException e) {
        // throw new IOException("Unsupported Encoding Error: " + e);
        // }
        String message = null;
        if (postStringBuffer.length() > 0) {
            message = postStringBuffer.substring(0, postStringBuffer.length() - 1);
        } else {
            message = postStringBuffer.toString();
        }
        return message;
    }

    /** Format the char-set string. */
    private static String formatCharset(String responseCharset) {
        String result = responseCharset;
        if (result != null && result.length() > 0) {
            result = result.trim();
            result = result.replaceAll(";", "");
        }
        return result;
    }
    
}
