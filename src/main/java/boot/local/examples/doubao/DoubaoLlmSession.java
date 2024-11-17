package boot.local.examples.doubao;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class DoubaoLlmSession
{
    private final String apiKey;
    private final String modelKey;
    private final ArkService service;
    private final List<ChatMessage> messages;
    private final ChatCompletionRequest chatCompletionRequest;

    public DoubaoLlmSession(String apiKey, String modelKey)
    {
        this.apiKey = apiKey;
        this.modelKey = modelKey;
        this.messages = new ArrayList<>();

        service = ArkService.builder().apiKey(apiKey).build();
        chatCompletionRequest = ChatCompletionRequest.builder()
            .model(modelKey)
            .messages(messages)
            .build();
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String getCurrentTime()
    {
        return DATE_FORMAT.format(new Date());
    }

    private static void logMessage(String response)
    {
        System.out.println(getCurrentTime() + ": " + response);
    }

    public String runChat(String messageContent)
    {
        ChatMessage message = new ChatMessage();
        message.setRole(ChatMessageRole.USER);
        message.setContent(messageContent);
        messages.add(message);
        List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();
        ChatCompletionChoice chatCompletionChoice = choices.get(0);
        ChatMessage chatCompletionMessage = new ChatMessage();
        chatCompletionMessage.setRole(ChatMessageRole.ASSISTANT);
        String chatCompletionMessageContent = chatCompletionChoice.getMessage().stringContent();
        chatCompletionMessage.setContent(chatCompletionMessageContent);
//        logMessage(chatCompletionMessageContent);

        messages.add(chatCompletionMessage);

        return chatCompletionMessageContent;
    }
}
