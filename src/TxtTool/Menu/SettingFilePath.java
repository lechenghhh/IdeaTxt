package TxtTool.Menu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.File;

/*测试程序*/
public class SettingFilePath extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getData(PlatformDataKeys.PROJECT);

        String filePath = Messages.showInputDialog(
                project,
                "当前路径:" + SettingConfig.instance().getFilePath(),
                "请设置文件路径(重启后生效)",
                Messages.getQuestionIcon());
        if (!new File(filePath).exists()) {
            Messages.showMessageDialog(project,
                    "文件不存在!",
                    "提示:",
                    Messages.getInformationIcon());
            return;
        }
        SettingConfig.instance().setFilePath(filePath);
    }
}