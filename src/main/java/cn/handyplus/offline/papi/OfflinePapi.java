package cn.handyplus.offline.papi;

import cn.handyplus.lib.InitApi;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.offline.papi.hook.PlaceholderUtil;
import cn.handyplus.offline.papi.job.PapiDataJob;
import cn.handyplus.offline.papi.util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 主类
 *
 * @author handy
 */
public class OfflinePapi extends JavaPlugin {
    public static boolean USE_PAPI = true;

    @Override
    public void onEnable() {
        InitApi initApi = InitApi.getInstance(this);
        // 加载配置文件
        ConfigUtil.init();
        // 加载 Placeholder
        new PlaceholderUtil(this).register();
        initApi.initCommand("cn.handyplus.offline.papi.command")
                .initListener("cn.handyplus.offline.papi.listener")
                .enableSql("cn.handyplus.offline.papi.enter")
                .addMetrics(18120)
                .checkVersion();

        // 初始化定时任务
        PapiDataJob.init();

        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "已成功载入服务器！");
        MessageUtil.sendConsoleMessage(ChatColor.GREEN + "Author:handy WIKI: https://ricedoc.handyplus.cn/wiki/OfflinePapi/README");
    }

    @Override
    public void onDisable() {
        InitApi.disable();
    }

}