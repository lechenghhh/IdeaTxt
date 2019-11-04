package TxtTool.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

import javax.swing.JLabel;

import TxtTool.Menu.SettingConfig;
import TxtTool.Utils.RandomAccessFileUtils.Reader;

import static TxtTool.Action.ShowLog.TOOL_WINDOW_ID;

public class NextPage extends AnAction {
    public static final String TOOL_WINDOW_ID = "IdeaTxtWindow";

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = (Project) e.getData(PlatformDataKeys.PROJECT);

        Reader reader = new Reader();


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
                    int p = SettingConfig.instance().getPageNum() ;
                    jLabel.setText(reader.toPage(p++));
                    SettingConfig.instance().setPageNum(p);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
}
