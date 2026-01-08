package cn.handyplus.offline.papi.listener;

import cn.handyplus.lib.annotation.HandyListener;
import cn.handyplus.lib.internal.HandyLoginEvent;
import cn.handyplus.lib.util.HandyHttpUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * 玩家登录的事件
 *
 * @author handy
 */
@HandyListener
public class HandyLoginEventListener implements Listener {

    /**
     * op 进入服务器发送更新提醒
     *
     * @param event 事件
     */
    @EventHandler
    public void onOpPlayerJoin(HandyLoginEvent event) {
        HandyHttpUtil.checkVersion(event.getPlayer());
    }

}
