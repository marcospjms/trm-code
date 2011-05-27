package trm.net.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import trm.net.model.InvalidMessageException;

/**
 *
 * @author Marcos Paulo
 */
public class GsonParser<Message> implements ParserMessage<Message> {

    private GsonUtil<Message> gsonUtil;

    public GsonParser(TypeToken<Message> typeToken) {
        gsonUtil = new GsonUtil<Message>(typeToken);
    }

    @Override
    public String buildMessage(Message message) {
        return gsonUtil.convertMessage(message);
    }

    @Override
    public Message parseMessage(String message) throws InvalidMessageException {
        
        Message result = null;
        
        try {
            result = gsonUtil.parserRequestClient(message);

        } catch (JsonParseException e) {
            throw new InvalidMessageException(e);
        }
        
        return result;
    }
}

class GsonUtil<Message> {

    private Gson gson;
    private Type typeClass;

    public GsonUtil(TypeToken<Message> typeToken) {

        gson = new GsonBuilder().create();
        typeClass = typeToken.getType();
    }

    public String convertMessage(Message message) {
        return gson.toJson(message);
    }

    public Message parserRequestClient(String message) throws JsonParseException {

        return (Message) gson.fromJson(message, typeClass);
    }
}