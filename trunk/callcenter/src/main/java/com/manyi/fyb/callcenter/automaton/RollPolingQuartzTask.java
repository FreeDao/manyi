package com.manyi.fyb.callcenter.automaton;

import java.util.Date;

import com.manyi.fyb.callcenter.utils.HttpClientHelper;

public class RollPolingQuartzTask extends AutomatonTask{

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (manually) {
			logger.info("no start rolling {}" , new Date());
			return;
		}
		logger.info("start rolling.... at {}" , new Date());
		
		String himsShortUrl = "/rollpoling/check.rest";
		HttpClientHelper.sendRestInterShort(himsShortUrl);
	}
}
