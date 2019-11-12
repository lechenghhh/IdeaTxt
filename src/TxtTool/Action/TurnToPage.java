package TxtTool.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

import javax.swing.JLabel;

import TxtTool.Utils.RandomAccessFileUtils.TxtReader;

public class TurnToPage extends AnAction {
    public static final String TOOL_WINDOW_ID = "IdeaTxt";

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = (Project) e.getData(PlatformDataKeys.PROJECT);
        TxtReader.instance();

        String textNum = Messages.showInputDialog(
                project,
                "请输入数字",
                "请输入需要跳转的页码:",
                Messages.getQuestionIcon());

        try {
            int p = Integer.parseInt(textNum);
            if (p < 1) p = 1;

            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(TOOL_WINDOW_ID);
            if (toolWindow != null) {
                // 无论当前状态为关闭/打开，进行强制打开ToolWindow 2017/3/21 16:21
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                try {            // ToolWindow未初始化时，可能为空 2017/4/4 18:20
                    JLabel jLabel = (JLabel) (toolWindow.getContentManager().getContent(0).getComponent().getComponent(0));
                    if (jLabel != null) {

                        jLabel.setText(" " + TxtReader.instance().toPage(p++));
                        SettingConfig.instance().setPageNum(p);

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        } catch (Exception x) {
            Messages.showMessageDialog(project,
                    "页码有误,请重试!",
                    "提示:",
                    Messages.getInformationIcon());
        }
    }
}
