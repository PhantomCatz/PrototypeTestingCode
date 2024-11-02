// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.util.Units;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOReal implements IntakeIO {
  
  TalonFX talonMotor = new TalonFX(4);
  // CANSparkMax sparkMotor = new CANSparkMax(20, MotorType.kBrushless);
  
  // Status Signals
  private final StatusSignal<Double> Position;
  private final StatusSignal<Double> Velocity;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> SupplyCurrent;
  private final StatusSignal<Double> TorqueCurrent;
  private final StatusSignal<Double> TempCelsius;

  /** Creates a new IntakeIOReal. */
  public IntakeIOReal() {
    Position = talonMotor.getPosition();
    Velocity = talonMotor.getVelocity();
    AppliedVolts = talonMotor.getMotorVoltage();
    SupplyCurrent = talonMotor.getSupplyCurrent();
    TorqueCurrent = talonMotor.getTorqueCurrent();
    TempCelsius = talonMotor.getDeviceTemp();

    BaseStatusSignal.setUpdateFrequencyForAll(
      100.0,
      Position,
      Velocity,
      AppliedVolts,
      SupplyCurrent,
      TorqueCurrent,
      TempCelsius
    );
  }

  @Override
  public void updateInputs(IntakeIOInputs inputs) {
    inputs.isIntakeIOMotorConnected =
        BaseStatusSignal.refreshAll(
                Position,
                Velocity,
                AppliedVolts,
                SupplyCurrent,
                TorqueCurrent,
                TempCelsius)
            .isOK();
    inputs.PositionMechs = Units.rotationsToRadians(Position.getValueAsDouble());
    inputs.VelocityRpm = Velocity.getValueAsDouble() * 60.0;
    inputs.AppliedVolts = AppliedVolts.getValueAsDouble();
    inputs.SupplyCurrentAmps = SupplyCurrent.getValueAsDouble();
    inputs.TorqueCurrentAmps = TorqueCurrent.getValueAsDouble();
    inputs.TempCelsius = TempCelsius.getValueAsDouble();
  }
  
  //Sets the motor speed to "speed" found in IntakeIO. Currently the number is 1000%
  @Override
  public void runMotor() {  
    talonMotor.set(IntakeIOInputs.speed);
    // sparkMotor.set(IntakeIOInputs.speed);

    Logger.recordOutput("Intake Motor Speed", IntakeIOInputs.speed); // Logs the motor speed and names it to "Intake Motor Speed" in AdvantageScope

  }
}
