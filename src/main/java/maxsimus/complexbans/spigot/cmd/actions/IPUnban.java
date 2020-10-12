package maxsimus.complexbans.spigot.cmd.actions;

import maxsimus.complexbans.api.ActionType;
import maxsimus.complexbans.spigot.util.ActionInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IPUnban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("usage: /ipunban <target>");
            return true;
        }

        ActionInterface.sendAction(ActionType.IPUNBAN, new String[]{sender.getName(), args[0]});

        return true;
    }
}
