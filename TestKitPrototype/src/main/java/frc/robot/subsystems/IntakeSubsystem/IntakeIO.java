// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO{

    @AutoLog
    public static class IntakeIOInputs {
      //Store variables here
      public boolean isIntakeIOMotorConnected = true;

      public double PositionMechs = 0.0;
      public double VelocityRpm = 0.0;
      public double AppliedVolts = 0.0;
      public double SupplyCurrentAmps = 0.0;
      public double TorqueCurrentAmps = 0.0;
      public double TempCelsius = 0.0;

    }

    public default void runMotor(double Speed) {}

    public default void runSparkMax(double Speed) {}

    public default void updateInputs(IntakeIOInputs inputs) {}

    public default void setPID(double kP, double kI, double kD) {}

    public default void setFF(double kS, double kV, double kA) {}
    
    public default void runCharacterizationMotor(double input) {}
}
