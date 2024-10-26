// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.OnOffSubsystem;

import org.littletonrobotics.junction.AutoLog;

public interface OnOffIO{

    @AutoLog
    public static class OnOffIOInputs {

        public boolean isOnOffMotorConnected = true;

        public double PositionMechs = 0.0;
        public double VelocityRpm = 0.0;
        public double AppliedVolts = 0.0;
        public double SupplyCurrentAmps = 0.0;
        public double TorqueCurrentAmps = 0.0;
        public double TempCelsius = 0.0;

    }
    
    public default void updateInputs(OnOffIOInputs inputs) {}

    public default void toggleMotor() {}
}
