package com.springai.rag.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.springai.rag.app.model.Answer;
import com.springai.rag.app.model.Question;

@Service
public class OpenAiServiceImpl implements OpenAiService {
	
	private ChatModel chatModel;
	private SimpleVectorStore simpleVectorStore;
	
	
	@Value("classpath:templates/rag-prompt-template.st")
	private Resource ragPromptTemplate;
	
	public OpenAiServiceImpl(ChatModel chatModel, SimpleVectorStore simpleVectorStore) {
		this.chatModel = chatModel;
		this.simpleVectorStore = simpleVectorStore;
	}

	@Override
	public Answer askQuestion(Question question) {
		
		List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.builder()
				.query(question.question()).topK(4).build());
		
		List<String> contentList = documents.stream().map(Document :: getContent).toList();
		
		PromptTemplate promptTemplate = new PromptTemplate(question.question());
		Prompt prompt = promptTemplate.create(Map.of("input", question.question(), 
				"documents", String.join("\n", contentList)));
		
		ChatResponse chatResponse = chatModel.call(prompt);
		
		return new Answer(chatResponse.getResult().getOutput().getContent());
	}
	
	

}
