package com.gab.onewebapp.beans;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

public class ProcessingTask extends TimerTask {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProcessingTask.class);
	private long reqId;
	private DeferredResult<ProcessingStatus> deferredResult;
	private int processingTimeMs;

	public ProcessingTask(long reqId, int processingTimeMs,
			DeferredResult<ProcessingStatus> deferredResult) {
		this.reqId = reqId;
		this.processingTimeMs = processingTimeMs;
		this.deferredResult = deferredResult;
	}

	@Override
	public void run() {
		if (deferredResult.isSetOrExpired()) {
			LOG.warn("Processing of non-blocking request #{} already expired",
					reqId);
		} else {
			boolean deferredStatus = deferredResult
					.setResult(new ProcessingStatus("Ok", processingTimeMs));
			LOG.debug(
					"Processing of non-blocking request #{} done, deferredStatus = {}",
					reqId, deferredStatus);
		}
	}
}
