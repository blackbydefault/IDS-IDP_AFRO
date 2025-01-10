package org.example;

/**
 * PROJECT A.F.R.O  (AWARE. FORMAL. REACTIVE. OPERATIVE.)
 * @author Leroy A.
 */
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.*;

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
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(botToken);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("threats."));
        shardManager = builder.build();

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
        }
        catch (LoginException e)
        {
            System.out.println("[ERROR]: PROVIDED TOKEN IS NOT VALID.");
        };



    }
}
