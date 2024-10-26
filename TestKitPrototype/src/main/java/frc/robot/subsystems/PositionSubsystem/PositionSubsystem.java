// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.PositionSubsystem;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utilities.LoggedTunableNumber;
import frc.robot.subsystems.VelocitySubsystem.VelocityIOInputsAutoLogged;
import lombok.RequiredArgsConstructor;

public class PositionSubsystem extends SubsystemBase {
  
  private final PositionIO io;
  private final PositionIOInputsAutoLogged inputs = new PositionIOInputsAutoLogged();
  private Position PositionType;

  /** Creates a new PositionSubsystem. */
  public PositionSubsystem() {
        io = new PositionIOReal() {};

  }

  @RequiredArgsConstructor
  public enum Position {

    HELLO(() -> 0.0),
    TUNNABLE(new LoggedTunableNumber("TunnablePosition", 10));

    private final DoubleSupplier motionType;
    private double getTargetMotionPosition() {
      return motionType.getAsDouble();
    }
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Position", inputs);
    // io.setPosition(0);
    // io.runSetpointTicks(PositionType.getTargetMotionPosition());
  }

  public Command setPosition() {
    return runOnce(() -> io.setPosition(8));
  }
}
