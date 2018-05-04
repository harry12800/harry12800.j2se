package cn.harry12800.j2se.utils;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageHelper {
	public static void main(String[] args) {
		try {
			//BufferedImage image = ImageIO.read(new FileInputStream("d:\\1.jpg"));
			// 读取图标
			BufferedImage imageBiao = ImageIO.read(new FileInputStream("F:\\java\\workspace\\nytm\\src\\file\\image\\27803.jpg"));
			//Graphics2D g = image.createGraphics();
			// g.setColor(Color.YELLOW);
			// g.setFont(new Font("华文中宋", Font.LAYOUT_LEFT_TO_RIGHT, 48));
			// g.drawString("图像合成示例",100, image.getHeight() - 400);
			// 写入图标
			ImageFilter imgf = new MyFilter(255);
			FilteredImageSource fis = new FilteredImageSource(imageBiao.getSource(), imgf);

			Image im = Toolkit.getDefaultToolkit().createImage(fis);
			BufferedImage bi = new BufferedImage(im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			ImageIO.write(bi, "png", new File("d:\\1.png"));
			//			g.drawImage(im, 0, 0, imageBiao.getWidth(null),
			//					imageBiao.getHeight(null), null);
			//			g.dispose();
			//			FileOutputStream out = new FileOutputStream("f:\\图标文字合成3.gif");
			//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			//			encoder.encode(image);
			//			out.close();
			// 把以上原图和加上图标后的图像
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class MyFilter extends RGBImageFilter {// 抽象类RGBImageFilter是ImageFilter的子类，
	// 继承它实现图象ARGB的处理
	int alpha = 0;

	public MyFilter(int alpha) {// 构造器，用来接收需要过滤图象的尺寸，以及透明度
		this.canFilterIndexColorModel = true;
		// TransparentImageFilter类继承自RGBImageFilter，它的构造函数要求传入原始图象的宽度和高度。
		// 该类实现了filterRGB抽象函数
		// ，缺省的方式下，该函数将x，y所标识的象素的ARGB值传入，程序员按照一定的程序逻辑处理后返回该象素新的ARGB值
		this.alpha = alpha;
	}

	public int filterRGB(int x, int y, int rgb) {
		DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();
		// DirectColorModel类用来将ARGB值独立分解出来
		int red = dcm.getRed(rgb);
		int green = dcm.getGreen(rgb);
		int blue = dcm.getBlue(rgb);
		int alp = dcm.getAlpha(rgb);
		if (red == 255 && blue == 255 && green == 255) {// 如果像素为白色，则让它透明
			alpha = 0;
		} else {
			alpha = 255;
		}
		if (alp == 0) {//png和gif格式图片透明部分仍然透明
			alpha = 0;
		} else {
			alpha = 255;
		}
		return alpha << 24 | red << 16 | green << 8 | blue;// 进行标准ARGB输出以实现图象过滤
	}

	/*
	 * 根据尺寸图片居中裁剪
	 */
	public static void cutCenterImage(String src, String dest, int w, int h) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2, (reader.getHeight(imageIndex) - h) / 2, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));

	}

	/*
	 * 图片裁剪二分之一
	 */
	public static void cutHalfImage(String src, String dest) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		int width = reader.getWidth(imageIndex) / 2;
		int height = reader.getHeight(imageIndex) / 2;
		Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));
	}
	/*
	 * 图片裁剪通用接口
	 */

	public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = ImageHelper.class.getResourceAsStream(src);
		System.out.println("asd:" + in);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(x, y, w, h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));

	}

	/*
	 * 图片缩放
	 */
	public static void zoomImage(String src, String dest, int w, int h) throws Exception {
		double wr = 0, hr = 0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image Itemp = bufImg.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		wr = w * 1.0 / bufImg.getWidth();
		hr = h * 1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
