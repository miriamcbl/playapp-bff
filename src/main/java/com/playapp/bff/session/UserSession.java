package com.playapp.bff.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

import com.playapp.bff.constants.Constants;

/**
 * The Class UserSession.
 */
public class UserSession implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message history. */
	private List<Message> messageHistory = new ArrayList<>();

	/**
	 * Instantiates a new user session.
	 */
	public UserSession() {
		messageHistory.add(new SystemMessage(Constants.LIMIT_THEMES_SYSTEM_PROMPT));
	}

	/**
	 * Adds the message to history.
	 *
	 * @param role    the role
	 * @param content the content
	 */
	public void addMessageToHistory(String role, String content) {
		// según el rol que sea
		MessageType messageType = MessageType.fromValue(role);
		switch (messageType) {
		case ASSISTANT:
			messageHistory.add(new AssistantMessage(content));
			break;
		case SYSTEM:
			messageHistory.add(new SystemMessage(content));
			break;
		case USER:
			messageHistory.add(new UserMessage(content));
			break;
		default:
		}
	}

	/**
	 * Gets the message history.
	 *
	 * @return the message history
	 */
	public List<Message> getMessageHistory() {
		return messageHistory;
	}
}
