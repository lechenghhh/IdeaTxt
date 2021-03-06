package TxtTool.Action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import TxtTool.Utils.RandomAccessFileUtils.TxtReader;

/*测试程序*/
public class WordNum extends AnAction {

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getData(PlatformDataKeys.PROJECT);

        String textNum = Messages.showInputDialog(
                project,
                "当前字数" + SettingConfig.instance().getTextNum() + ",请输入数字(推荐小于200,设置后页码将无法对应)",
                "请设置每页字数:",
                Messages.getQuestionIcon());
        SettingConfig.instance().setTextNum(Integer.parseInt(textNum));
        TxtReader.instance().init(SettingConfig.instance().getFilePath());

//        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(TOOL_WINDOW_ID);
//        JPanel container = (JPanel) toolWindow.getContentManager().getContent(0).getComponent();
//        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
//        Content content = contentFactory.createContent(container, "Control", false);
//        toolWindow.getContentManager().addContent(content);
    }
}