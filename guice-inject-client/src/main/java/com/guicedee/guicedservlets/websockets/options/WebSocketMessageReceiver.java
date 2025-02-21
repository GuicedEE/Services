package com.guicedee.guicedservlets.websockets.options;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@SuppressWarnings("unused")
@JsonAutoDetect(fieldVisibility = ANY,
				getterVisibility = NONE,
				setterVisibility = NONE)
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class WebSocketMessageReceiver<J extends WebSocketMessageReceiver<J>>
{
	private String action;
	private String broadcastGroup;
	private String dataService;
	private Map<String, Object> data = new HashMap<>();
	private String webSocketSessionId;

	public WebSocketMessageReceiver()
	{
		//No Config Required
	}
	
	public WebSocketMessageReceiver(String action, Map<String, Object> data)
	{
		this.action = action;
		this.data = data;
	}
	
	@JsonAnySetter
	public void add(String key, String value)
	{
		data.put(key, value);
	}
	
	@Override
	public String toString()
	{
		return "WebSocketMessageReceiver{" +
						"action=" + action +
						", broadcastGroup='" + broadcastGroup + '\'' +
						", data=" + data +
						'}';
	}
}
