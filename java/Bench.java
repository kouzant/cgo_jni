import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Bench {
  static {
    System.loadLibrary("bench");
  }

  public static void main(String... argv) throws Exception {
    Bench bench = new Bench();
    Thread f = new Adder(bench, argv[1].equals("native"));
    f.setDaemon(true);
    f.start();
    TimeUnit.SECONDS.sleep(Long.parseLong(argv[0]));
    f.interrupt();
    TimeUnit.MILLISECONDS.sleep(200);
    System.exit(0);
  }

  private native long add_one(long i);

  private long addOne(long i) {
    return i + 1;
  }

  public static class Adder extends Thread {
    private final Bench bench;
    private final boolean nativeCall;

    private Adder(Bench bench, boolean nativeCall) {
      this.bench = bench;
      this.nativeCall = nativeCall;
    }

    @Override
    public void run() {
      long i = 0;
      while (!Thread.currentThread().isInterrupted()) {
        if (nativeCall) {
          i = bench.add_one(i);
        } else {
          i = bench.addOne(i);
        }
      }
      System.out.println("Finished");
      System.out.println("Number of calls: " + i);
    }
  }
}