package org.example;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.*;

import javax.security.auth.login.LoginException;

public class AFRO
{
    private final ShardManager shardManager;
    public AFRO() throws LoginException
    {
        String botToken = "NzA0Mzc4MzI3Mjg4MzgxNjEx.Gv3u4D.ZbeQvXRGHE3t8fVur0v3Rfn1gQ1d8S5LqKBRow";
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(botToken);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("threats."));
        shardManager = builder.build();



    }

    public ShardManager getShardManager()
    {
        return shardManager;
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
