package org.example.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandsManager extends ListenerAdapter
{
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        String command = event.getName();
        switch(command)
        {
            case "verify":
                event.reply("***[ Verify Here. ]***").queue();
                break;

            case "welcome":
                String userTag = event.getUser().getAsTag();
                event.reply(" What's good **" + userTag + "**. ").queue();
                break;

            case "info":
                event.reply("\nInspired by Afro Samurai and developed by 13LACK0UT, I am a Discord IDS (Intrusion Detection System) dedicated to detect malicious users and prevent nuking/server raids.").queue();
                break;


            default:
                event.reply("[UNKNOWN COMMAND]  `" + command + "`").queue();
        }
//        if (command.equals("welcome"))
//        {
//            String userTag = event.getUser().getAsTag();
//            event.reply(" What's good **" + userTag + "**. ").queue();
//        }


    }

    //GLOBAL COMMAND

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event)
    {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "GET WELCOMED BY BOT."));
        commandData.add(Commands.slash("info", "BOT BIO."));
        commandData.add(Commands.slash("verify", "SET VERIFY MESSAGE."));
        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}
