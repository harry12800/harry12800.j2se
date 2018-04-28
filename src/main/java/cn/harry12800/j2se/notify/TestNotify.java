package cn.harry12800.j2se.notify;

public class TestNotify implements Notify{

	int i=0;
	public TestNotify(int i) {
		 this.i = i;
	}
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "title"+i;
	}

	@Override
	public int getTimes() {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public String getContent() {
		// TODO Auto-generated method stub
		return "content"+i;
	}

	@Override
	public String getIcon() {
		// TODO Auto-generated method stub
		return "icon"+i;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
