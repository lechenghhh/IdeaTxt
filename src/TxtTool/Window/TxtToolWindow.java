package TxtTool.Window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import TxtTool.Action.SettingConfig;
import TxtTool.Utils.RandomAccessFileUtils.TxtReader;
import TxtTool.Utils.Utils;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey.Chursin
 * Date: Aug 25, 2010
 * Time: 2:09:00 PM
 */
public class TxtToolWindow implements ToolWindowFactory {

    private static final int PADDING_SIZE = 128;

    private JPanel container;
    private JButton btnNext;
    private JButton btnLog;
    private JLabel lableText;

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        // 将显示面板添加到显示区
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(getContent(), "", false);
        toolWindow.getContentManager().addContent(content);

        //UI自适应宽度
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension lableSize = lableText.getPreferredSize();
        lableSize.width = screenSize.width - PADDING_SIZE;
        lableText.setPreferredSize(lableSize);

        //初始化
        TxtReader.instance();
        SettingConfig.instance().setPageNum(SettingConfig.instance().getPageNum() - 1);//因为next会下一页，所以先减少1
        lableText.setText(" Hello World ! ");

        //设置按钮监听
        btnLog.addActionListener(e -> {//显示伪装
            Utils.showLog(lableText);
            int p = SettingConfig.instance().getPageNum();
            if (p == 1) return;
            TxtReader.instance().toPage(p--);
            SettingConfig.instance().setPageNum(p);
        });
        btnNext.addActionListener(e -> {//下一页
            int p = SettingConfig.instance().getPageNum();
            lableText.setText(" " + TxtReader.instance().toPage(p++));
            SettingConfig.instance().setPageNum(p);
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