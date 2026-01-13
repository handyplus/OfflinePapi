package cn.handyplus.offline.papi.core;

import cn.handyplus.lib.core.CollUtil;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.offline.papi.enter.OfflinePapiEnter;
import cn.handyplus.offline.papi.hook.PlaceholderApiUtil;
import cn.handyplus.offline.papi.param.OfflineParam;
import cn.handyplus.offline.papi.service.OfflinePapiService;
import cn.handyplus.offline.papi.util.ConfigUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PAPI 工具类
 *
 * @author handy
 * @since 1.1.9
 */
public class PapiUtil {

    /**
     * 获取变量
     *
     * @param player      玩家
     * @param placeholder 变量
     * @return 结果
     */
    public static @Nullable String getPapi(OfflinePlayer player, @NonNull String placeholder) {
        List<String> papiList = ConfigUtil.CONFIG.getStringList("papi");
        if (CollUtil.isEmpty(papiList)) {
            return null;
        }
        OfflineParam offlineParam = getOfflineParam(placeholder, 1, papiList);
        if (offlineParam == null) {
            return null;
        }
        // 如果是 me 查询自己
        if ("me".equalsIgnoreCase(offlineParam.getPlayerName()) && player != null) {
            offlineParam.setPlayerName(player.getName());
        }
        // 如果玩家在线.直接获取实时变量
        Optional<Player> onlinePlayerOpt = BaseUtil.getOnlinePlayer(offlineParam.getPlayerName());
        if (onlinePlayerOpt.isPresent()) {
            String value = PlaceholderApiUtil.set(onlinePlayerOpt.get(), offlineParam.getPapi());
            // 如果可以获取到的话就返回实时变量
            if (!value.equals(offlineParam.getPapi())) {
                return value;
            }
        }
        // 玩家不在线在获取离线变量
        Optional<OfflinePapiEnter> offlinePapiEnterOptional = OfflinePapiService.getInstance().findByPlayerNameAndPapi(offlineParam.getPlayerName(), offlineParam.getPapi());
        String defaultValue = ConfigUtil.CONFIG.getString("defaultValue." + offlineParam.getPapiType(), "");
        return offlinePapiEnterOptional.map(OfflinePapiEnter::getVault).orElse(defaultValue);
    }

    /**
     * 获取变量信息
     *
     * @param placeholder 变量
     * @param number      数量
     * @param list        现有配置的变量
     * @return OfflineParam
     */
    private static OfflineParam getOfflineParam(String placeholder, int number, List<String> list) {
        String playerName;
        String[] placeholderStr = placeholder.split("_");
        List<String> strList = new ArrayList<>();
        for (int i = 0; i < number && number <= placeholderStr.length; i++) {
            strList.add(placeholderStr[i]);
        }
        playerName = CollUtil.listToStr(strList, "_");
        // 递归到尾直接返回null
        if (playerName.equals(placeholder)) {
            return null;
        }
        String papi = placeholder.replaceFirst(playerName + "_", "");
        // 判断是否包含配置的变量,如果不包含递归处理下一个
        if (!list.contains("%" + papi + "%")) {
            return getOfflineParam(placeholder, number + 1, list);
        }
        return OfflineParam.builder().playerName(playerName).papiType(papi).papi("%" + papi + "%").build();
    }

}
