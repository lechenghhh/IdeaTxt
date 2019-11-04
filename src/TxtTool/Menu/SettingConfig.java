package TxtTool.Menu;

import com.intellij.ide.util.PropertiesComponent;

public class SettingConfig {

    private static final String DEFAULT_FILE_PATH = "C:/Users/User/text.txt";
    private static final String FILE_PATH = "FILE_PATH";
    private static final String PAGE_NUM = "PAGE_NUM";
    private static final String TEXT_SIZE = "TEXT_SIZE";
    private static final String TEXT_NUM = "TEXT_NUM";

    private static SettingConfig settingConfig = new SettingConfig();
    private PropertiesComponent pc;

    private SettingConfig() {
        pc = PropertiesComponent.getInstance();
        System.out.println("FilePath=" + getFilePath());
        System.out.println("PageNum=" + getPageNum());
        System.out.println("TextNum=" + getTextNum());
        System.out.println("TextSize=" + getTextSize());
    }

    public static SettingConfig instance() {
        return settingConfig;
    }

    public int getTextSize() {
        return pc.getInt(TEXT_SIZE, 1);
    }

    public void setTextSize(int textSize) {
        pc.setValue(TEXT_SIZE, textSize, 14);
    }

    public int getTextNum() {
        return pc.getInt(TEXT_NUM, 80);
    }

    public void setTextNum(int textNum) {
        pc.setValue(TEXT_NUM, textNum, 80);
    }

    public int getPageNum() {
        return pc.getInt(PAGE_NUM, 1);
    }

    public void setPageNum(int pageNum) {
        pc.setValue(PAGE_NUM, pageNum, 1);
    }

    public String getFilePath() {
        return pc.getValue(FILE_PATH, DEFAULT_FILE_PATH);
    }

    public void setFilePath(String filePath) {
        pc.setValue(FILE_PATH, filePath);
    }

    public String getCachePath() {
        return getFilePath() + ".cache";
    }
}
