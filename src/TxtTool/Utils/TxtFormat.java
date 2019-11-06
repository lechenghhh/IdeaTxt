package TxtTool.Utils;/*
1. 输入文件夹，循环该文件夹下所有TXT文件，删除文件中的空白字符和空行。
2. 直接利用main函数参数来输入文件夹路径。
*/

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import TxtTool.Menu.SettingConfig;

public class TxtFormat {
//    private static String path = "C:/Users/User/text.txt";
//    private static String path2 = "C:/Users/User/text.txt.cache";

    public static void main(String[] args) {    //直接利用main函数参数输入文件夹路径
        Format();
    }

    public static void Format() {
        deleteCache();
        ClearFormat(SettingConfig.instance().getFilePath());
        AddLineFormat(SettingConfig.instance().getCachePath());
    }

    /**
     * 清除掉所有格式，变换为无换行符的文本
     */
    private static void ClearFormat(String url) {
        try {
            File file = new File(url);
            if (file.exists()) {    //判断是否是路径是否存在，是否是文件夹
//                Runtime.getRuntime().exec("notepad " + file.getAbsolutePath());//打开待处理文件,参数是字符串，是个命令
                String str = null;

                BufferedWriter writer = writeCache(file);

                InputStreamReader stream = new InputStreamReader(new FileInputStream(file), "UTF-8");    //读入字节流，并且设置编码为UTF-8
                BufferedReader reader = new BufferedReader(stream);    ////构造一个字符流的缓存器，存放在控制台输入的字节转换后成的字符

                String REGEX = "\\s+";    //空格、制表符正则表达式,\s匹配任何空白字符，包括空格、制表符、换页符等
                Pattern patt = Pattern.compile(REGEX);    //创建Pattern对象，处理正则表达式

                int pos = 0;
                while ((str = reader.readLine()) != null) {
                    Matcher mat = patt.matcher(str);    //先处理每一行的空白字符
                    str = mat.replaceAll("");

//                    System.out.println(pos++);
                    if (str == "") {    //如果不想保留换行符直接写入就好，不用多此一举
                        continue;
                    } else {
                        writer.write(str);    //如果想保留换行符，可以利用str+"\r\n" 来在末尾写入换行符
                    }
                }
                writer.close();
                reader.close();

                //打开修改后的文档
                //Runtime.getRuntime().exec("notepad " + newFile.getAbsolutePath());

                System.out.println("文件格式清除完成");
            } else {
                System.out.println("文件路径不存在或输入的不是文件夹路径");
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件。并且每隔一段增加一个换行符
     */
    private static String AddLineFormat(String pathfile) {
        File file = new File(pathfile);
        Reader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //一次读多个字符
            char[] tempchars = new char[SettingConfig.instance().getTextNum()];
//            char[] tempchars = new char[80];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(file),"UTF-8");

            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
//                    System.out.println("tempchars="+tempchars);
                    buffer.append(tempchars);
                    buffer.append("\n");
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
//                            System.out.println("tempchars i="+tempchars[i]);
                            buffer.append(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e1) {

                }
        }
        deleteCache();
        BufferedWriter writer = writeCache(file);
        try {
            writer.write(buffer.toString());
            writer.close();
            System.out.println("文件格式化完成");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件格式化失败");
        }

        return buffer.toString();
    }

    private static BufferedWriter writeCache(File file) {
        String strCacheName = "";
        if (!file.getName().endsWith(".cache")) {
            strCacheName = ".cache";
        }
        File newFile = new File(file.getParent(), file.getName() + strCacheName);    //建立将要输出的文件和文件名
        OutputStreamWriter outstream = null;//写入字节流
        try {
            outstream = new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedWriter writer = new BufferedWriter(outstream);
        return writer;
    }

    private static void deleteCache() {
        //删掉缓存文件并创建新文件
        File deleteFile = new File(SettingConfig.instance().getCachePath());
        if (deleteFile.exists())
            deleteFile.delete();
        else System.out.println("缓存文件不存在，无法删除");
    }

    private static void copyFIle(String sourcePath, String destPaht) {
        File source = new File(sourcePath);
        File dest = new File(destPaht);
        try {
            InputStream input = null;
            OutputStream output = null;
            try {
                input = new FileInputStream(source);
                output = new FileOutputStream(dest);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buf)) != -1) {
                    output.write(buf, 0, bytesRead);
                }
            } finally {
                input.close();
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}