package com.sumscope.cdh.webbond;

public interface IPubSub {

	void publish(String channel, String message);

	void subscribe(ISubListener listener, String channel);

	void psubscribe(ISubListener listener, String pattern);

}
