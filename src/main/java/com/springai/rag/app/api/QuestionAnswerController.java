package com.springai.rag.app.api;

import org.springframework.web.bind.annotation.RestController;

import com.springai.rag.app.model.Answer;
import com.springai.rag.app.model.Question;
import com.springai.rag.app.service.OpenAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class QuestionAnswerController {
	
	private OpenAiService openAiService;
	
	public QuestionAnswerController(OpenAiService openAiService) {
		this.openAiService = openAiService;
	}
	
	@PostMapping("/ask")
	public Answer askQuestion(@RequestBody Question question) {
		return openAiService.askQuestion(question);
	}
	

}
