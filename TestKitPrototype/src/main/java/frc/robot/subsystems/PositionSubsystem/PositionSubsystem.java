// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.PositionSubsystem;

import java.lang.reflect.Method;
import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utilities.LoggedTunableNumber;
import frc.robot.subsystems.VelocitySubsystem.VelocityIOInputsAutoLogged;
import lombok.RequiredArgsConstructor;

public class PositionSubsystem extends SubsystemBase {
  
  private final PositionIO io;
  private final PositionIOInputsAutoLogged inputs = new PositionIOInputsAutoLogged();
  private Position PositionType;
  static double position;
  static LoggedTunableNumber tunnablePos = new LoggedTunableNumber("Position/TunnablePosition", 1);
  static LoggedTunableNumber kP = new LoggedTunableNumber("Position/kP", 0.17);
  static LoggedTunableNumber kI = new LoggedTunableNumber("Position/kI", 0.0);
  static LoggedTunableNumber kD = new LoggedTunableNumber("Position/kD", 0.0006);

  static LoggedTunableNumber kS = new LoggedTunableNumber("Position/kS", 0);
  static LoggedTunableNumber kV = new LoggedTunableNumber("Position/kV", 0);
  static LoggedTunableNumber kA = new LoggedTunableNumber("Position/kA", 0);

  /** Creates a new PositionSubsystem. */
  public PositionSubsystem() {
        // io = new PositionIOKraken() {};
        io = new PositionIOSparkMax() {};

        io.setPID(kP.getAsDouble(), kI.getAsDouble(), kD.getAsDouble());
  }
  //==========================================================//
  // ^^ Hallo make sure you set this to the correct motor ^^  //
  //==========================================================//
  @RequiredArgsConstructor
  public enum Position {

    ZERO(() -> 0.0),
    TUNNABLE(tunnablePos);

    private final DoubleSupplier motionType;
    private double getTargetMotionPosition() {
      return motionType.getAsDouble();
    }
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Position", inputs);
    // System.out.println(position);
    if(DriverStation.isDisabled()) {

    } else {
      io.setPosition(position);
    }
    Logger.recordOutput("Position/targetPosition", position);
  }

  public Command setPosition() {
    return startEnd(() -> position = Position.TUNNABLE.getTargetMotionPosition(), () -> position = Position.ZERO.getTargetMotionPosition());
  }
}

