package TxtTool.Window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey.Chursin
 * Date: Aug 25, 2010
 * Time: 2:09:00 PM
 */
public class TxtToolFactory implements ToolWindowFactory {

    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        TxtToolWindow txtToolWindow = new TxtToolWindow(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(txtToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}