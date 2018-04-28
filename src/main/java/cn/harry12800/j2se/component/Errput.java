//package cn.harry12800.j2se.component;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
//public class Errput extends OutputStream{
//
//	int s  =0;
//	int i = 0;
//	byte[] vyf = new byte[2048];
//	@Override
//	public void write(int arg0) throws IOException {
//		if(s==13&&arg0==10){
//			String t = new String(vyf,"utf-8");
//			i = 0;
//			NotifyWindow.error(t);
//		}
//		else{
//			vyf[i++] = (byte) arg0;
//		}
//		
//		s = arg0;
//	}
//
//}
