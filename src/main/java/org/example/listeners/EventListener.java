package org.example.listeners;

import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class EventListener extends ListenerAdapter
{
    private static final Map<String, TreeMap<Long, Integer>> userMessages = new HashMap<>();
    private static final long SPAM_TIME_WINDOW = 5000;
    private static final int MAX_MESSAGES_IN_WINDOW = 5;
    private static final int joinLimit = 5;
    private final AtomicInteger joinCount = new AtomicInteger(0);
    private boolean invitesDisabled = false;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final long TIME_FRAME_SECONDS = 3600; // Set the time frame for enabling invites
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        User user = event.getUser(); //RETRIEVES USER DATA
        String emoji = event.getReaction().getEmoji().getAsReactionCode(); //RETRIEVES EMOJI USED
        String channelReference = event.getChannel().getId(); // RETRIEVES CHANNEL REFERENCED
        if (channelReference.equals("1128834160790868072") && emoji.equals("\u2705"))
        {
            Role roleWanderer = event.getGuild().getRoleById(1227692003131789343L);
            if (roleWanderer != null)
            {
                event.getGuild().addRoleToMember(user, roleWanderer).queue();
            }
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String logChannelID = "1333843664832692254";
        if (event.getAuthor().isBot())
        {
            return;
        }
        TextChannel logChannelName = event.getGuild().getTextChannelById(logChannelID);
        if (logChannelName == null)
        {
            System.err.println("[LOGGING CHANNEL IS NOT FOUND OR HASN'T BEEN INITIALIZED.]");
                    return;
        }
        String userTag = event.getAuthor().getAsTag();
        String pulledChannel = event.getChannel().getName();
        String messageContent = event.getMessage().getContentDisplay();
        if (messageContent.isEmpty())
        {
            messageContent = "[NO TEXT CONTENT]";
        }
        String logEntry = String.format("[LOG MESSAGE] %s WROTE IN #%s -> %s", userTag,pulledChannel,messageContent);

        if (isMessageSpamming(event.getAuthor().getId()))
        {
            event.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("[AFRO]  >>> STOP SPAMMING. ").queue());
        }


        logChannelName.sendMessage(logEntry).queue();

    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event)
    {
        String userTag = event.getMember().getUser().getAsTag();
        String logChannel = "1327454722348552294";
        LocalDateTime time = LocalDateTime.now();
        int currentUserCount = joinCount.incrementAndGet();
        String logMessage = "[ " + userTag + " ENTERED THE SERVER AT " + time + " ] ";
        event.getGuild().getTextChannelById(logChannel).sendMessage(logMessage).queue();
        if (currentUserCount > joinLimit)  //CALLS IF CONCURRENT USER JOINS HAVE EXCEEDED 5.
        {
            disableInvites(event);
            if (logChannel != null)
            {
                event.getGuild().getTextChannelById(logChannel).sendMessage("[ALERT] - TOO MANY USERS HAVE JOINED CONCURRENTLY. INVITES HAVE BEEN DISABLED TEMPORARILY.").queue();
            }
            scheduler.schedule(() -> enableInvites(event), TIME_FRAME_SECONDS, TimeUnit.SECONDS);
        }
    }

    private void disableInvites(GuildMemberJoinEvent event)
    {
        event.getGuild().retrieveInvites().queue(invites ->
        {
            for (Invite invite : invites) {
                invite.delete().queue();
            }
        });
        invitesDisabled = true;

    ;}
    private void enableInvites(GuildMemberJoinEvent event)
    {
        invitesDisabled = false;
        String logChannel = "1327454722348552294";
        if (logChannel != null)
        {
            event.getGuild().getTextChannelById(logChannel).sendMessage("[ALERT] INVITES HAVE BEEN RE-ENABLED.").queue();
        }

    }

    /**
     * Helper method to check if the user is message spamming.
     * @param discordUserID
     * @return either true or false. 
     */
    private boolean isMessageSpamming(String discordUserID)
    {
        long currentTime = System.currentTimeMillis();

        TreeMap<Long, Integer> timestamps = userMessages.computeIfAbsent(discordUserID, k -> new TreeMap<>());

        timestamps.headMap(currentTime - SPAM_TIME_WINDOW).clear();

        if(timestamps.size() >= MAX_MESSAGES_IN_WINDOW)
        {
            return true;
        }

        timestamps.put(currentTime, timestamps.getOrDefault(currentTime, 0) + 1);
        return false;

    }
    
}