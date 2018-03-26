package com.sumscope.cdh.webbond;

public enum PubSubChannel {

	CHANNEL_FOR_TEST("ChannelForTest");
	
	String name;

	private PubSubChannel(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
