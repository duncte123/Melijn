package me.melijn.jda.commands.util;

import me.melijn.jda.Helpers;
import me.melijn.jda.blub.Category;
import me.melijn.jda.blub.Command;
import me.melijn.jda.blub.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.User;

import static me.melijn.jda.Melijn.PREFIX;

public class AvatarCommand extends Command {

    public AvatarCommand() {
        this.commandName = "avatar";
        this.usage = PREFIX + this.commandName + " [user]";
        this.description = "Shows you your avatar and link.";
        this.aliases = new String[]{"profilepicture"};
        this.category = Category.UTILS;
        this.permissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getGuild() == null || Helpers.hasPerm(event.getMember(), this.commandName, 0)) {
            String[] args = event.getArgs().split("\\s+");
            User user;
            if (args.length == 0 || args[0].equalsIgnoreCase("")) {
                user = event.getAuthor();
            } else {
                user = Helpers.getUserByArgsN(event, args[0]);
            }
            if (user != null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Helpers.EmbedColor);
                eb.setTitle(user.getName() + "#" + user.getDiscriminator() + "'s avatar");
                eb.setImage(user.getEffectiveAvatarUrl() + "?size=2048");
                eb.setDescription("[open](" + user.getEffectiveAvatarUrl() + "?size=2048)");
                 event.reply(eb.build());
            } else {
                event.reply("Unknown user");
            }
        } else {
            event.reply("You need the permission `" + commandName + "` to execute this command.");
        }
    }
}
