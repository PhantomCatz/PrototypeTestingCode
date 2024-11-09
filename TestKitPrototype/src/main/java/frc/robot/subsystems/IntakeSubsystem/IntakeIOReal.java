// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.util.Units;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOReal implements IntakeIO {
  
  TalonFX talonMotor = new TalonFX(21);
  CANSparkMax sparkMotor = new CANSparkMax(20, MotorType.kBrushless);
  
  // Status Signals
  private final StatusSignal<Double> Position;
  private final StatusSignal<Double> Velocity;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> SupplyCurrent;
  private final StatusSignal<Double> TorqueCurrent;
  private final StatusSignal<Double> TempCelsius;

  private final TalonFXConfiguration config = new TalonFXConfiguration();

  private final VoltageOut voltageControl = new VoltageOut(0).withUpdateFreqHz(0.0);


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
  public void runMotor(double Speed) {  
    talonMotor.set(Speed);
    // sparkMotor.set(IntakeIOInputs.speed);

    Logger.recordOutput("Intake Motor Speed", Speed); // Logs the motor speed and names it to "Intake Motor Speed" in AdvantageScope

  }

  @Override
  public void runSparkMax(double Speed) {  
    sparkMotor.set(Speed);

    Logger.recordOutput("SparkMax Intake Motor Speed", Speed); // Logs the motor speed and names it to "Intake Motor Speed" in AdvantageScope

  }

  @Override
  public void setPID(double kP, double kI, double kD) {
    config.Slot0.kP = kP;
    config.Slot0.kI = kI;
    config.Slot0.kD = kD;
    System.out.println("kP: " + kP + " kI: " + kI + " kD: " + kD);
    talonMotor.getConfigurator().apply(config);
  }

  @Override
  public void runCharacterizationMotor(double input) {
    talonMotor.setControl(voltageControl.withOutput(input));
  }

  @Override
  public void setFF(double kS, double kV, double kA) {
    config.Slot0.kS = kS;
    config.Slot0.kV = kV;
    config.Slot0.kA = kA;
    System.out.println("kS: " + kS + " kV: " + kV + " kA: " + kA);
    talonMotor.getConfigurator().apply(config);
  }
}
