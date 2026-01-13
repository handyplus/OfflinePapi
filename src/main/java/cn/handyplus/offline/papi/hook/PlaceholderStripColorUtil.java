package cn.handyplus.offline.papi.hook;

import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.offline.papi.OfflinePapi;
import cn.handyplus.offline.papi.core.PapiUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * 变量扩展
 *
 * @author handy
 */
public class PlaceholderStripColorUtil extends PlaceholderExpansion {
    private final OfflinePapi plugin;

    public PlaceholderStripColorUtil(OfflinePapi plugin) {
        this.plugin = plugin;
    }

    /**
     * 变量前缀
     *
     * @return 结果
     */
    @Override
    public @NotNull String getIdentifier() {
        return "offlinePapi_stripColor";
    }

    /**
     * 注册变量
     *
     * @param player      玩家
     * @param placeholder 变量字符串
     * @return 变量
     */
    @Override
    public String onRequest(OfflinePlayer player, @NotNull String placeholder) {
        return BaseUtil.stripColor(PapiUtil.getPapi(player, placeholder));
    }

    /**
     * 因为这是一个内部类，
     * 你必须重写这个方法，让PlaceholderAPI知道不要注销你的扩展类
     *
     * @return 结果
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * 因为这是一个内部类，所以不需要进行这种检查
     * 我们可以简单地返回{@code true}
     *
     * @return 结果
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * 作者
     *
     * @return 结果
     */
    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * 版本
     *
     * @return 结果
     */
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
