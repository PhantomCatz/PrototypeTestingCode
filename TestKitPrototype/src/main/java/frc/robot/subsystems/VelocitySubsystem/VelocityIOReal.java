// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

public class VelocityIOReal implements VelocityIO {
    
  TalonFX talonMotor = new TalonFX(1);

  private final VelocityVoltage velocityControl = new VelocityVoltage(0).withUpdateFreqHz(0.0);
 
  private final TalonFXConfiguration config = new TalonFXConfiguration();


  // Status Signals
  private final StatusSignal<Double> Position;
  private final StatusSignal<Double> Velocity;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> SupplyCurrent;
  private final StatusSignal<Double> TorqueCurrent;
  private final StatusSignal<Double> TempCelsius;

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

    config.Slot0.kP = 8;
    config.Slot0.kI = 0;
    config.Slot0.kD = 0;

    config.CurrentLimits.SupplyCurrentLimit = 60.0;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;

    talonMotor.getConfigurator().apply(config, 1.0);
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
            .withFeedForward(0)
    );
    Logger.recordOutput("VelocityStuff", Rpm);
  }

  @Override
  public void toggleRunVelocity() {
    if(VelocityIOInputs.motorPower != 0) {
      VelocityIOInputs.motorPower = 0;
    } else {
      VelocityIOInputs.motorPower = 1;
    }
  }
}
