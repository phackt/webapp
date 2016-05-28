package com.gab.onewebapp.controller;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.gab.onewebapp.beans.ProcessingStatus;
import com.gab.onewebapp.beans.ProcessingTask;

@RestController
public class ProcessingController {

	private final AtomicLong reqId = new AtomicLong();
	
	@RequestMapping("/process-non-blocking")
	public DeferredResult<ProcessingStatus> nonBlockingProcessing(
			@RequestParam(value = "minMs", required = false, defaultValue = "0") int minMs,
			@RequestParam(value = "maxMs", required = false, defaultValue = "0") int maxMs) {

		int processingTimeMs = calculateProcessingTime(minMs, maxMs);

		// Create the deferredResult and initiate a callback object, task, with it
		DeferredResult<ProcessingStatus> deferredResult = new DeferredResult<>();
		ProcessingTask task = new ProcessingTask(reqId.incrementAndGet(), processingTimeMs,deferredResult);

		// Schedule the task for asynch completion in the future
		new Timer().schedule(task, processingTimeMs);

		// Return to let go of the precious thread we are holding on to...
		return deferredResult;
	}

	private int calculateProcessingTime(int minMs, int maxMs) {
		if (maxMs < minMs)
			maxMs = minMs;
		int processingTimeMs = minMs + (int) (Math.random() * (maxMs - minMs));
		return processingTimeMs;
	}
}
