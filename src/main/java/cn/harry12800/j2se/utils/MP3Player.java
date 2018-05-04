package cn.harry12800.j2se.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import cn.harry12800.tools.StringUtils;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player {
	private String filename;
	private Player player;

	public MP3Player(String filename) {
		//		Media Media = new Media("file:///"+new File(filename).toURI());
		//		MediaPlayer player = new MediaPlayer(Media  );
		//		player.setAutoPlay(true);
		this.filename = filename;
	}

	public MP3Player(byte[] data) {
		try {
			player = new Player(new ByteArrayInputStream(data));
			player.play();
			//			int position = player.getPosition();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (player != null)
			player.close();
	}

	public void play() {
		try {
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
			player = new Player(buffer);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Speak.speak1("阿萨德发阿萨发送地方");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
