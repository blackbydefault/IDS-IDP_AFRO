package org.example.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Random;

public class EventListener extends ListenerAdapter
{
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        User user = event.getUser(); //RETRIEVES USER DATA
        String emoji = event.getReaction().getEmoji().getAsReactionCode(); //RETRIEVES EMOJI USED
        String channelReference = event.getChannel().getAsMention(); // RETRIEVES CHANNEL REFERENCED
        String terminalChannelID = "1235774045509914695";
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in " + channelReference + " channel.";
        event.getGuild().getTextChannelById(terminalChannelID).sendMessage(message).queue();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        Random random = new Random();
        int dice = random.nextInt(7);
        String message = event.getMessage().getContentRaw();
        if (message.contains("test"))
        {
            if (dice < 3)
            {
                event.getChannel().sendMessage("quiet.").queue();
            }
            else
            {
                event.getChannel().sendMessage("get lost.").queue();
            }
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event)
    {
        Role roleWanderer = event.getGuild().getRoleById(1227692003131789343L);
        if (roleWanderer != null)
        {
            event.getGuild().addRoleToMember(event.getMember(), roleWanderer).queue();
        }
        String userTag = event.getMember().getUser().getAsTag();
        String logChannel = "1327454722348552294";
        LocalDateTime time = LocalDateTime.now();
        String logMessage = "[ " + userTag + " ENTERED THE SERVER AT " + time + " ] ";
        event.getGuild().getTextChannelById(logChannel).sendMessage(logMessage).queue();


    }
}
