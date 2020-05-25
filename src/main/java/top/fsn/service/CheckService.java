package top.fsn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.fsn.config.PhantomConfig;
import top.fsn.util.SystemCallUtil;

import java.io.File;
import java.io.IOException;

/***
 *
 * @Author:fsn
 * @Date: 2020/5/25 22:46
 * @Description
 */

@Service
public class CheckService {

    @Value("${dwz.image.path}")
    private String path;

    private String suffix = ".png";

    @Autowired
    protected PhantomConfig phantom;

    // 跑了一个任务, 截了100张图计算出的平均时间在3s左右
    private static final int sleep = 3000;
    private static final int retry = 2;

    public boolean check(String url, String name) throws IOException, InterruptedException {
        synchronized (this) {
            String path = SystemCallUtil.screenshot(
                    url, name + suffix, phantom.getScreensHotCommand()
            );
            File file = null;
            int tmpTry = retry;
            do {
                Thread.sleep(sleep);  // 休息一下
                file = new File(path);
            } while (!file.exists() && (--tmpTry) > 0); // 重试一次

            if (file == null || !file.exists()) {
                // 打印日志;
                return false;
            }
        }
        return true;
    }
}
