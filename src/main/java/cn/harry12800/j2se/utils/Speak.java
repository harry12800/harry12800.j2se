package cn.harry12800.j2se.utils;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Speak {

	//设置APPID/AK/SK
	public static final String APP_ID = "10169742";
	public static final String API_KEY = "UnBK8kdRDhYHPN4TZQGKRnQB";
	public static final String SECRET_KEY = "4fce688dcdce77d531ae9556144ce3f2";

	public synchronized static void speak1(String words) throws Exception {
		// 初始化一个FaceClient
		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		// 调用API
		TtsResponse res = client.synthesis(words, "zh", 1, null);
		//        System.out.println(res.getErrorCode());
		byte[] data = res.getData();
		// 保存data至文件，即为可播放的MP3文件
		//        FileUtils.byte2File(data, "D:/", "1.mp3");
		//		MP3Player mp3 = new MP3Player("D:/1.mp3");
		new MP3Player(data);
		//		mp3.play();
	}

	public synchronized static void speak(String words) throws Exception {

		ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
		Dispatch sapo = sap.getObject();
		try {

			// 音量 0-100  
			sap.setProperty("Volume", new Variant(100));
			// 语音朗读速度 -10 到 +10  
			sap.setProperty("Rate", new Variant(1));

			// 执行朗读  
			Dispatch.call(sapo, "Speak", new Variant(words));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sapo.safeRelease();
			sap.safeRelease();
		}
	}
}