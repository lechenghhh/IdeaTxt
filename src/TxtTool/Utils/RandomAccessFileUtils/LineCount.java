package TxtTool.Utils.RandomAccessFileUtils;/*
 * 该方法实现：
 * 计算小说总行数
 */

import java.io.*;

public class LineCount {

    public int count(String a) {//构造带参函数
        File file = new File(a);//指定文件路径
        int lineCount = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            //按行读小说，累加
            while (raf.readLine() != null) {
                lineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCount;//返回总行数
    }
}
//————————————————
//版权声明：本文为CSDN博主「杨丹的博客」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/yangdan1025/article/details/84797530