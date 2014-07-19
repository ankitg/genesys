package com.genesys.chatlib.enums;

public enum APICalls {
		REQUESTCHAT(0), 	// API call to initiate a new chat
		STARTTYPING(1),    	// API call to send typing started notification
		STOPTYPING(2),	   	// API call to send typing stopped notification
		MESSAGE(3),			// API call to send a chat message
		COMPLETE(4),		// API call to end the chat session
		GETCHAT(5),       	// API call to get a chat session
		GETTRANSCRIPT(6);	// API call to get chat history / poll for new messages
		
		private int _typeCode;
	 
		private APICalls(int typeCode)
		{
			_typeCode = typeCode;
		}
	 
		/**
		 * Returns the TYPE code.
		 * @return Integer
		 */
		public int getTypeCode()
		{
			return _typeCode;
		}
	
	
}
