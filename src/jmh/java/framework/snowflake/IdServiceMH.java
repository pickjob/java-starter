package framework.snowflake;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author pickjob@126.com
 * @time 2019-03-31
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 1, time = 10)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class IdServiceMH {
    private static Logger logger = LogManager.getLogger(IdServiceMH.class);
    private IdService idService;

    @Setup
    public void generateData() {
        idService =  new IdServiceImpl();
    }

    @Group("IdServiceGroup")
    @GroupThreads(3)
    @Benchmark
    public void benchmark(Blackhole bh) {
        bh.consume(idService.generateSeqId());
    }
}

