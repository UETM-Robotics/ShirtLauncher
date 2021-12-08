package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {

    private Compressor compressor = new Compressor();
    private AnalogInput gauge = new AnalogInput(3);
    private Solenoid trigger = new Solenoid(0);
    private Timer timer = new Timer();

    int a = 0;

    @Override
    public void periodic() {
        System.out.println(gauge.getValue() / 27.5);
    }

    public void start() {
        compressor.start();
    }

    public void stop() {
        compressor.stop();
    }

    public void shoot() {

        trigger.set(true);

        timer.reset();

        timer.start();
        while (timer.get() < 0.5) {
            a = 0;
        }

        timer.stop();

        trigger.set(false);

    }

}
