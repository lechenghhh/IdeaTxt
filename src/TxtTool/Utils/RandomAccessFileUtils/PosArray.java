package TxtTool.Utils.RandomAccessFileUtils;
/*
 * 该方法实现：
 * 创建动态数组，将输出的每行文本的末尾指针存入数组
 * 返回值为获取了文本所有行指针的动态数组
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.lang.*;

public class PosArray {
    public ArrayList<Long> posget(String b) {	//方法带参并且有返回值，定义为动态数组类型
        ArrayList<Long> pageEnd = new ArrayList<Long>();//创建动态数组pageEnd
        String line = null;  //行
        long position=0; //定义指针
        int page = 0;  //页码
        try {
            File file=new File(b);//指定文件
            RandomAccessFile raf = new RandomAccessFile(file,"r");//以只读方式读取文件
            pageEnd.add( position); //将每行指针存入动态数组
            while((line= raf.readLine())!=null){//按行读取文档
                position=raf.getFilePointer();//获取该行指针
                pageEnd.add(position);//将获取的行指针存入数组
                line =new String(line.getBytes("8859_1"), "UTF-8");//编码转化
//                System.out.println(line);
//                System.out.println("p= "+position);
            }
        }
        catch(Exception e){
            //e.printStackTrace(
            return pageEnd;//返回动态数组的值
        }
        return pageEnd;

}}

//————————————————
//版权声明：本文为CSDN博主「杨丹的博客」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/yangdan1025/article/details/84797530