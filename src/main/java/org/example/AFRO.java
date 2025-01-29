package org.example;

/**
 * PROJECT A.F.R.O  (AWARE. FORMAL. REACTIVE. OPERATIVE.)
 * @author Leroy A.
 */
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.*;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.example.commands.CommandsManager;
import org.example.listeners.EventListener;

import javax.security.auth.login.LoginException;


/**
 * JDA-5.2.2 IDS DISCORD BOT
 * Main class to initialize bot.
 */
public class AFRO
{
    private final Dotenv config;
    private final ShardManager shardManager;

    /**
     * Loads environmental variables and builds the bot shard manager.
     * @throws LoginException when bot token is not valid.
     */
    public AFRO() throws LoginException
    {
        config = Dotenv.configure().load();   //LOADS CONFIG FILE
        String botToken = config.get("BOTTOKEN"); //SETS GLOBAL VARIABLE IN CONFIG FILE TO BOT TOKEN VARIABLE

        // Build shard manager
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(botToken);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("threats."));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.enableCache(CacheFlag.ROLE_TAGS);

        shardManager = builder.build();


        //REGISTERED LISTENERS
        shardManager.addEventListener(new EventListener(), new CommandsManager());


    }
    /**
     * Retrieves the bot shard manager
     * @return the ShardManager instance for bot.
     */
    public ShardManager getShardManager()
    {
        return shardManager;
    }
    public Dotenv getConfig()
    {
        return config;

    }
    public static void main(String[] args)
    {
        try
        {
            AFRO bot = new AFRO();
            System.out.println("[AFRO STATUS]: ONLINE");
        }
        catch (LoginException e)
        {
            System.out.println("[ERROR]: PROVIDED TOKEN IS NOT VALID.");
        };

    }
}
