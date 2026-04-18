package org.dawn.backend.config.langchain;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.dawn.backend.config.sys.AppConfig;
import org.dawn.backend.constant.Message;
import org.dawn.backend.service.AiAgentService;

import java.time.Duration;

public class LangChainConfig {
    private static AiAgentService assistant;

    private static final String modeUrl = AppConfig.get("openRouter.url");
    private static final String modeApi = AppConfig.get("openRouter.api");
    private static final String modeType = AppConfig.get("openRouter.model");

    public static AiAgentService getAssistant() {
        if (assistant == null) {
            OpenAiChatModel model = OpenAiChatModel
                    .builder()
                    .baseUrl(modeUrl)
                    .apiKey(modeApi)
                    .modelName(modeType)
                    .timeout(Duration.ofSeconds(60))
                    .logRequests(true)
                    .logResponses(true)
                    .build();

            ChatMemoryProvider memoryProvider = sessionId ->
                    MessageWindowChatMemory.withMaxMessages(10);

            assistant = AiServices
                    .builder(AiAgentService.class)
                    .chatModel(model)
                    .chatMemoryProvider(memoryProvider)
                    .build();
        }
        return assistant;
    }
}
