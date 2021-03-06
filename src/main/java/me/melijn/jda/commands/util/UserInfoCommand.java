package me.melijn.jda.commands.util;

import me.melijn.jda.Helpers;
import me.melijn.jda.blub.Category;
import me.melijn.jda.blub.Command;
import me.melijn.jda.blub.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;

import java.text.SimpleDateFormat;
import java.util.Date;

import static me.melijn.jda.Melijn.PREFIX;

public class UserInfoCommand extends Command {

    public UserInfoCommand() {
        this.commandName = "userinfo";
        this.description = "Shows you useful information about a user/member";
        this.usage = PREFIX + this.commandName + " <user>";
        this.aliases = new String[]{"profile", "memberinfo"};
        this.category = Category.UTILS;
        this.permissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getGuild() == null || Helpers.hasPerm(event.getMember(), this.commandName, 0)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm ss");
            String[] args = event.getArgs().split("\\s+");
            Helpers.retrieveUserByArgs(event, args[0], user -> {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Helpers.EmbedColor);
                eb.setTitle(user.getName() + "#" + user.getDiscriminator() + "'s profile");
                eb.setThumbnail(user.getEffectiveAvatarUrl() + "?size=2048");
                if (event.getGuild() == null || event.getGuild().getMember(user) == null) {
                    eb.addField("ID:", user.getId(), true);
                    eb.addField("Avatar:", "**[link](" + user.getEffectiveAvatarUrl() + "?size=2048)**", true);
                    eb.addField("Discord join date:", simpleDateFormat.format(Date.from(user.getCreationTime().toInstant())) + "s", true);
                    eb.addField("Is Member:", "no", true);
                    eb.addField("Is Bot:", String.valueOf(user.isBot()), false);
                } else {
                    Member member = event.getGuild().getMember(user);
                    String nickname = member.getNickname() == null ? "None" : member.getNickname();
                    eb.addField("ID:", user.getId(), false);
                    eb.addField("Nickname:", nickname, true);
                    eb.addField("Avatar:", "**[link](" + user.getEffectiveAvatarUrl() + "?size=2048)**", true);
                    eb.addField("Status:", member.getOnlineStatus().name().toLowerCase(), false);
                    eb.addField("Discord join date:", simpleDateFormat.format(Date.from(user.getCreationTime().toInstant())) + "s", true);
                    eb.addField("Guild join date:", simpleDateFormat.format(Date.from(member.getJoinDate().toInstant())) + "s", true);
                    eb.addField("Is Owner:", member.isOwner() ? "yes" : "no", true);
                    eb.addField("Is Member:", "yes", true);
                    eb.addField("Is Bot:", user.isBot() ? "yes" : "no", false);
                }
                event.reply(eb.build());
            });
        } else {
            event.reply("You need the permission `" + commandName + "` to execute this command.");
        }
    }
}
