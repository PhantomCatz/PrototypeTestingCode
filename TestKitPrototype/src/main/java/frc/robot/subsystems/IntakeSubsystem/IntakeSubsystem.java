// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utilities.LoggedTunableNumber;

public class IntakeSubsystem extends SubsystemBase {

  private final IntakeIO io;
  private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

    static LoggedTunableNumber tunableNumber = new LoggedTunableNumber("Intake/MotorPower", 0.5);


  /** Creates a new OnOffSubsystem. */
  public IntakeSubsystem() {
    io = new IntakeIOReal() {};
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    // System.out.println(tunableNumber.get());
    Logger.processInputs("Intake", inputs);
  }

  public Command runMotor() {
    return startEnd(() -> io.runMotor(tunableNumber.getAsDouble()), () -> io.runMotor(0));
  }
}
