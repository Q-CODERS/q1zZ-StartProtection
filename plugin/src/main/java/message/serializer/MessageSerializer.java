package message.serializer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import me.q1zz.startprotection.message.Message;
import me.q1zz.startprotection.message.MessageType;
import org.jetbrains.annotations.NotNull;

public class MessageSerializer implements ObjectSerializer<Message> {

    @Override
    public boolean supports(@NotNull Class<? super Message> type) {
        return Message.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Message message, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {

        data.add("type", message.getType());
        data.add("text", message.getText());

    }

    @Override
    public Message deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {

        final MessageType type = data.get("type", MessageType.class);
        final String text = data.get("text", String.class);

        return new Message(type, text);
    }
}
