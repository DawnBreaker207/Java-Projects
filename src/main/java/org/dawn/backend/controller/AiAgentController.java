package org.dawn.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.annotation.Post;
import org.dawn.backend.config.response.ResponseObject;
import org.dawn.backend.controller.config.AbstractController;
import org.dawn.backend.dto.request.ChatRequest;
import org.dawn.backend.service.AiAgentService;

@RequiredArgsConstructor
public class AiAgentController extends AbstractController {
    private final AiAgentService agentService;

    @Post("/chat")
    public ResponseObject<String> chat(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        ChatRequest dto = body(req, ChatRequest.class);
        String answer = agentService.chat(sessionId, dto.getMessage());
        return ResponseObject.success(answer);
    }
}
