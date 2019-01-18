package basic.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author pickjob@126.com
 * @time 2019-01-18
 */
public class DirectoryWatchShowCase {
    private static final Logger logger = LogManager.getLogger(DirectoryWatchShowCase.class);
    private final WatchService watcher;
    private final boolean recursive;
    private boolean trace = false;
    private int count;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        count++;
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException{
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    DirectoryWatchShowCase(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.recursive = recursive;

        if (recursive) {
            logger.info("Scanning {} ...", dir);
            registerAll(dir);
            logger.info("Done.");
        } else {
            register(dir);
        }
    }

    /**
     * Process all events for keys queued to the watcher
     */
    void processEvents() {
        for (;;) {

            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = ((Path)key.watchable()).resolve(name);

                // print out event
                logger.info("%s: %s", event.kind().name(), child);

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key
            boolean valid = key.reset();
            if (!valid) {
                // directory no longer accessible
                count--;
                if (count == 0)
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0 || args.length > 2) {
            logger.error("usage: java DirectoryWatchShowCase [-r] dir");
            System.exit(-1);
        }
        boolean recursive = false;
        int dirArg = 0;
        if ("-r".equals(args[0])) {
            if (args.length < 2) {
                logger.error("usage: java DirectoryWatchShowCase [-r] dir");
                System.exit(-1);
            }
            recursive = true;
            dirArg++;
        }
        Path dir = Paths.get(args[dirArg]);
        new DirectoryWatchShowCase(dir, recursive).processEvents();
    }
}
