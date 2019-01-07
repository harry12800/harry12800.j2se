package cn.harry12800.j2se.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class ProgressRequestBody extends RequestBody {
	public static final int UPDATE = 0x01;
	private RequestBody requestBody;
	private ProgressListener mListener;
	private BufferedSink bufferedSink;

	public int x = 0;
	private byte[] bytes;

	public ProgressRequestBody(RequestBody body, ProgressListener listener) {
		requestBody = body;
		mListener = listener;
	}

	public ProgressRequestBody(RequestBody body, byte[] bytes, ProgressListener listener) {
		requestBody = body;
		mListener = listener;
		this.bytes = bytes;
		x = 1;
	}

	@Override
	public MediaType contentType() {

		return requestBody.contentType();

	}

	@Override
	public long contentLength() throws IOException {
		if (x == 0)
			return requestBody.contentLength();
		else {
			return bytes.length;
		}
	}

	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		if (x == 1) {
			Source source = Okio.source(new ByteArrayInputStream(bytes));
			Buffer buf = new Buffer();
			// long current = 0;
			for (long readCount; (readCount = source.read(buf, 2048)) != -1;) {
				sink.write(buf, readCount);
				// current += readCount;
				// callback 进度通知
			}
			return;
		}
		if (bufferedSink == null) {
			bufferedSink = Okio.buffer(sink(sink));
		}
		// 写入
		requestBody.writeTo(bufferedSink);
		// 刷新
		bufferedSink.flush();
	}

	public static abstract class ProgressListener {
		public abstract void onProgress(long currentBytes, long contentLength, boolean done);

	}

	private Sink sink(BufferedSink sink) {

		return new ForwardingSink(sink) {
			long bytesWritten = 0L;
			long contentLength = 0L;

			public void write(Buffer source, long byteCount) throws IOException {
				super.write(source, byteCount);
				if (contentLength == 0) {
					contentLength = contentLength();
				}
				bytesWritten += byteCount;
				mListener.onProgress(bytesWritten, contentLength, bytesWritten == contentLength);
			}
		};
	}

}