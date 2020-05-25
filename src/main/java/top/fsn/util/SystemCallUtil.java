package top.fsn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/***
 *
 * @Author:fsn
 * @Date: 2020/5/18 14:20
 * @Description 执行一些脚本的工具类
 */

public class SystemCallUtil {

    /**
     *
     * @param url   待截图的网站链接
     * @param path  图片路径+名称 eg. F:\\pic\\9.png
     * @param screensHotCommand 截图命令
     * eg. eg. F:\phantomjs-2.1.1-windows\bin\phantomjs.exe F:\phantomjs-2.1.1-windows\bin\screensHot.js
     * @return      返回图片保存路径
     * @description 注意, 命令形式调用外部工具的时候, 都要考虑并发问题, 否则容易出现
     * 部分线程可以截图成功, 部分截图不成功
     * @throws IOException
     */
    public static String screenshot(String url, String path, String screensHotCommand) throws IOException, InterruptedException {
        InputStream inputStream = null;
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String command = screensHotCommand + url + " " + path;
            process = runtime.exec(command);
            inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (reader.readLine() != null) ;
            reader.close();
            return path;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     *
     * @param url   待爬取的网站链接
     * @param crawlTextCommand  爬取文本命令
     * eg. F:\phantomjs-2.1.1-windows\bin\phantomjs.exe F:\phantomjs-2.1.1-windows\bin\crawlText.js
     * @return      爬取的文本内容
     * @throws IOException
     */
    public static String crawlText(String url, String crawlTextCommand) throws IOException {
        InputStream inputStream = null;
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String command = crawlTextCommand + url;
            process = runtime.exec(command);
            inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String content;
            while ((content = reader.readLine()) != null) {
                builder.append(content);
            }
            return builder.toString();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
    }
}
