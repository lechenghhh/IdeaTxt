package TxtTool.Window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import TxtTool.Menu.SettingConfig;
import TxtTool.Utils.RandomAccessFileUtils.TxtReader;
import TxtTool.Utils.Utils;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey.Chursin
 * Date: Aug 25, 2010
 * Time: 2:09:00 PM
 */
public class TxtToolWindow implements ToolWindowFactory {

    private JPanel container;
    private JButton btnGo;
    private JButton btnLog;
    private JTextField fieldPage;
    private JLabel lableText;

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        // 将显示面板添加到显示区
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(getContent(), "", false);
        toolWindow.getContentManager().addContent(content);

        //初始化
        TxtReader.instance();
        fieldPage.setText(SettingConfig.instance().getPageNum() + "");
        lableText.setText("Hello World !");

        //设置按钮监听
        fieldPage.addActionListener(e -> fieldPage.setText(fieldPage.getText().replaceAll("^[0-9]", "")));//限定只能输入数字
        btnLog.addActionListener(e -> {
            Utils.showLog(lableText);
            int p = SettingConfig.instance().getPageNum();
            TxtReader.instance().toPage(p--);
            SettingConfig.instance().setPageNum(p);
        });//显示伪装
        btnGo.addActionListener(e -> {
            if (fieldPage.getText().equals("")) {//如果框内为空则为下一页
                int p = SettingConfig.instance().getPageNum();
                lableText.setText(TxtReader.instance().toPage(p++));
                SettingConfig.instance().setPageNum(p);
            } else {//如果已经填了值则跳转 并置空输入框
                int p = Integer.parseInt(fieldPage.getText());
                lableText.setText(TxtReader.instance().toPage(p++));
                SettingConfig.instance().setPageNum(p);
                fieldPage.setText("");
            }
        });
    }

    public JPanel getContent() {
        return container;
    }


    //显示提示
    public void showTips(String s) {
        lableText.setText(s);
    }

}