public class Breshit {
    /**
     * This class represents the breshit israeli Spaceraft to the moon.
     * Developed from material given to us by our instructor in lecture 1+2+3.
     * */
    public static final double WEIGHT_EMP = 165; // kg
    public static final double WEIGHT_FULE = 420; // kg
    public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FULE; // kg
    // https://davidson.weizmann.ac.il/online/askexpert/%D7%90%D7%99%D7%9A-%D7%9E%D7%98%D7%99%D7%A1%D7%99%D7%9D-%D7%97%D7%9C%D7%9C%D7%99%D7%AA-%D7%9C%D7%99%D7%A8%D7%97
    public static final double MAIN_ENG_F = 430; // N
    public static final double SECOND_ENG_F = 25; // N
    public static final double MAIN_BURN = 0.15; //liter per sec, 12 liter per m'
    public static final double SECOND_BURN = 0.009; //liter per sec 0.6 liter per m'
    public static final double ALL_BURN = MAIN_BURN + 8 * SECOND_BURN;

    private double _power, _ang;
    private double _alt, _h_dist = 0;
    private double _vs, _hs, _acc = 0;
    private double _fuel, _weight;
    private double _time;

    public Breshit(double _power, double _ang, double _alt, double _vs, double _hs, double _fuel) {
        this._power = _power;
        this._ang = _ang;
        this._alt = _alt;
        this._vs = _vs;
        this._hs = _hs;
        this._fuel = _fuel;
    }

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

    @Override
    public String toString() {
        return "Breshet{" +
                "_power=" + _power +
                ", _ang=" + _ang +
                ", _alt=" + _alt +
                ", _h_dist=" + _h_dist +
                ", _vs=" + _vs +
                ", _hs=" + _hs +
                ", _acc=" + _acc +
                ", _fuel=" + _fuel +
                ", _weight=" + _weight +
                ", _time=" + _time +
                '}';
    }

    public double get_power() {
        return _power;
    }

    public void set_power(double _power) {
        this._power = _power;
    }

    public double get_ang() {
        return _ang;
    }

    public void set_ang(double _ang) {
        this._ang = _ang;
    }

    public double get_alt() {
        return _alt;
    }

    public void set_alt(double _alt) {
        this._alt = _alt;
    }

    public double get_h_dist() {
        return _h_dist;
    }

    public void set_h_dist(double _h_dist) {
        this._h_dist = _h_dist;
    }

    public double get_vs() {
        return _vs;
    }

    public void set_vs(double _vs) {
        this._vs = _vs;
    }

    public double get_hs() {
        return _hs;
    }

    public void set_hs(double _hs) {
        this._hs = _hs;
    }

    public double get_acc() {
        return _acc;
    }

    public void set_acc(double _acc) {
        this._acc = _acc;
    }

    public double get_fuel() {
        return _fuel;
    }

    public void set_fuel(double _fuel) {
        this._fuel = _fuel;
    }

    public double get_weight() {
        return _weight;
    }

    public void set_weight(double _weight) {
        this._weight = _weight;
    }

    public double get_time() {
        return _time;
    }

    public void set_time(double _time) {
        this._time = _time;
    }

    public void addPower(double d) {
        double dd = _power + d;
        if (dd >= 0.0 && dd <= 1) {
            _power = dd;
        }
    }

    public void setPower(double v) {
        if (v >= 0 && v <= 1) {
            _power = v;
        }
    }

    public void addAng(double d) {
        double dd = _ang + d;
        if (dd >= 0 && dd <= 90) {
            _ang = dd;
        }
    }

    public void step(double dt_sec) {
        double ang_red = Math.toRadians(_ang);
        double h_acc = Math.sin(ang_red) * _acc;
        double v_acc = Math.cos(ang_red) * _acc;
        double vacc = Moon.getAcc(_hs);
        _time += dt_sec;
        double dw = dt_sec * ALL_BURN * _power;
        if (_fuel > 0) {
            _fuel -= dw;
            _weight = WEIGHT_EMP + _fuel;
            _acc = _power * accMax(_weight);
        } else {
            _acc = 0;
        }
        v_acc -= vacc;
        if (_hs > 0) {
            _hs -= h_acc * dt_sec;
        }
        _h_dist -= _hs * dt_sec;
        _vs -= v_acc * dt_sec;
        _alt -= dt_sec * _vs;
    }

}
