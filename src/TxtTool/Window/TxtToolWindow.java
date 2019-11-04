package TxtTool.Window;


import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

import java.io.File;

import TxtTool.Utils.RandomAccessFileUtils.Reader;
import TxtTool.Menu.SettingConfig;

import com.intellij.openapi.project.Project;

public class TxtToolWindow {

    private Reader reader = new Reader();

    private JPanel container;
    private JButton btnGo;
    private JButton btnLog;
    private JTextField fieldPage;
    private JLabel lableText;

    private int page = 1;

    public JPanel getContent() {
        return container;
    }

    public TxtToolWindow(Project project, ToolWindow toolWindow) {
        //初始化
        fieldPage.setText(SettingConfig.instance().getPageNum() + "");
        File txtFile = new File(SettingConfig.instance().getFilePath());
        if (!txtFile.exists()) {
            showTips(toolWindow, "txt not found !");
            return;
        }
        lableText.setText(reader.init(SettingConfig.instance().getFilePath()));

        //设置按钮监听
        fieldPage.addActionListener(e -> fieldPage.setText(fieldPage.getText().replaceAll("^[0-9]", "")));//限定只能输入数字
        btnLog.addActionListener(e -> showLog(toolWindow));//显示伪装
        btnGo.addActionListener(e -> {
            if (fieldPage.getText().equals("")) {//如果框内为空则为下一页
                SettingConfig.instance().setPageNum(page);
                lableText.setText(reader.toPage(page++));
            } else {//如果已经填了值则跳转 并置空输入框
                page = Integer.parseInt(fieldPage.getText());
                SettingConfig.instance().setPageNum(page);
                lableText.setText(reader.toPage(page++));
                fieldPage.setText("");
            }
        });
    }

    //显示提示
    public void showTips(ToolWindow toolWindow, String s) {
        lableText.setText(s);
    }

    //显示Log信息
    public void showLog(ToolWindow toolWindow) {
        switch ((int) (Math.random() * 4)) {
            case 0:
                lableText.setText("run Starting program: throw_exception Catchpoint 1 (exception thrown), 0x00007ffff7b8f910 in __cxa_throw () from /usr/lib/libstdc++.so.6");
                break;
            case 1:
                lableText.setText("javax.servlet.ServletException: Error creating bean with name 'sessionFactory ' defined in class path resource [applicationContext.xml]: Invocation of init method failed; nested exception is java.lang.NoClassDefFoundError org.apache.struts.action.RequestProcessor.processException(RequestProcessor.java:523) org.apache.struts.action.RequestProcessor.processActionPerform(RequestProcessor.java:421) ");
                break;
            case 2:
                lableText.setText("11-01 14:34:34.438 3823-3903/? E/Unity: java.net.ConnectException: failed to connect to cdp.cloud.unity3d.com/32.123.119.74 (port 3306): connect failed: ETIMEDOUT (Connection timed out) (Filename:  Line: 390)");
                break;
            case 3:
                lableText.setText("Uncaught TypeError: Cannot read property 'isArray' of undefined at cached.php?t=1421837618&f=/js/ext/typeahead.min.js:1 at cached.php?t=1421837618&f=/js/ext/typeahead.min.js:1");
                break;
            case 4:
                lableText.setText(">>> assert 1 != 1 Traceback (most recent call last): File \"<pyshell#70>\", line 1, in <module> assert 1 != 1 AssertionError");
                break;
            default:
                lableText.setText("A typical use for this exception, is in conjunction with the is_callable() function.");
        }
    }

}