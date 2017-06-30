package com.uib.common.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 分页处理
 * @author kevin
 *
 */
public class PageTag extends TagSupport{
	
	private static Log logger = LogFactory.getLog(PageTag.class);
	
	private String formName;  
    
    private String curPage;  
      
    private String showPages;  
      
    private String totalPages;  
      
    private String PREVIOUS_PAGE = "上一页";  
  
    private String NEXT_PAGE = "下一页 ";  
      
    public String getHref(int number) {  
        return "Javascript:ToPage(" + number + ");";  
    }  
  
    public String goHref(int number) {  
        return " <a href=\"" + getHref(number) + "\" class=\"pagebox\">" + number + "</a>";  
    }  
      
    public int doEndTag() throws JspException {  
          
          
        int showPages = Integer.parseInt(this.showPages);  
        int curpage = Integer.parseInt(this.curPage);  
        int totalPages = Integer.parseInt(this.totalPages);  
          
        StringBuffer strBuf = new StringBuffer(512);  
        // 总页数  
        int pagecount = totalPages;  
        // 初始化值  
        if (curpage == 0) {  
            curpage = 1;  
        } else {  
            if (curpage <= 0) {  
                curpage = 1;  
            }  
            if (curpage > pagecount) {  
                curpage = pagecount;  
            }  
        }  
          
        strBuf.append("<style type='text/css'>");  
        strBuf.append(".pagebox{margin-left:2px;padding:3px 5px 3px 5px; border:1px solid #fff; background-color:#ebebeb;color:#FFFFFF; font-size:12px;}");  
        strBuf.append(".cpagebox{margin-left:2px;padding:3px 5px 3px 5px; border:1px gray; background-color:#ebebeb; color:red; font-size:12px;}");  
        strBuf.append(".vpagebox{margin-left:2px;padding:3px 5px 3px 5px; background-color:#FFFFFF; color:#000000;font-size:12px;}");  
        strBuf.append("</style>");  
          
        strBuf.append("<script language='JavaScript' type='text/JavaScript'>");  
        strBuf.append("function ToPage(p) { \n");  
        strBuf.append(" window.document." + formName + ".pageNo.value=p;\n");  
        strBuf.append(" window.document." + formName + ".submit();\n");  
        strBuf.append("}</script>");  
          
        if (curpage > 1) {  
            strBuf.append("<a href=\"" + getHref(curpage - 1) + "\" class=\"pagebox\" >" + PREVIOUS_PAGE + "</a>");  
        }  
  
        // 分页  
        if (pagecount <= showPages + 2) {  
            for (int i = 1; i <= pagecount; i++) {  
                if (i == curpage) {  
                    strBuf.append("<font class=\"cpagebox\">" + i + "</font>");  
                } else {  
                    strBuf.append(goHref(i));  
                }  
            }  
        } else {  
            if (curpage < showPages) {   
                for (int i = 1; i <= showPages; i++) {  
                    if (i == curpage) {  
                        strBuf.append("<font class=\"cpagebox\">" + i + "</font>");  
                    } else {  
                        strBuf.append(goHref(i));  
                    }  
                }  
                strBuf.append("<font class=\"vpagebox\">...</font>");  
                strBuf.append(goHref(pagecount));  
            } else if (curpage > pagecount - showPages + 1) { // 右边  
                strBuf.append(goHref(1));  
                strBuf.append("<font class=\"vpagebox\">...</font>");  
                for (int i = pagecount - showPages + 1; i <= pagecount; i++) {  
                    if (i == curpage) {  
                        strBuf.append("<font class=\"cpagebox\">" + i  
                                + "</font>");  
                    } else {  
                        strBuf.append(goHref(i));  
                    }  
                }  
            } else { // 中间  
                strBuf.append(goHref(1));  
                //strBuf.append(goHref(2));  
                strBuf.append("<font class=\"vpagebox\">...</font>");  
                int offset = (showPages - 2) / 2;  
                for (int i = curpage - offset; i <= curpage + offset; i++) {  
                    if (i == curpage) {  
                        strBuf.append("<font class=\"cpagebox\">" + i + "</font>");  
                    } else {  
                        strBuf.append(goHref(i));  
                    }  
                }  
                strBuf.append("<font class=\"vpagebox\">...</font>");  
                strBuf.append(goHref(pagecount));  
            }  
        }  
  
        // 显示下-页  
        if (curpage != pagecount) {  
            // 加上链接 curpage+1  
            strBuf.append("<a href=\"" + getHref(curpage + 1) + "\" class=\"pagebox\" >" + NEXT_PAGE + "</a>");  
        }  
          
        strBuf.append("<input name='pageNo' type='hidden' size='3' length='3' />");  
  
        try {  
            pageContext.getOut().println(strBuf.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
            logger.debug(e.getMessage());  
        }  
          
        return EVAL_PAGE;  
    }  
  
    public String getFormName() {  
        return formName;  
    }  
  
    public void setFormName(String formName) {  
        this.formName = formName;  
    }  
  
    public String getCurPage() {  
        return curPage;  
    }  
  
    public void setCurPage(String curPage) {  
        this.curPage = curPage;  
    }  
  
    public String getShowPages() {  
        return showPages;  
    }  
  
    public void setShowPages(String showPages) {  
        this.showPages = showPages;  
    }  
  
    public String getTotalPages() {  
        return totalPages;  
    }  
  
    public void setTotalPages(String totalPages) {  
        this.totalPages = totalPages;  
    }  
}
