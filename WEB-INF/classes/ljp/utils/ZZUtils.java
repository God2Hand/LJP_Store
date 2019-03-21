package ljp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZZUtils {
	
	private ZZUtils(){}
	
	public static boolean check(String regex, String str) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);//通过模式对象得到匹配器对象，这个时候需要的是被匹配的字符串
		boolean b = m.matches();//调用匹配器对象的功能
		return b;
	}

}
