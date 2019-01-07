package cn.harry12800.j2se.utils;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.harry12800.j2se.utils.ProgressRequestBody.ProgressListener;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadUtil {
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

	public static OkHttpClient.Builder initClientBuilder() throws KeyManagementException, NoSuchAlgorithmException {
		X509TrustManager xtm = new X509TrustManager() {

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

			}

			public X509Certificate[] getAcceptedIssuers() {
				X509Certificate[] x509Certificates = new X509Certificate[0];
				return x509Certificates;
			}

		};

		SSLContext sslContext = null;
		sslContext = SSLContext.getInstance("SSL");

		sslContext.init(null, new TrustManager[] { xtm }, new SecureRandom());

		HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		OkHttpClient.Builder builder = new OkHttpClient.Builder().retryOnConnectionFailure(true)
				// .addInterceptor(interceptor)
				.sslSocketFactory(sslContext.getSocketFactory(), xtm).hostnameVerifier(DO_NOT_VERIFY)
				.connectTimeout(30000, TimeUnit.MILLISECONDS);

		return builder;
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
	public static String uploadFile(String url, String path, String remotePath, final ProgressListener listener)
			throws IOException {
		File file = new File(path);
		MediaType type = MediaType.parse("application/octet-stream");
		RequestBody fileBody = RequestBody.create(type, file);
		// 三种：混合参数和文件请求
		RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
				// 一样的效果
				.addPart(Headers.of("Content-Disposition",
						"form-data; name=\"file\"; filename=\"" + file.getName() + "\""), fileBody)
				// 一样的效果
				.addFormDataPart("path", remotePath)
				/*
				 * .addFormDataPart("name",currentPlan.getName())
				 * .addFormDataPart("volume",currentPlan.getVolume())
				 * .addFormDataPart("type",currentPlan.getType()+"")
				 * .addFormDataPart("mode",currentPlan.getMode()+"")
				 * .addFormDataPart("params","plans.xml",fileBody)
				 */
				.build();

		Request request = new Request.Builder().url(url).addHeader("User-Agent", "android")
				.header("Content-Type", "text/html; charset=utf-8;")
				.post(new ProgressRequestBody(multipartBody, listener))// 传参数、文件或者混合，改一下就行请求体就行
				.build();
		Response response = client.newCall(request).execute();
		try {
			String result = response.body().string();
			if (response.isSuccessful()) {
				return result;
			}
		} finally {
			response.close();
		}
		return "失败！";
	}

	public static String uploadBytes(String url, String content, String remotePath, ProgressListener listener)
			throws IOException {
		MediaType type = MediaType.parse("application/octet-stream");
		RequestBody fileBody = RequestBody.create(type, content);
		// 三种：混合参数和文件请求
		RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
				// 一样的效果
				.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"config.dbax\""),
						fileBody)
				// 一样的效果
				.addFormDataPart("path", remotePath).build();
		Request request = new Request.Builder().url(url).addHeader("User-Agent", "android")
				.header("Content-Type", "text/html; charset=utf-8;")
				.post(new ProgressRequestBody(multipartBody, listener))// 传参数、文件或者混合，改一下就行请求体就行
				.build();
		Response response = client.newCall(request).execute();
		try {
			String result = response.body().string();
			if (response.isSuccessful()) {
				return result;
			}
		} finally {
			response.close();
		}
		return "失败！";
	}

}