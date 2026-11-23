package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;

public class WordCensor extends SocialChannelDecorator {

	private String wordToCensor;

	public WordCensor(String wordToCensor) {
		this.wordToCensor = wordToCensor;
	}

	public WordCensor(String wordToCensor, SocialChannel channel) {
		this.wordToCensor = wordToCensor;
		this.delegate = channel;
	}

	@Override
	public void deliverMessage(String message) {
		if (message != null && this.wordToCensor != null) {
			message = message.replace(this.wordToCensor, "###");
		}
		if (delegate != null) {
			delegate.deliverMessage(message);
		}
	}
}