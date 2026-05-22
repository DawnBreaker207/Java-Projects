package org.dawn.backend.config.integration;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.dawn.backend.service.system.AiAgentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LangChainConfig {
    private static AiAgentService assistant;

    @Value("${openRouter.url}")
    private String modeUrl;
    @Value("${openRouter.api}")
    private String modeApi;
    @Value("${openRouter.model}")
    private String modeType;

    @Bean
    public AiAgentService getAssistant() {
        if (assistant == null) {
            OpenAiChatModel model = OpenAiChatModel
                    .builder()
                    .baseUrl(modeUrl)
                    .apiKey(modeApi)
                    .modelName(modeType)
                    .timeout(Duration.ofSeconds(60))
                    .maxTokens(4000)
                    .logRequests(true)
                    .logResponses(true)
                    .responseFormat("json_object")
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
