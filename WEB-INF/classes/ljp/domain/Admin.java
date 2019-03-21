package ljp.domain;

import ljp.constant.Constant;

public class Admin {

	private String mid;
	private String mname;
	private String mpassword;
	
	/**
	 * id为1则是内置的超级管理员
	 * @return
	 */
	public boolean getSuper() {
		return Constant.SUPER.equals(mid) ? true : false;
	}
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	
	
}
