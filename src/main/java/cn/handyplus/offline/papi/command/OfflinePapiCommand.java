package cn.handyplus.offline.papi.command;

import cn.handyplus.lib.annotation.HandyCommand;
import cn.handyplus.lib.command.HandyCommandWrapper;
import cn.handyplus.lib.util.BaseUtil;
import cn.handyplus.lib.util.MessageUtil;
import cn.handyplus.offline.papi.constants.TabListEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 命令
 *
 * @author handy
 */
@HandyCommand(name = "offlinePapi")
public class OfflinePapiCommand implements TabExecutor {
    private final static String PERMISSION = "offlinePapi.reload";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        // 判断指令是否正确
        if (args.length < 1) {
            sendHelp(sender);
            return true;
        }
        boolean rst = HandyCommandWrapper.onCommand(sender, cmd, label, args, BaseUtil.getLangMsg("noPermission"));
        if (!rst) {
            sendHelp(sender);
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands;
        if (!sender.hasPermission(PERMISSION)) {
            commands = new ArrayList<>();
        } else {
            commands = TabListEnum.returnList(args, args.length);
        }
        if (commands == null) {
            return null;
        }
        StringUtil.copyPartialMatches(args[args.length - 1].toLowerCase(), commands, completions);
        Collections.sort(completions);
        return completions;
    }

    /**
     * 发送帮助
     *
     * @param sender 发送人
     */
    private void sendHelp(CommandSender sender) {
        if (!sender.hasPermission(PERMISSION)) {
            return;
        }
        List<String> helps = new ArrayList<>();
        helps.add("&a/opapi reload &f- &a重载插件");
        helps.add("&a/opapi init &f- &a初始化全部数据");
        helps.add("&a/opapi delete &f- &a删除全部数据");
        for (String help : helps) {
            MessageUtil.sendMessage(sender, help);
        }
    }

}