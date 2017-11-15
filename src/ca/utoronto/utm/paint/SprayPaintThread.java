package ca.utoronto.utm.paint;

public class SprayPaintThread extends Thread {

    private SprayPaint sprayPaint;

    public SprayPaintThread(SprayPaint sprayPaint) {
        this.sprayPaint = sprayPaint;
    }

    @Override
    public void run() {

        while(true) {
            sprayPaint.randomPoints();
            //repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                break;
            }
        }
    }


}
