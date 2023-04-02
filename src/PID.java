public class PID {
    /**
     * This Class represents a simple PID, it is developed from material given in lecture lecture 1+2+3.
     * */
    private boolean first_run;
    private final double P;
    private final double I;
    private final double D;
    private final double max_i;
    private double integral;
    private double last_error;

    public PID(double p, double i, double d, double max_i) {
        this.P = p;
        this.I = i;
        this.D = d;
        this.max_i = max_i;
        integral = 0;
        first_run = true;
        last_error = 0;
    }

    public static double constrain(double v, double min, double max) {
        double ans = v;
        if (ans > max) {
            ans = max;
        }
        if (ans < min) {
            ans = min;
        }
        return ans;
    }

    public double update(double error, double dt) {
        if (first_run) {
            last_error = error;
            first_run = false;
        }
        integral += I*error * dt;
        double diff = (error - last_error) / dt;
        double const_integral = constrain(integral, -max_i, max_i);
        double control_out = P * error + D * diff + const_integral;
        this.last_error = error;
        return control_out;
    }
}
