abstract class RequestTest {

    public void runTest(String testname) {
        System.out.println("---------- STARTING TEST: "+testname+" ----------");
    }

    class AsyncJUnit {
        boolean finished = false;

        void finish() {
            finished = true;
        }

        void waitForFinish() throws InterruptedException {
            while (!finished) Thread.sleep(1);
        }
    }
}
