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


    @SystemMessage("""
            Bạn là một trợ lý viết lách.
            Nhiệm vụ của bạn là viết tiếp văn bản mà người dùng đang nhập dở.
            CHỈ TRẢ VỀ phần văn bản viết tiếp, không lặp lại phần người dùng đã nhập.
            Không chào hỏi, không giải thích. Trả về tối đa 10 từ.
            """)
    String suggest(@UserMessage String currentText);
}
