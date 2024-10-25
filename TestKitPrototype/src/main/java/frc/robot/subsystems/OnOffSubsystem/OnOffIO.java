// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.OnOffSubsystem;

import org.littletonrobotics.junction.AutoLog;

public interface OnOffIO{

    @AutoLog
    public static class IntakeIOInputs {}


    public default void toggleMotor() {}
}
