package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DriveTrain extends SubsystemBase {

    int lfMotor = 3;
    int lbMotor = 4;

    int rfMotor = 1;
    int rbMotor = 2;

    private SpeedControllerGroup lD = new SpeedControllerGroup(createSPARK(lfMotor), createSPARK(rbMotor));
    private SpeedControllerGroup rD = new SpeedControllerGroup(createSPARK(rfMotor), createSPARK(rbMotor));

    public void init() {
        rD.setInverted(true);
    }

    public void set(double l, double r) {

        if (Math.abs(l) < 0.05)
            l = 0.0;

        if (Math.abs(r) < 0.05)
            r = 0.0;

        lD.set(l);
        rD.set(r);
    }

    private CANSparkMax createSPARK(int id) {
        return new CANSparkMax(id, MotorType.kBrushless);
    }

}
