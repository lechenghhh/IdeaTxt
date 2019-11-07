package TxtTool.Utils.RandomAccessFileUtils;

import java.io.*;
import java.util.ArrayList;

import TxtTool.Action.SettingConfig;
import TxtTool.Utils.TxtFormat;

public class TxtReader {

    private RandomAccessFile randomAccessFile;
    private ArrayList<Long> lineArr;//创建长整型动态数组lineArr

    private String line = null;  //行文字
    private int eachNum = 1;//每次行数
    private long position = 0;  //指针
    private int page = 0;  //页码
    private int lineCount = 0; //行计数器
    private int lineSum;//总行数

    private volatile static TxtReader txtReader;

    private TxtReader() {
    }

    public static TxtReader instance() {
        if (txtReader == null) {
            synchronized (TxtReader.class) {
                if (txtReader == null) {
                    txtReader = new TxtReader();
                    SettingConfig.instance();
                    txtReader.init(SettingConfig.instance().getFilePath());
                }
            }
        }
        return txtReader;
    }

    public String init(String filePath) {
        File theFile = new File(filePath);
        if (!theFile.exists()) {
            return "File Not Found";
        }


        String result = "";

        TxtFormat.Format();//先对txt进行格式化

        try {
            filePath += ".cache";//为了不锁定源文件，所以创建一个副本，对副本进行操作
            //调用posArray类posget方法（带参）
            PosArray array = new PosArray();
            lineArr = new ArrayList<Long>();
            lineArr = array.posget(filePath);

            File file = new File(filePath);//指定文件路径（命令行参数输入）
            randomAccessFile = new RandomAccessFile(file, "r");//构造方法，以只读方式读文件
            randomAccessFile.seek(position);//跳转到当前指针位置
            //规定eachNum行为一页,打印首页
            for (int i = 0; i < eachNum; i++) {
                line = randomAccessFile.readLine(); //以行读，循环eachNum次
                lineCount++;
                line = new String(line.getBytes("8859_1"), "UTF-8");//编码转化
//                System.out.println(line + "\n"); //打印读出的行
                result += line;
            }
            position = randomAccessFile.getFilePointer();//获取指针的位置（指针会随着读字节流的进度移动）
            page = lineCount / eachNum; //eachNum行为一页
            //调用lineCount 的count方法(带参)：计算文本总行数
            LineCount hlm = new LineCount();
            lineSum = hlm.count(filePath);

            result += " " + page + "/" + lineSum;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String down() {
        String result = "";
        try {
            if (page == (lineSum / eachNum) + 1) {
                position = lineArr.get(lineSum - (lineSum % eachNum));//获取最后一页开头位置（总行-最后一页行数）
                randomAccessFile.seek(position);
                for (int i = 0; i < (lineSum % eachNum); i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
//                    System.out.println(line + "\n");
                    result += line;
                }
                position = randomAccessFile.getFilePointer();
                page = (lineSum / eachNum) + 1;
                result += " " + page + "/" + lineSum;
            }
            //当前页非最后一页，下翻从当前指针位置开始，往下读eachNum行即可
            else {
                randomAccessFile.seek(position);
                for (int i = 0; i < eachNum; i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
//                    System.out.println(line + "\n");
                    result += line;
                }
                position = randomAccessFile.getFilePointer();
                lineCount += eachNum;
                page = lineCount / eachNum;
                result += " " + page + "/" + lineSum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String read2() {
        try {
            randomAccessFile.seek(8);
            line = randomAccessFile.readLine();
            line = new String(line.getBytes("8859_1"), "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public String up() {
        String result = "";
        try {
            if (page == 1) {
                randomAccessFile.seek(0);
                for (int i = 0; i < eachNum; i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
                    System.out.println(line + "\n");
                    result += line;
                }
                position = randomAccessFile.getFilePointer();
                page = 1;
                result += " " + page + "/" + lineSum;

            }
            //当前页非第一页，调用获取行指针的动态数组lineArr找到上一页的开始位置即可
            //执行u操作时，指针位于当前页末，要将指针移动到上一页初，中间行数为(page-2) *eachNum
            //获得上页开始位置指针，逐行往下读eachNum行即可
            else {
                position = lineArr.get((page - 2) * eachNum);
                randomAccessFile.seek(position);
                for (int i = 0; i < eachNum; i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
                    result += line;
                }
                position = randomAccessFile.getFilePointer();
                page = page - 1;
                result += " " + page + "/" + lineSum;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public String toPage(int jump) {
        String result = "";
        try {
            //跳转到最后一页，此处默认最后一页不满eachNum行
            if (jump == (lineSum / eachNum) + 1) {
                position = lineArr.get(lineSum - (lineSum % eachNum));
                randomAccessFile.seek(position);
                for (int i = 0; i < (lineSum % eachNum); i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
                    System.out.println(line + "\n");
                    result += line;

                }
                position = randomAccessFile.getFilePointer();
                result += " " + page + "/" + lineSum;

            }
            //跳转到除最后一页的其他页，从数组中获取当页首行指针：(jump(欲跳转页)-1)*eachNum 行
            else if (jump < (lineSum / eachNum) + 1) {
                position = lineArr.get((jump - 1) * eachNum);
                randomAccessFile.seek(position);
                for (int i = 0; i < eachNum; i++) {
                    line = randomAccessFile.readLine();
                    line = new String(line.getBytes("8859_1"), "UTF-8");
                    //LineCount++;
//                    System.out.println(line + "\n");
                    result = line;
                }
                position = randomAccessFile.getFilePointer();
                page = jump;
                result += " " + page + "/" + lineSum + " ";

            }
            //输入页码超过文本页码范围，提示出错
            else {
                result = "页码超过文本范围/文本不存在 " + jump + "/" + lineSum + " ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String top() {

        String result = "";
        try {
            randomAccessFile.seek(0);
            lineCount = 0;
            for (int i = 0; i < eachNum; i++) {
                line = randomAccessFile.readLine();
                line = new String(line.getBytes("8859_1"), "UTF-8");
                lineCount++;
                System.out.println(line + "\n");
                result += line;
            }
            position = randomAccessFile.getFilePointer();
            page = lineCount / eachNum;
            result += " " + page + "/" + lineSum;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public String bottom() {

        String result = "";
        try {

            position = lineArr.get(lineSum - (lineSum % eachNum));
            randomAccessFile.seek(position);
            for (int i = 0; i < (lineSum % eachNum); i++) {
                line = randomAccessFile.readLine();
                line = new String(line.getBytes("8859_1"), "UTF-8");
                System.out.println(line + "\n");
                result += line;
            }
            position = randomAccessFile.getFilePointer();
            page = (lineSum / eachNum) + 1;
            result += " " + page + "/" + lineSum;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public String read1() {
        String result = "";
        try {
            randomAccessFile.seek(position);//移动文件指针位置
            byte[] buff = new byte[1024];//用于保存实际读取的字节数
            int hasRead = 0;
            //循环读取
            while ((hasRead = randomAccessFile.read(buff)) > 0) {
                //打印读取的内容,并将字节转为字符串输入
                result = new String(buff, "UTF-8");
                result.replaceAll("\n", "");
                System.out.println("result = " + result);
                System.out.println("position = " + position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     public String comd() {

     //此部分实现输入命令command >
     // q:退出；u:往上翻页；d:往下翻页；
     // s:开头页；e：末尾页；g:跳转到规定页码.
     while (true) {
     int command = System.in.read();//键盘输入命令q、u、d、s、e、g
     //输入'q':退出
     if (command == 'q') {
     System.exit(0);
     }

     //输入'u'上翻页
     //当前页码在第一页，上翻仍为第一页，
     //则使指针跳到0处，从开头读eachNum行,仍为第一页
     else if (command == 'u') {

     }
     //执行d操作时，若当前页为最后一页，仍打印出本页
     //若阅读文档最后一页不满eachNum行，整除后最后一页被省略，最后一页页码需手动+1
     else if (command == 'd') {

     }
     //执行s操作，指针移动到开头位置（开头指针为0）即可
     else if (command == 's') {

     }
     //执行e操作，获取最后一页开头指针
     //总行数减去最后一页行数，指针位置在最后一页开头
     else if (command == 'e') {

     }
     //执行g操作，找到指针数组中欲跳转页开头行指针即可
     else if (command == 'g') {

     }

     }
     }
     **/
}
//————————————————
//版权声明：本文为CSDN博主「杨丹的博客」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/yangdan1025/article/details/84797530