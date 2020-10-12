package maxsimus.complexbans.spigot.cmd.actions;

import maxsimus.complexbans.api.ActionType;
import maxsimus.complexbans.spigot.util.ActionInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class Kick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 3) {
            sender.sendMessage("usage: /ban <target> <reason (use '-' instead of ' ')> <display reason>");
            return true;
        }

        ActionInterface.sendAction(ActionType.KICK, new String[]{sender.getName(), args[0], args[1], String.join(" ", Arrays.copyOfRange(args, 2, args.length))});

        return true;
    }
}
