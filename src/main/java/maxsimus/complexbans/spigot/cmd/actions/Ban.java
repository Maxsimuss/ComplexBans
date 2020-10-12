package maxsimus.complexbans.spigot.cmd.actions;

import maxsimus.complexbans.api.ActionType;
import maxsimus.complexbans.spigot.util.ActionInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class Ban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 4) {
            sender.sendMessage("usage: /ban <target> <duration> <reason (use '-' instead of ' ')> <display reason>");
            return true;
        }

        ActionInterface.sendAction(ActionType.BAN, new String[]{sender.getName(), args[0], args[1], args[2], String.join(" ", Arrays.copyOfRange(args, 3, args.length))});

        return true;
    }
}
