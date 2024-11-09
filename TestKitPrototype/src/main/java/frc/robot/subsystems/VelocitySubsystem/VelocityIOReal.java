// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.VoltageConfigs;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

public class VelocityIOReal implements VelocityIO {
    
  TalonFX talonMotor = new TalonFX(5);

  private final VelocityVoltage velocityControl = new VelocityVoltage(0).withUpdateFreqHz(60.0);
  private final VoltageOut voltageControl = new VoltageOut(0).withUpdateFreqHz(0.0);
  // Status Signals
  private final StatusSignal<Double> Position;
  private final StatusSignal<Double> Velocity;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> SupplyCurrent;
  private final StatusSignal<Double> TorqueCurrent;
  private final StatusSignal<Double> TempCelsius;
  
  TalonFXConfiguration config = new TalonFXConfiguration();


  /** Creates a new IntakeIOReal. */
  public VelocityIOReal() {
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


    config.Slot0.kP = 0.17;
    config.Slot0.kI = 0;
    config.Slot0.kD = 0.0006;

    config.CurrentLimits.SupplyCurrentLimit = 60.0;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;

    talonMotor.getConfigurator().apply(config, 1.0);

    talonMotor.optimizeBusUtilization(0, 1.0);

  }

  @Override
  public void updateInputs(VelocityIOInputs inputs) {
    inputs.isVelocityIOMotorConnected =
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

  @Override
  public void runVelocity(double Rpm) {
    talonMotor.setControl(
        velocityControl
            .withVelocity(Rpm / 60.0)
    );
    Logger.recordOutput("VelocityStuff", Rpm);
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
