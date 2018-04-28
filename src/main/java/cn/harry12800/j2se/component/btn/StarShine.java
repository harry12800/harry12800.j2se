package cn.harry12800.j2se.component.btn;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
/** 
 * 五颜六色满天星的实现 
 * 
 * @author monitor 
 * Created on 2011-3-3, 22:47:03 
 */  
@SuppressWarnings("serial")
public class StarShine extends JComponent{  
    private List<Shape> stars=new LinkedList<Shape>();  
    private static Random random=new Random();  
    private static Color[][] colors={  
        {Color.WHITE, Color.BLACK},  
        {Color.WHITE, Color.BLUE},  
        {Color.ORANGE, Color.PINK},  
        {Color.ORANGE, Color.green}  
    };  
    private String menInfo="";  
    public StarShine(){  
        setBackground(Color.WHITE);  
        //每秒输出内存信息  
        new java.util.Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				//随机多边形  
                int centerX =random.nextInt(getWidth());  
                int centerY =random.nextInt(getHeight());  
                double innerSize = 1 + (25 * Math.random());  
                double outerSize = innerSize + 10 + (15 * Math.random());  
                int numPoints = (int)(8 * Math.random() + 5);  
                stars.add(getStar(centerX,centerY,innerSize,outerSize,numPoints));  
                //内存信息  
                long tm=Runtime.getRuntime().totalMemory();  
                long mm=Runtime.getRuntime().maxMemory();  
                long fm=Runtime.getRuntime().freeMemory();  
                long um=tm-fm;  
                menInfo=String.format("%d / %d MB  %d", um/(1024*1024),mm/(1024*1024),stars.size());  
                repaint();  				
			}
		}, 1000, 100);
//        new Timer(500, new ActionListener() {  
//            public void actionPerformed(ActionEvent evt) {  
//                //随机多边形  
//                int centerX =random.nextInt(getWidth());  
//                int centerY =random.nextInt(getHeight());  
//                double innerSize = 1 + (25 * Math.random());  
//                double outerSize = innerSize + 10 + (15 * Math.random());  
//                int numPoints = (int)(8 * Math.random() + 5);  
//                stars.add(getStar(centerX,centerY,innerSize,outerSize,numPoints));  
//                //内存信息  
//                long tm=Runtime.getRuntime().totalMemory();  
//                long mm=Runtime.getRuntime().maxMemory();  
//                long fm=Runtime.getRuntime().freeMemory();  
//                long um=tm-fm;  
//                menInfo=String.format("%d / %d MB  %d", um/(1024*1024),mm/(1024*1024),stars.size());  
//                repaint();  
//            }  
//        }).start();  
    }  
    @Override  
    protected void paintComponent(Graphics g) {  
        Graphics2D g2d = (Graphics2D)g;  
        //天空背景  
        GradientPaint background = new GradientPaint(0f, 0f, Color.GRAY.darker(),  
                0f, (float)getHeight(), Color.GRAY.brighter());  
        g2d.setPaint(background);  
        g2d.fillRect(0, 0, getWidth(), 4*getHeight()/5);  
        //地面背景  
        background = new GradientPaint(0f, (float)4*getHeight()/5,  
                Color.BLACK,  
                0f, (float)getHeight(), Color.GRAY.darker());  
        g2d.setPaint(background);  
        g2d.fillRect(0, 4*getHeight()/5, getWidth(), getHeight()/5);  
        //开启抗锯齿  
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                RenderingHints.VALUE_ANTIALIAS_ON);  
        //画所有的星星  
        for (Shape star : stars) {  
            Rectangle rect = star.getBounds();  
            Point2D center = new Point2D.Float(  
                    rect.x + (float)rect.width / 2.0f,  
                    rect.y + (float)rect.height / 2.0f);  
            float radius = (float)rect.width / 2.0f;  
            float[] dist = {0.1f, 0.9f};  
            //圆形辐射颜色渐变模式  
            RadialGradientPaint paint = new RadialGradientPaint(center, radius,  
                    dist, colors[random.nextInt(colors.length)]);  
            g2d.setPaint(paint);  
            g2d.fill(star);  
        }  
        g2d.drawString(menInfo,10, 10);  
    }  
    /** 
     * 获得一个随机边的多边形 
     * @param x 中心点X 
     * @param y 中心点Y 
     * @param innerRadius 内圆半径 
     * @param outerRadius 外圆半径 
     * @param pointsCount 角数 
     * @return 一个多边形 
     */  
    private static Shape getStar(double x, double y,  
            double innerRadius, double outerRadius,int pointsCount) {  
        GeneralPath path = new GeneralPath();  
        double outerAngleIncrement = 2 * Math.PI / pointsCount;  
        double outerAngle = 0.0;  
        double innerAngle = outerAngleIncrement / 2.0;  
        x += outerRadius;  
        y += outerRadius;  
        float x1 = (float) (Math.cos(outerAngle) * outerRadius + x);  
        float y1 = (float) (Math.sin(outerAngle) * outerRadius + y);  
        float x2 = (float) (Math.cos(innerAngle) * innerRadius + x);  
        float y2 = (float) (Math.sin(innerAngle) * innerRadius + y);  
        path.moveTo(x1, y1);  
        path.lineTo(x2, y2);  
        outerAngle += outerAngleIncrement;  
        innerAngle += outerAngleIncrement;  
        for (int i = 1; i < pointsCount; i++) {  
            x1 = (float) (Math.cos(outerAngle) * outerRadius + x);  
            y1 = (float) (Math.sin(outerAngle) * outerRadius + y);  
            path.lineTo(x1, y1);  
            x2 = (float) (Math.cos(innerAngle) * innerRadius + x);  
            y2 = (float) (Math.sin(innerAngle) * innerRadius + y);  
            path.lineTo(x2, y2);  
            outerAngle += outerAngleIncrement;  
            innerAngle += outerAngleIncrement;  
        }  
        path.closePath();  
        return path;  
    }  
    /** 创建界面 */  
    private static void createAndShowGUI() {  
        final JFrame f = new JFrame("Star Shine");  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.setSize(800, 500);  
        f.add(new StarShine());  
        f.setVisible(true);  
        f.setLocationRelativeTo(f.getOwner());  
    }  
    public static void main(String args[]) {  
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
                } catch (Exception ex) {  
                }  
                createAndShowGUI();  
            }  
        });  
    }  
}  