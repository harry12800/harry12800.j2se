package cn.harry12800.j2se.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.harry12800.j2se.utils.JsonUtils;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

	public static abstract class HttpBaseCallBack<T> {
		public Type mType;
		static Type getSuperClassTypeParameter(Class<?> subClass) {
			Type superClass = subClass.getGenericSuperclass();
			if (superClass instanceof Class) {
				throw new RuntimeException("Misssing Type parameter.");
			}
			ParameterizedType parameterizedType = (ParameterizedType) superClass;
			return parameterizedType.getActualTypeArguments()[0];
		}

		public HttpBaseCallBack() {
			mType = getSuperClassTypeParameter(getClass());
		}
	}

	public static OkHttpClient client = new OkHttpClient();

	static {
		try {
			client = initClientBuilder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
					.readTimeout(30, TimeUnit.SECONDS).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static <T> T get(String url, Class<T> clazz) throws IOException {
		return get(url, null, null, clazz);
	}

	public static String get(String url) throws IOException {
		return get(url, null, null);
	}

	public static byte[] getBytes(String url, Map<String, String> headers, Map<String, String> params)
			throws IOException {
		Response response = _get(url, headers, params);
		if (response != null) {
			return response.body().bytes();
		} else {
			throw new IOException("Get请求失败:" + url);
		}
	}

	public static String get(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
		Response response = _get(url, headers, params);
		if (response != null) {
			return response.body().string();
		} else {
			throw new IOException("Get请求失败:" + url);
		}
	}

	public static <T> T get(String url, Map<String, String> headers, Map<String, String> params,
			 Class<T> clazz) throws IOException {
		if (params != null && params.size() > 0) {
			StringBuffer buffer = new StringBuffer(url);
			buffer.append("?");
			for (String key : params.keySet()) {
				buffer.append(key + "=");
				buffer.append(params.get(key) + "&");
			}
			url = buffer.toString();
		}

		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}
		Request request = reqBuilder.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return  JsonUtils.string2Json(response.body().string(), clazz);
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

 
	private static Response _get(String url, Map<String, String> headers, Map<String, String> params)
			throws IOException {
		if (params != null && params.size() > 0) {
			StringBuffer buffer = new StringBuffer(url);
			buffer.append("?");
			for (String key : params.keySet()) {
				buffer.append(key + "=");
				buffer.append(params.get(key) + "&");
			}
			url = buffer.toString();
		}

		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}
		Request request = reqBuilder.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response;
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	public static String post(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
		FormBody.Builder builder = new FormBody.Builder();
		for (String key : params.keySet()) {
			builder.add(key, params.get(key));
		}
		RequestBody requestBodyPost = builder.build();

		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}
		Request requestPost = reqBuilder.post(requestBodyPost).build();
		Response response = client.newCall(requestPost).execute();
		return response.body().string();
	}

	public static String postJson(String url, Map<String, String> headers, String json) throws IOException {
		RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}
		Request requestPost = reqBuilder.post(body).build();
		Response response = client.newCall(requestPost).execute();
		return response.body().string();
	}
	@SuppressWarnings("unchecked")
	public static <T> T postJson(String url, Map<String, String> headers, Object json,Class<T> returnClazz) throws IOException {
		RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtils.object2String(json));
		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}
		Request requestPost = reqBuilder.post(body).build();
		Response response = client.newCall(requestPost).execute();
		if(returnClazz == String.class) {
			return (T) response.body().string();
		}
		return JsonUtils.string2Json(response.body().string(), returnClazz);
	}
	public static byte[] download(String url) throws IOException {
		return download(url, null, null, null);
	}

	public static byte[] download(String url, Map<String, String> headers, Map<String, String> params,
			ProgressListener listener) throws IOException {
		if (params != null && params.size() > 0) {
			StringBuffer buffer = new StringBuffer(url);
			buffer.append("?");
			for (String key : params.keySet()) {
				buffer.append(key + "=");
				buffer.append(params.get(key) + "&");
			}
			url = buffer.toString();
		}

		Request.Builder reqBuilder = new Request.Builder().url(url);
		if (headers != null && headers.size() > 0) {
			for (String key : headers.keySet()) {
				reqBuilder.addHeader(key, headers.get(key));
			}
		}

		Request request = reqBuilder.build();
		byte[] data = null;
		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				InputStream inputStream = response.body().byteStream();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				// byte[] buff = new byte[2048000];
				byte[] buff = new byte[2048];

				int len;
				long total = response.body().contentLength();
				// total = response.body().bytes().length;
				// long total = inputStream.available();
				long sum = 0L;
				while ((len = inputStream.read(buff)) > -1) {
					outputStream.write(buff, 0, len);
					sum += len;

					if (listener != null) {
						int progress = (int) (sum * 1.0f / total * 100);
						listener.onProgress(progress);
					}

				}

				data = outputStream.toByteArray();

				inputStream.close();
				outputStream.close();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return data;
	}

	public static OkHttpClient.Builder initClientBuilder() throws KeyManagementException, NoSuchAlgorithmException {
		X509TrustManager xtm = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				X509Certificate[] x509Certificates = new X509Certificate[0];
				return x509Certificates;
			}

		};

		SSLContext sslContext = null;
		sslContext = SSLContext.getInstance("SSL");

		sslContext.init(null, new TrustManager[] { xtm }, new SecureRandom());

		HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				// .addInterceptor(interceptor)
				.sslSocketFactory(sslContext.getSocketFactory(), xtm).hostnameVerifier(DO_NOT_VERIFY)
				.connectTimeout(10000, TimeUnit.MILLISECONDS);

		return builder;
	}

	public interface ProgressListener {
		void onProgress(int process);
	}

	public static boolean upload(String url, String type, byte[] part) throws IOException {
		Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse(type), part)).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return true;
		}

		return false;
	}

	/**
	 * 上传文件到服务器，服务器已经指定了文件路径。
	 * 
	 * @param url
	 *            http路径
	 * @param path
	 *            本地文件路径
	 * @return
	 * @throws IOException
	 */
	public static boolean uploadFile(String url, String path) throws IOException {
		File file = new File(path);
		MediaType type = MediaType.parse("application/octet-stream");
		RequestBody fileBody = RequestBody.create(type, file);
		// 三种：混合参数和文件请求
		RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
				// 一样的效果
				.addPart(Headers.of("Content-Disposition",
						"form-data; name=\"file\"; filename=\"" + file.getName() + "\""), fileBody)
				// 一样的效果
				/*
				 * .addFormDataPart("id",currentPlan.getPlanId()+"")
				 * .addFormDataPart("name",currentPlan.getName())
				 * .addFormDataPart("volume",currentPlan.getVolume())
				 * .addFormDataPart("type",currentPlan.getType()+"")
				 * .addFormDataPart("mode",currentPlan.getMode()+"")
				 * .addFormDataPart("params","plans.xml",fileBody)
				 */
				.build();

		Request request = new Request.Builder().url(url).addHeader("User-Agent", "android")
				.header("Content-Type", "text/html; charset=utf-8;").post(multipartBody)// 传参数、文件或者混合，改一下就行请求体就行
				.build();
		Response response = client.newCall(request).execute();

		System.out.println(response.body().string());
		if (response.isSuccessful()) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String url = "http://127.0.0.1/v1/user/uploadAvatar";
		try {
			HttpUtil.uploadFile(url, "C:/Users/harry12800/Desktop/2018-04.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
