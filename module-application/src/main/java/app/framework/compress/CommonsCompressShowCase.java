package app.framework.compress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Apache Commons Compress: 压缩框架
 *      ArchiveInputStream、ArchiveOutputStream: 支持归档格式
 *          7z, ar, arj, cpio, dump, tar, zip, Pack200
 *      CompressorInputStream、CompressorOutputStream: 支持压缩格式
 *           gzip, bzip2, xz, lzma, Pack200, DEFLATE, Brotli, DEFLATE64, ZStandard, Z
 *
 * @author: pickjob@126.com
 * @date: 2022-11-29
 */
public class CommonsCompressShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
        List<Path> logs;
        try (Stream<Path> walk = Files.walk(Path.of("logs"))) {
            logs = walk.filter(Files::isRegularFile).toList();
        }
        logger.info("files: {}", logs);
        // 打包、压缩
        try (
                OutputStream fo = Files.newOutputStream(Paths.get("logs/logs.tar.xz"));
                OutputStream bo = new BufferedOutputStream(fo);
                OutputStream xzo = new XZCompressorOutputStream(bo);
                ArchiveOutputStream<TarArchiveEntry> o = new TarArchiveOutputStream(xzo)) {
            for (Path log : logs) {
                File f = log.toFile();
                TarArchiveEntry entry = o.createArchiveEntry(f, new File(f.getParent()).getName() + "-" + f.getName());
                o.putArchiveEntry(entry);
                try (InputStream i = Files.newInputStream(f.toPath())) {
                    IOUtils.copy(i, o);
                }
                o.closeArchiveEntry();
            }
        }
        // Tar Archive && XZ Uncompress
        String targetDir = "logs/logs-bak";
        try (
                InputStream fi = Files.newInputStream(Paths.get("logs/logs.tar.xz"));
                InputStream bi = new BufferedInputStream(fi);
                InputStream xzi = new XZCompressorInputStream(bi);
                ArchiveInputStream<TarArchiveEntry> i = new TarArchiveInputStream(xzi)) {
            ArchiveEntry entry = null;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    continue;
                }
                String name = STR."\{targetDir}/\{entry.getName()}";
                File f = new File(name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException(STR."failed to create directory \{f}");
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException(STR."failed to create directory \{parent}");
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(i, o);
                    }
                }
            }
        }
    }
}