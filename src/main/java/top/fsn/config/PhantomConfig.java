package top.fsn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/***
 *
 * @Author:fsn
 * @Date: 2020/5/20 14:21
 * @Description
 */

@Service
public class PhantomConfig {
    @Value("${dwz.plugin.script.phantom}")
    // 保留本地windows参数值, 程序运行后@Value(*)会覆盖这个值
    private String PHANTOM_DIR = "F:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";

    @Value("${dwz.plugin.script.screensHot}")
    private String SCREENSHOT_JS = "F:\\phantomjs-2.1.1-windows\\bin\\screensHot.js";

    @Value("${dwz.plugin.script.crawlText}")
    private String CRAWL_TEXT_JS = "F:\\phantomjs-2.1.1-windows\\bin\\crawlText.js";

    private static final String SPACE = " ";

    public String getScreensHotCommand() {
        return PHANTOM_DIR + SPACE + SCREENSHOT_JS + SPACE;
    }

    public String getCrawlTextCommand() {
        return PHANTOM_DIR + SPACE + CRAWL_TEXT_JS + SPACE;
    }
}
