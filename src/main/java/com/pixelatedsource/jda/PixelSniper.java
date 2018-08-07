package com.pixelatedsource.jda;

import com.pixelatedsource.jda.blub.CommandClient;
import com.pixelatedsource.jda.blub.CommandClientBuilder;
import com.pixelatedsource.jda.commands.HelpCommand;
import com.pixelatedsource.jda.commands.InviteCommand;
import com.pixelatedsource.jda.commands.VoteCommand;
import com.pixelatedsource.jda.commands.developer.EvalCommand;
import com.pixelatedsource.jda.commands.developer.WeebshCommand;
import com.pixelatedsource.jda.commands.fun.*;
import com.pixelatedsource.jda.commands.management.*;
import com.pixelatedsource.jda.commands.music.*;
import com.pixelatedsource.jda.commands.util.*;
import com.pixelatedsource.jda.db.MySQL;
import com.pixelatedsource.jda.events.AddReaction;
import com.pixelatedsource.jda.events.Channels;
import com.pixelatedsource.jda.events.Chat;
import com.pixelatedsource.jda.events.JoinLeave;
import com.pixelatedsource.jda.utils.WebUtils;
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.entities.Game;
import org.discordbots.api.client.DiscordBotListAPI;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PixelSniper {

    private static final Config config = new Config();
    public static long OWNERID = Long.parseLong(config.getValue("ownerid"));
    private static String TOKEN = config.getValue("token");
    public static String PREFIX = config.getValue("prefix");
    private static String IP = config.getValue("ipadress");
    private static String USER = config.getValue("username");
    private static String PASS = config.getValue("password");
    private static String DBNAME = config.getValue("database");
    private static String DBLTOKEN = config.getValue("dbltoken");
    public static final ExecutorService MAIN_THREAD = Executors.newCachedThreadPool(t -> new Thread(t, "Melijn-main-thread"));


    public static DiscordBotListAPI dblAPI = null;
    public static MySQL mySQL = new MySQL(IP, USER, PASS, DBNAME);
    private static ShardManager shardManager = null;

    public static void main(String[] args) throws LoginException {
        new WebUtils();
        mySQL.update("TRUNCATE TABLE commands");
        CommandClientBuilder client = new CommandClientBuilder();
        client.setOwnerId(OWNERID);
        client.setPrefix(PREFIX);
        client.addCommands(new SetVerificationThreshold(), new SetUnverifiedRole(), new SetVerificationCode(), new SetVerificationChannel(), new ShardsCommand(), new ClearChannelCommand(), new NyanCatCommand(), new SummonCommand(), new ForwardCommand(), new RewindCommand(), new NightCoreCommand(), new TremoloCommand(), new PitchCommand(), new SpeedCommand(), new CryCommand(), new ShrugCommand(), new DabCommand(), new HighfiveCommand(), new WastedCommand(), new LewdCommand(), new PunchCommand(), new ShuffleCommand(), new EvalCommand(), new WeebshCommand(), new SayCommand(), new DiscordMemeCommand(), new LoopQueueCommand(), new SetNotifications(), new VoteCommand(), new InviteCommand(), new SetJoinLeaveChannelCommand(), new SetJoinRoleCommand(), new SetJoinMessageCommand(), new SetLeaveMessageCommand(), new TriggeredCommand(), new SlapCommand(), new PatCommand(), new FilterCommand(), new PotatoCommand(), new PauseCommand(), new SPlayCommand(), new BanCommand(), new HistoryCommand(), new MuteCommand(), new SetMuteRoleCommand(), new TempMuteCommand(), new UnmuteCommand(), new KickCommand(), new AvatarCommand(), new WarnCommand(), new PurgeCommand(), new HelpCommand(), new PingCommand(), new PlayCommand(), new QueueCommand(), new CatCommand(), new SkipCommand(), new ClearCommand(), new StopCommand(), new ResumeCommand(), new VolumeCommand(), new InfoCommand(), new UserInfoCommand(), new LoopCommand(), new TextToEmojiCommand(), new SeekCommand(), new PermCommand(), new NowPlayingCommand(), new RemoveCommand(), new GuildInfoCommand(), new RolesCommand(), new RoleCommand(), new DogCommand(), new SetPrefixCommand(), new SetMusicChannelCommand(), new SetLogChannelCommand(), new TempBanCommand(), new UnbanCommand(), new SetStreamerModeCommand(), new SetStreamUrlCommand());
        CommandClient commandClient = client.build();

        shardManager = new DefaultShardManagerBuilder()
                .setShardsTotal(1)
                .setToken(TOKEN)
                .setGame(Game.playing(PREFIX + "help | melijn.com"))
                .setAutoReconnect(true)
                .addEventListeners(commandClient, new JoinLeave(), new AddReaction(), new Channels(), new Chat())
                .setAudioSendFactory(new NativeAudioSendFactory())
                .build();

        dblAPI = new DiscordBotListAPI.Builder()
                .token(DBLTOKEN)
                .botId("368362411591204865")
                .build();

        Helpers.startTimer(shardManager.getShardById(0), dblAPI, 0);
        Helpers.starttime = System.currentTimeMillis();
        /*setting avatar & username
        try {
            File f = new File(System.getProperty("java.io.tmpdir") + "tmp" + ".png");
            f.deleteOnExit();
            FileUtils.copyURLToFile(new URL("https://melijn.com/files/u/13-06-2018--16.15-11s.png"), f);
            jda.getSelfUser().getManager().setAvatar(Icon.from(f)).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public ShardManager getShardManager() {
        return shardManager;
    }
}
