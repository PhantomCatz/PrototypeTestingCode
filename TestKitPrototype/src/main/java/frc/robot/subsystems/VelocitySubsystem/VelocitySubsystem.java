// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utilities.LoggedTunableNumber;

public class VelocitySubsystem extends SubsystemBase {

  private final VelocityIO io;
  private final VelocityIOInputsAutoLogged inputs = new VelocityIOInputsAutoLogged();

  private double targetRpm;

  static LoggedTunableNumber tunableNumber = new LoggedTunableNumber("Velocity/MotorPower", 500);
  static LoggedTunableNumber kP = new LoggedTunableNumber("Velocity/kP", 0.17);
  static LoggedTunableNumber kI = new LoggedTunableNumber("Velocity/kI", 0.0);
  static LoggedTunableNumber kD = new LoggedTunableNumber("Velocity/kD", 0.0006);

  static LoggedTunableNumber kS = new LoggedTunableNumber("Velocity/kS", 0);
  static LoggedTunableNumber kV = new LoggedTunableNumber("Velocity/kV", 0);
  static LoggedTunableNumber kA = new LoggedTunableNumber("Velocity/kA", 0);

  /** Creates a new VelocitySubsystem. */
  public VelocitySubsystem() {
        io = new VelocityIOReal() {};
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Velocity", inputs);
    // This method will be called once per scheduler run
    // System.out.println(tunableNumber.get());
    if(DriverStation.isDisabled()) {

    } else {
      io.runVelocity(targetRpm);
    }

    LoggedTunableNumber.ifChanged(hashCode(), pid-> io.setPID(pid[0], pid[1], pid[2]), kP, kI, kD);
    LoggedTunableNumber.ifChanged(hashCode(), ff-> io.setFF(ff[0], ff[1], ff[2]), kS, kV, kA);

    Logger.recordOutput("Velocity", targetRpm);
  }

  public Command onVelocity(){
    return startEnd(() -> targetRpm = tunableNumber.get(), () -> targetRpm = 0.0);
  }

  // public Command setVelocityOff() {
  //   return startEnd(null, null)
  // }

}

