package payment.chen.service.common.http;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payment.chen.service.common.util.PropertyUtil;
import payment.chen.service.common.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpExecutor {
    private static final Logger logger = LoggerFactory.getLogger(HttpExecutor.class);
    public static final int DEFAULT_TIMEOUT = 15 * 1000;
    public static final String DEFAULT_ENCODING = "UTF-8";

    private HttpClientContext httpContext;
    private CloseableHttpClient httpClient;
    private RequestConfig defaultRequestConfig;
    private boolean useCookie = true;
    //如果选择使用代理，系统回到配置文件中查询代理信息
    private static final boolean USE_PROXY_DEF = true;
    private int timeout;
    private String encoding;

    public HttpExecutor() {
        this(true,USE_PROXY_DEF, DEFAULT_ENCODING, DEFAULT_TIMEOUT);
    }

    public HttpExecutor(boolean useCookie) {
        this(useCookie,USE_PROXY_DEF, DEFAULT_ENCODING, DEFAULT_TIMEOUT);
    }

    public HttpExecutor(boolean useCookie, boolean userProxy) {
        this(useCookie, userProxy, DEFAULT_ENCODING, DEFAULT_TIMEOUT);
    }
    public HttpExecutor(String encoding) {
        this(true,USE_PROXY_DEF, encoding, DEFAULT_TIMEOUT);
    }

    public HttpExecutor(int timeout) {
        this(true,USE_PROXY_DEF, DEFAULT_ENCODING, timeout);
    }

    public HttpExecutor(boolean useCookie, int timeout) {
        this(useCookie,USE_PROXY_DEF, DEFAULT_ENCODING, timeout);
    }

    public HttpExecutor(boolean useCookie, String encoding) {
        this(useCookie, USE_PROXY_DEF, encoding, DEFAULT_TIMEOUT);
    }

    public HttpExecutor(String encoding, int timeout) {
        this(true,USE_PROXY_DEF, encoding, timeout);
    }

    public HttpExecutor(boolean useCookie, boolean useProxy, String encoding, int timeout) {

        this.useCookie = useCookie;
        //this.useProxy = useProxy;
        this.timeout = timeout;
        this.encoding = encoding;

        httpContext = HttpClientContext.create();

        RequestConfig.Builder requestBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout);
        defaultRequestConfig = requestBuilder.build();

        ConnectionConfig.Builder connBuilder = ConnectionConfig.custom()
                .setCharset(Charset.forName(encoding));
        ConnectionConfig connCfg = connBuilder.build();

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //trustStore.load(new FileInputStream("C:/Java/jdk1.7.0/jre/lib/security/cacerts"), "changeit".toCharArray());
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, new AnyTrustStrategy()).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();

        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        connManager.setDefaultConnectionConfig(connCfg);
        //connManager.setDefaultSocketConfig(socketConfig);
        //构建客户端
        HttpClientBuilder clientBuilder =  HttpClientBuilder.create().setConnectionManager(connManager);

        if (useCookie) {
            CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
                public CookieSpec create(HttpContext context) {
                    return new BrowserCompatSpec() {
                        @Override
                        public void validate(Cookie cookie, CookieOrigin origin)
                                throws MalformedCookieException {
                            CookieStore cs = httpContext.getCookieStore();
                            cs.addCookie(cookie);
                            //logger.debug("update cookie, add cookie: {}", cookie);
                        }
                    };
                }
            };
            Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
                    .register(CookieSpecs.BEST_MATCH,
                            new BestMatchSpecFactory())
                    .register(CookieSpecs.BROWSER_COMPATIBILITY,
                            new BrowserCompatSpecFactory())
                    .register("customCookiePolicy", easySpecProvider)
                    .build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec("customCookiePolicy")
                    .build();

            clientBuilder.setDefaultCookieSpecRegistry(r)
                    .setDefaultRequestConfig(requestConfig);
        }else{
            clientBuilder.disableCookieManagement();
        }

        if(useProxy){
            String proxyHost = null;
            Integer proxyPort = null;

            //到配置文件中查找代理信息
            String proxyInfo = PropertyUtil.getString("ProxyInfo");
            if(StringUtils.isNotBlank(proxyInfo) && proxyInfo.contains(":")){
                String[] infoArray = proxyInfo.split(":");
                if(infoArray.length == 2){
                    proxyHost = infoArray[0];
                    proxyPort = StringUtils.str2Int(infoArray[1], 0);
                }
            }
            if(proxyHost != null && proxyPort != null){
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
                clientBuilder.setRoutePlanner(routePlanner);
            }else{
                logger.error("Can not get the proxy info from configure file. Please check! System will skip the proxy temporarily.proxyInfo:{}",proxyInfo);
            }
        }

        httpClient = clientBuilder.build();
    }

    public Response executeGet(String uri) {
        return executeGet(uri, null, null);
    }

    public Response executeGet(String uri, RequestConfig requestConfig) {
        return executeGet(uri, requestConfig, null);
    }

    public Response executeGet(String uri, List<NameValuePair> headers) {
        return executeGet(uri, null, headers);
    }

    public Response executeGet(String uri, RequestConfig requestConfig, List<NameValuePair> headers) {
        HttpGet httpGet = new HttpGet(uri);
        return execute(httpGet, requestConfig, headers);
    }

    public Response executeFormPost(String uri, List<NameValuePair> data) {
        return executeFormPost(uri, data, null, null);
    }

    public Response executeFormPost(String uri, List<NameValuePair> data, RequestConfig requestConfig) {
        return executeFormPost(uri, data, requestConfig, null);
    }

    public Response executeFormPost(String uri, List<NameValuePair> data, List<NameValuePair> headers) {
        return executeFormPost(uri, data, null, headers);
    }

    public Response executeFormPost(String uri, List<NameValuePair> data, RequestConfig requestConfig, List<NameValuePair> headers) {
        HttpPost httpPost = new HttpPost(uri);
        HttpEntity entity = createFormEntity(data);
        httpPost.setEntity(entity);

        return execute(httpPost, requestConfig, headers);
    }
    
    public String getFormPostData(List<NameValuePair> data) {
    	HttpEntity entity = createFormEntity(data);
    	StringBuffer out = new StringBuffer();
		try {
			InputStream in = entity.getContent();
			byte[] b = new byte[1024];
	    	for(int n; (n=in.read(b))!=-1;){
	    		out.append(new String(b,0,n));
	    	}
	    	return out.toString();
		} catch (IllegalStateException | IOException e) {
		}
    	return null;
    }

    public Response executeStringPost(String uri, String data) {
        return executeStringPost(uri, data, null, null, null);
    }

    public Response executeStringPost(String uri, String data, String mimeType) {
        return executeStringPost(uri, data, mimeType, null, null);
    }

    public Response executeStringPost(String uri, String data, String mimeType, List<NameValuePair> headers) {
        return executeStringPost(uri, data, mimeType, null, headers);
    }

    public Response executeStringPost(String uri, String data, String mimeType, RequestConfig requestConfig) {
        return executeStringPost(uri, data, mimeType, requestConfig, null);
    }

    public Response executeStringPost(String uri, String data, String mimeType, RequestConfig requestConfig, List<NameValuePair> headers) {
        if (StringUtils.isNullOrWhiteSpace(mimeType))
            mimeType = ContentType.APPLICATION_JSON.getMimeType();

        HttpPost httpPost = new HttpPost(uri);
        HttpEntity entity = createStringEntity(data, mimeType);
        httpPost.setEntity(entity);

        return execute(httpPost, requestConfig, headers);
    }

    private HttpEntity createFormEntity(List<NameValuePair> params) {
        try {
            return new UrlEncodedFormEntity(params, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity createStringEntity(String content, String mimeType) {
        ContentType contentType = ContentType.create(mimeType, Charset.forName(encoding));
        return new StringEntity(content, contentType);
    }

    private void setHttpRequestHeaders(HttpRequestBase request, List<NameValuePair> headers) {
        if (headers != null) {
            for (NameValuePair nv : headers) {
                request.setHeader(nv.getName(), nv.getValue());
                if (nv.getName().equalsIgnoreCase("Cookie") && this.getCookieStore() == null){
                    CookieStore cs = new BasicCookieStore();
                    String value = StringUtils.safeString(nv.getValue());
                    String[] cks = value.split("\\;");

                    for (String ck : cks) {
                        String[] c = ck.split("\\=", 2);
                        if (c.length == 2) {
                            Cookie cookie = new BasicClientCookie(c[0].trim(), c[1].trim());
                            cs.addCookie(cookie);
                        }
                    }
                    httpContext.setCookieStore(cs);
                }
            }
        }
    }

    /**
     * 针对模拟浏览器设置部分常用headers
     * @param headers 已有的header对象
     */
    public static void addHeadersForBrowserSimulation(List<NameValuePair> headers){
        if(headers != null ){
            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            headerMap.put("accept-encoding", "gzip, deflate, sdch");
            headerMap.put("accept-language", "en,zh;q=0.8,zh-CN;q=0.6,en-US;q=0.4,ja;q=0.2");
            headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
            if(headers.size() >0){
                for (NameValuePair nv : headers) {
                    if(nv !=null ){
                        for (String key : headerMap.keySet()) {
                            if(key.equalsIgnoreCase(nv.getName())){
                                headerMap.put(key, null);
                            }
                        }
                    }
                }
            }

            for (String key : headerMap.keySet()) {
                if(headerMap.get(key) != null){
                    headers.add(new BasicNameValuePair(key, headerMap.get(key)));
                }
            }
        }
    }


    private Response execute(HttpRequestBase request, RequestConfig requestConfig, List<NameValuePair> headers) {
        HttpEntity respEntity = null;
        Response response = new Response(encoding);

        if (requestConfig != null)
            request.setConfig(requestConfig);
        if (headers != null)
            setHttpRequestHeaders(request, headers);

        try {
//            //TODO test
//            if(this.useCookie) {
//                if (request.getHeaders("cookie") == null && httpContext.getCookieStore() != null && httpContext.getCookieStore().getCookies() != null && httpContext.getCookieStore().getCookies().size() > 0){
//                    StringBuffer cookieSB = new StringBuffer();
//                    for (Cookie cookie : httpContext.getCookieStore().getCookies()) {
//                        cookieSB.append(cookie.getName() + "=" + cookie.getValue());
//                        cookieSB.append("; ");
//                    }
//                    request.setHeader("cookie", cookieSB.toString());
//                }
//            }
            HttpResponse resp = httpClient.execute(request, httpContext);
            respEntity = resp.getEntity();
            int code = resp.getStatusLine().getStatusCode();

            response.setStatusCode(code);
            response.setHeaders(resp.getAllHeaders());

            if (code == HttpStatus.SC_OK) {
                InputStream is = respEntity.getContent();
                byte[] content = HttpHelper.readBytes(is);
                Header enc = resp.getEntity().getContentEncoding();

                if (enc != null && enc.getValue().equals("gzip"))
                    content = HttpHelper.unGZip(content);

                response.setResultCode(Response.Success);
                response.setContent(content);
            } else if (code == HttpStatus.SC_MOVED_PERMANENTLY || code == HttpStatus.SC_MOVED_TEMPORARILY) {
                response.setResultCode(Response.Redirect);
                response.setContent(resp.getFirstHeader("Location").getValue().getBytes());
            } else {
                response.setResultCode(Response.ServerError);
                response.setErrorInfo(resp.getStatusLine().getReasonPhrase());
            }
        } catch (ConnectionPoolTimeoutException pte) {
            response.setResultCode(Response.ConnectionPoolTimeoutException);
            response.setErrorInfo(pte.getMessage());
        } catch (ConnectTimeoutException cte) {
            response.setResultCode(Response.ConnectTimeoutException);
            response.setErrorInfo(cte.getMessage());
        } catch (SocketTimeoutException ste) {
            response.setResultCode(Response.SocketTimeoutException);
            response.setErrorInfo(ste.getMessage());
        } catch (ClientProtocolException e) {
            response.setResultCode(Response.ClientProtocolException);
            response.setErrorInfo(e.getMessage());
        } catch (IOException e) {
            response.setResultCode(Response.IOException);
            response.setErrorInfo(e.getMessage());
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
            if (respEntity != null) {
                try {
                    EntityUtils.consume(respEntity);
                } catch (IOException e) {
                }
            }
        }

        return response;
    }

    public RequestConfig getDefaultRequestConfig() {
        return defaultRequestConfig;
    }

    public CookieStore getCookieStore() {
        return httpContext.getCookieStore();
    }

    /**
     * 将CookieStor的值作为String返回
     * @return cookie string
     */
    public String getCookieStr(){
        String cookieStr = "";
        CookieStore cs = this.getCookieStore();
        if(cs != null){
            List<Cookie> cookieList = cs.getCookies();
            if(cookieList != null && cookieList.size() >0){
                StringBuffer cookieBF = new StringBuffer();
                for(Cookie cookie : cookieList){
                    cookieBF.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
                }
                cookieStr = cookieBF.toString();
            }
        }
        return cookieStr;
    }

    /**
     * 根据name得到第一个找到的cookie的Str值。忽略大小写
     * @return
     */
    public Cookie getCookieByName(String name){
        if(name != null && name.trim().length() >0){
            CookieStore cs = this.getCookieStore();
            if(cs != null){
                List<Cookie> cookieList = cs.getCookies();
                if(cookieList != null && cookieList.size() >0){
                    for(Cookie cookieItem : cookieList){
                        if(cookieItem != null && name.equalsIgnoreCase(cookieItem.getName())){
                            return cookieItem;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void addCookie(Cookie cookie) {
        getCookieStore().addCookie(cookie);
    }

    public void addCookie(String name, String value) {
        getCookieStore().addCookie(new BasicClientCookie(name, value));
    }

    public void clearCookie() {
        getCookieStore().clear();
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public HttpClientContext getHttpContext() {
        return httpContext;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getEncoding() {
        return encoding;
    }

    public static class Response {
        public static final int Success = 0;
        public static final int Redirect = 1;
        public static final int ServerError = 2;
        public static final int ConnectionPoolTimeoutException = 3;
        public static final int ConnectTimeoutException = 4;
        public static final int SocketTimeoutException = 5;
        public static final int ClientProtocolException = 6;
        public static final int IOException = 7;

        public String encoding;
        public int statusCode;
        public int resultCode;
        public String errorInfo;
        public byte[] content;
        public Header[] headers;

        public Response() {

        }

        public Response(String encoding) {
            this.encoding = encoding;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getErrorInfo() {
            return errorInfo;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }

        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        public Header[] getHeaders() {
            return headers;
        }

        public Header getHeader(String name) {
            Header header = null;
            if (headers != null && name != null) {
                for (Header item : headers) {
                    if (item != null && name.equalsIgnoreCase(item.getName())) {
                        header = item;
                        break;
                    }
                }
            }
            return header;
        }

        public List<Header> getHeaders(String name) {
            List<Header> headerList = new ArrayList<Header>();
            if (headers != null && name != null) {
                for (Header item : headers) {
                    if (item != null && name.equalsIgnoreCase(item.getName())) {
                        headerList.add(item);
                    }
                }
            }
            return headerList;
        }

        public void setHeaders(Header[] headers) {
            this.headers = headers;
        }

        @Override
        public String toString() {
            if (content == null)
                return "";

            return new String(content, Charset.forName(encoding));
        }

        public boolean isSuccess() {
            return resultCode == Response.Success;
        }

        public boolean isNoError() {
            return resultCode == Response.Success ||
                    resultCode == Response.Redirect;
        }

        public boolean isNoExecption() {
            return resultCode == Response.Success ||
                    resultCode == Response.Redirect ||
                    resultCode == Response.ServerError;
        }
    }

    public void close() {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
    }

}

class AnyTrustStrategy implements TrustStrategy {
    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return true;
    }

}
