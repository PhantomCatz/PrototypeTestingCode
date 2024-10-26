// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.AutoLog;

public interface VelocityIO{

    @AutoLog
    public static class VelocityIOInputs {

        public boolean isVelocityIOMotorConnected = true;

        public static int motorPower;

        public double PositionMechs = 0.0;
        public double VelocityRpm = 0.0;
        public double AppliedVolts = 0.0;
        public double SupplyCurrentAmps = 0.0;
        public double TorqueCurrentAmps = 0.0;
        public double TempCelsius = 0.0;

    }

    public default void updateInputs(VelocityIOInputs inputs) {}

    public default void runVelocity(double Rpm) {}

    public default void toggleRunVelocity() {}
}
