package org.example.listeners;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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
        String message = event.getMessage().getContentRaw();
        if (message.contains("test"))
        {
            event.getChannel().sendMessage("quiet.").queue();
        }


    }
}
