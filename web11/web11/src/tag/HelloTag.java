package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * 1,继承SimpleTagSupport类，并override doTag方法。
 * 2,标签有哪些属性，标签类也有对应的属性，属性
 * 要一致(名称要一样，类型要匹配),属性还必须提供
 * 相应的set方法。
 */
public class HelloTag extends SimpleTagSupport{
	private String info;
	private int qty;
	public HelloTag(){
		System.out.println("HelloTag's constructor...");
	}
	public void setInfo(String info) {
		System.out.println("setInfo..." + info);
		this.info = info;
	}

	public void setQty(int qty) {
		System.out.println("setQty..." + qty);
		this.qty = qty;
	}

	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("doTag...");
		//获得pageContext,pageContext提供了获得其它所有
		//隐含对象的方法。
		PageContext ctx = (PageContext)getJspContext();
		JspWriter out = ctx.getOut();
		for(int i = 0 ;i < qty ;i ++){
			out.println(info + "<br/>");
		}
	}
}
