package org.dawn.backend.service;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AiAgentService {

    @SystemMessage("""
            Bạn là một trợ lý AI thông minh và lịch sự.
            Hãy trả lời các câu hỏi của người dùng một cách chính xác.
            """)
    String chat(@MemoryId String sessionId, @UserMessage String message);
}
