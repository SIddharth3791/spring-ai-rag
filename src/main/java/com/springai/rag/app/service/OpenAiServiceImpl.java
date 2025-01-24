package com.springai.rag.app.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.springai.rag.app.model.Answer;
import com.springai.rag.app.model.Question;

@Service
public class OpenAiServiceImpl implements OpenAiService {
	
	private ChatModel chatModel;
	
	public OpenAiServiceImpl(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	@Override
	public Answer askQuestion(Question question) {
		
		PromptTemplate promptTemplate = new PromptTemplate(question.question());
		Prompt prompt = promptTemplate.create();
		
		ChatResponse chatResponse = chatModel.call(prompt);
		
		return new Answer(chatResponse.getResult().getOutput().getContent());
	}
	
	

}
