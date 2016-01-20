package com.web.shop.tag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class WelcomeTag extends TagSupport {
    private String locale;
    private static final String EN = "en";
    private static final String EN_WELCOME = "Welcome, ";
    private static final String RU_WELCOME = "Добро пожаловать, ";

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String to;
            if (EN.equalsIgnoreCase(locale) || locale.isEmpty()) {
                to = EN_WELCOME;
            } else {
                to = RU_WELCOME;
            }
            pageContext.getOut().write(to);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
