package cn.harry12800.j2se.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类
public class PlaySounds extends Thread {

	private String filename;

	public PlaySounds(String wavfile) {
		filename =   wavfile;
	}
	public void run() {
		 //注意加载资源文件的写法  /表示从根目录开始  否则就表示从当前类所在的路径下开始  
        InputStream is=this.getClass().getResourceAsStream(filename);    
		AudioInputStream audioInputStream = null;
		BufferedInputStream bufferedInputStream = new BufferedInputStream(is );
		try {
			audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		auline.start();
		int nBytesRead = 0;
		// 这是缓冲
		byte[] abData = new byte[512];
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}
	}
	public static void play(String str) {
		new PlaySounds(str).run();
	}
}
