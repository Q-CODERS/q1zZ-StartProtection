package me.q1zz.startprotection.command.handler;


import dev.rollczi.litecommands.handler.result.ResultHandler;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import lombok.AllArgsConstructor;
import me.q1zz.startprotection.message.Message;
import me.q1zz.startprotection.message.MessageAnnouncer;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public class MessageHandler implements ResultHandler<CommandSender, Message> {
    private final MessageAnnouncer announcer;
    @Override
    public void handle(Invocation<CommandSender> invocation, Message message, ResultHandlerChain<CommandSender> chain) {
        this.announcer.sendMessage(invocation.sender(), message);
    }

}
