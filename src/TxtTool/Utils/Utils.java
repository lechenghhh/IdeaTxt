package TxtTool.Utils;

import javax.swing.JLabel;

public class Utils {

    public  static  void showLog(JLabel lableText){
        switch ((int) (Math.random() * 4)) {
            case 0:
                lableText.setText("run Starting program: throw_exception Catchpoint 1 (exception thrown), 0x00007ffff7b8f910 in __cxa_throw () from /usr/lib/libstdc++.so.6");
                break;
            case 1:
                lableText.setText("javax.servlet.ServletException: Error creating bean with name 'sessionFactory ' defined in class path resource [applicationContext.xml]: Invocation of init method failed; nested exception is java.lang.NoClassDefFoundError org.apache.struts.action.RequestProcessor.processException(RequestProcessor.java:523) org.apache.struts.action.RequestProcessor.processActionPerform(RequestProcessor.java:421) ");
                break;
            case 2:
                lableText.setText("11-01 14:34:34.438 3823-3903/? E/Unity: java.net.ConnectException: failed to connect to cdp.cloud.unity3d.com/32.123.119.74 (port 3306): connect failed: ETIMEDOUT (Connection timed out) (Filename:  Line: 390)");
                break;
            case 3:
                lableText.setText("Uncaught TypeError: Cannot read property 'isArray' of undefined at cached.php?t=1421837618&f=/js/ext/typeahead.min.js:1 at cached.php?t=1421837618&f=/js/ext/typeahead.min.js:1");
                break;
            case 4:
                lableText.setText(">>> assert 1 != 1 Traceback (most recent call last): File \"<pyshell#70>\", line 1, in <module> assert 1 != 1 AssertionError");
                break;
            default:
                lableText.setText("A typical use for this exception, is in conjunction with the is_callable() function.");
        }
    }
}
