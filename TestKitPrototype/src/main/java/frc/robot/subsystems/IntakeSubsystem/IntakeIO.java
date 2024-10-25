// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO{

    @AutoLog
    public static class IntakeIOInputs {
      //Store variables here
      public static double speed = 10;

    }

    public default void runMotor() {}



}
