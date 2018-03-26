package com.sumscope.cdh.webbond;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FsWatcher extends Thread {

	private static Logger logger = LoggerFactory.getLogger(FsWatcher.class);
	private volatile boolean terminate;
	private Callable<Void> callable;
	private String file;

	public FsWatcher(Callable<Void> callable, String file) {
		this.file = file;
		this.callable = callable;
		terminate = false;
	}

	@Override
	public void run() {
		Path dir = Paths.get("");
		logger.info("Current path: " + dir.toAbsolutePath());
		try (WatchService watcher = dir.getFileSystem().newWatchService()) {
			dir.register(watcher, /* ENTRY_CREATE, ENTRY_DELETE, */ StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				if (terminate) {
					break;
				}
				WatchKey watchKey = watcher.poll(10, TimeUnit.SECONDS);
				if (watchKey == null) {
					continue;
				}
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> event : events) {
					Path p = (Path) event.context();
					/* multiple modify events are fired, and file size is 0. */
					long len = p.toFile().length();
					if (p.toString().equals(file) && len != 0) {
						callable.call();
					}
				}
				watchKey.reset();
			}
		} catch (Exception e) {
			logger.info(null, e);
		}
	}

	public void terminate() {
		terminate = true;
	}

}
