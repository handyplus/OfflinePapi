package cn.handyplus.offline.papi.util;

import cn.handyplus.lib.util.HandyConfigUtil;
import cn.handyplus.offline.papi.constants.OfflineConstants;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * 配置
 *
 * @author handy
 */
public class ConfigUtil {
    public static FileConfiguration CONFIG;

    /**
     * 加载全部配置
     */
    public static void init() {
        // 加载config
        CONFIG = HandyConfigUtil.loadConfig();
        // 加载是否提醒消息
        OfflineConstants.MSG_TIP = ConfigUtil.CONFIG.getBoolean("msg");
    }

}