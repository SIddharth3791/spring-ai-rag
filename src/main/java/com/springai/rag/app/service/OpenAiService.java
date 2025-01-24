package com.springai.rag.app.service;

import com.springai.rag.app.model.Answer;
import com.springai.rag.app.model.Question;

public interface OpenAiService {

	Answer askQuestion(Question question);
}
