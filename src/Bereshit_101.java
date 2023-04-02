import java.io.File;

/**
 * This class represents the basic flight controller of the Bereshit probe to the moon.
 * This class was built using material from out course lecturer ben-moshe.
 */
public class Bereshit_101 {
    public static final double WEIGHT_EMP = 165; // kg
    public static final double WEIGHT_FULE = 420; // kg
    public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FULE; // kg
    // https://davidson.weizmann.ac.il/online/askexpert/%D7%90%D7%99%D7%9A-%D7%9E%D7%98%D7%99%D7%A1%D7%99%D7%9D-%D7%97%D7%9C%D7%9C%D7%99%D7%AA-%D7%9C%D7%99%D7%A8%D7%97
    public static final double MAIN_ENG_F = 430; // N
    public static final double SECOND_ENG_F = 25; // N
    public static final double MAIN_BURN = 0.15; //liter per sec, 12 liter per m'
    public static final double SECOND_BURN = 0.009; //liter per sec 0.6 liter per m'
    public static final double ALL_BURN = MAIN_BURN + 8 * SECOND_BURN;

    public static double accMax(double weight) {
        return acc(weight, true, 8);
    }

    public static double acc(double weight, boolean main, int seconds) {
        double t = 0;
        if (main) {
            t += MAIN_ENG_F;
        }
        t += seconds * SECOND_ENG_F;
        double ans = t / weight;
        return ans;
    }

    public static double desired_hs(double alt) {
        double minAlt = 2000, maxAlt = 30000;
        if (alt < minAlt) {
            return 0;
        }
        if (alt > maxAlt) {
            return Moon.EQ_SPEED;
        }
        double norm = (alt - minAlt) / (maxAlt - minAlt);
        norm = Math.pow(norm, 0.70);
        double dns = norm * Moon.EQ_SPEED;
        return dns;
    }

    public static double desired_vs(double alt) {
        double maxAlt = 30000;
        if (alt > maxAlt) {
            return 0;
        }
        if (alt > 1500) {
            return 24;
        }
        if (alt > 200) {
            return 12;
        }
        if (alt > 50) {
            return 6;
        }
        return 2;
    }

    public static double desired_ang(double alt) {
        return 58.3;
    }

    // 14095, 955.5, 24.8, 2.0
    public static void main(String[] args) {
        System.out.println("Simulating Bereshit's Landing:");
        // starting point:
        double vs = 24.8;
        double hs = 932;
        double dist = 181 * 1000;
        double ang = 58.3; // zero is vertical (as in landing)
        double alt = 13748; // 2:25:40 (as in the simulation) // https://www.youtube.com/watch?v=JJ0VfRL9AMs
        double time = 0;
        double dt = 1; // sec
        double acc = 0; // Acceleration rate (m/s^2)
        double fuel = 121; //
        double weight = WEIGHT_EMP + fuel;
        File file = new File("results.csv");
        System.out.println("time, vs, dvs, hs, dist, alt, ang, weight, acc, fuel");
        double NN = 0.7; // rate[0,1]
        Breshit ber = new Breshit(NN, ang, alt, vs, hs, fuel);
        double P = 0.03;
        double I = 0.0003;
        double D = 0.2;
        PID pid = new PID(P, I, D, 100);
        PID ang_pid = new PID(P, I, D, 100);
        // ***** main simulation loop ******
        while (ber.get_alt() > 0) {
            ber.step(dt);
            hs = ber.get_hs();
            vs = ber.get_vs();
            ang = ber.get_ang();
            double dhs = desired_hs(ber.get_alt());
            double dvs = desired_vs(ber.get_alt());
            double error = vs - dvs;
            double error_hs = hs - dhs;
            double error_ang = ang - desired_ang(ber.get_alt());
            double gas = pid.update(error + error_hs, dt);
            double add_ang = ang_pid.update(error_ang, dt);
            ber.addPower(gas);
            if (Math.abs(hs) < 2) {
                double angC = ber.get_ang();
                if (angC >= 3 * dt) {
                    ber.addAng(-3 * dt);
                } else {
                    ber.addAng(-angC);
                }
            } else {
                ber.addAng(-add_ang);
            }
            if (time % 10 == 0 || ber.get_alt() < 100) {
                System.out.println(ber.get_time() + ", " + ber.get_vs() + ", " + dvs + ", " + ber.get_hs() + ", " + ber.get_h_dist() + ", " + ber.get_alt() + ", " + ber.get_ang() + ", " + ber.get_weight() + ", " + ber.get_acc() + ", " + ber.get_fuel());
            }
        }
    }
}