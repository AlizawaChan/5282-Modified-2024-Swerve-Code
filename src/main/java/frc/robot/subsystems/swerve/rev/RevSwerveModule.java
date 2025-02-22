
package frc.robot.subsystems.swerve.rev;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.lib.util.swerveUtil.CTREModuleState;
//import frc.robot.lib.util.swerveUtil.CTREModuleState;
import frc.robot.lib.util.swerveUtil.RevSwerveModuleConstants;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
//import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.RelativeEncoder;
//import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.Faults;
import com.revrobotics.spark.SparkBase.PersistMode;
//import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ResetMode;

/**
 * a Swerve Modules using REV Robotics motor controllers and CTRE CANcoder absolute encoders.
 */
public class RevSwerveModule implements SwerveModule
{
    private SparkMaxConfig motorConfig;
    public int moduleNumber;
    private Rotation2d rotOffset;
   // private Rotation2d lastAngle;

    private SparkMax mAngleMotor;
    private SparkMax mDriveMotor;

    private CANcoder angleEncoder;
    private RelativeEncoder relAngleEncoder;
    private RelativeEncoder relDriveEncoder;
    private SwerveModuleState m_desiredState;
    private SparkMaxConfig angleConfig = new SparkMaxConfig();
    private SparkMaxConfig driveConfig = new SparkMaxConfig();

    //SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

    public RevSwerveModule(int moduleNumber, RevSwerveModuleConstants moduleConstants)
    {
        this.moduleNumber = moduleNumber;
        this.rotOffset = moduleConstants.rotOffset;
        
        motorConfig = new SparkMaxConfig();

        /* Angle Motor Config */
        mAngleMotor = new SparkMax(moduleConstants.angleMotorID, MotorType.kBrushless);
        configAngleMotor();

        /* Drive Motor Config */
        mDriveMotor = new SparkMax(moduleConstants.driveMotorID,  MotorType.kBrushless);
        configDriveMotor();

         /* Angle Encoder Config */
    
        angleEncoder = new CANcoder(moduleConstants.cancoderID);
        configEncoders();

        
       // lastAngle = getState().angle;

       
    }


    private void configEncoders()
    {     

        //double driveRevToMeters = RevSwerveConfig.driveRevToMeters;
        //double DegreesPerTurnRotation = RevSwerveConfig.DegreesPerTurnRotation;

        // absolute encoder   
      
        angleEncoder.getConfigurator().apply(new CANcoderConfiguration());
        //angleEncoder.configAllSettings(new RevSwerveConfig().canCoderConfig);
       
        relDriveEncoder = mDriveMotor.getEncoder();
        relDriveEncoder.setPosition(0);

         
        //relDriveEncoder.setPosition(relDriveEncoder.getPosition() * driveRevToMeters);
        relDriveEncoder.setPosition(RevSwerveConfig.driveRevToMeters);
        relDriveEncoder.getVelocity();

        
        relAngleEncoder = mAngleMotor.getEncoder();
        relAngleEncoder.setPosition(RevSwerveConfig.DegreesPerTurnRotation);
        // in degrees/sec
        relAngleEncoder.getVelocity();
    

        resetToAbsolute();
        mDriveMotor.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        mAngleMotor.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
        
        
    }

    @SuppressWarnings("deprecation")
    private void configAngleMotor()
    {
        //mAngleMotor.restoreFactoryDefaults();
        //SparkClosedLoopController controller = mAngleMotor.getClosedLoopController();
        motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(RevSwerveConfig.angleKP)
        .i(RevSwerveConfig.angleKI)
        .d(RevSwerveConfig.angleKD)
        .velocityFF(RevSwerveConfig.angleKF)
        .outputRange(-RevSwerveConfig.anglePower, RevSwerveConfig.anglePower);
        
        
        //mAngleMotor.setSmartCurrentLimit(RevSwerveConfig.angleContinuousCurrentLimit);
       
        //mAngleMotor.setInverted(RevSwerveConfig.angleMotorInvert);
        //mAngleMotor.setIdleMode(RevSwerveConfig.angleIdleMode);

        angleConfig.inverted(false);
        mAngleMotor.configure(angleConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        
       
    }
    @SuppressWarnings("deprecation")
    private void configDriveMotor()
    {        
        //mDriveMotor.restoreFactoryDefaults();
        //SparkClosedLoopController controller = mDriveMotor.getClosedLoopController();
        motorConfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .p(RevSwerveConfig.angleKP)
        .i(RevSwerveConfig.angleKI)
        .d(RevSwerveConfig.angleKD)
        .velocityFF(RevSwerveConfig.angleKF)
        .outputRange(-RevSwerveConfig.anglePower, RevSwerveConfig.anglePower);
        //mDriveMotor.setSmartCurrentLimit(RevSwerveConfig.driveContinuousCurrentLimit);
        //mDriveMotor.setInverted(RevSwerveConfig.driveMotorInvert);
        //mDriveMotor.setIdleMode(RevSwerveConfig.driveIdleMode); 
        
       driveConfig.inverted(false);
       mDriveMotor.configure(angleConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
       
    }



    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop)
    {
        
        
        /* This is a custom optimize function, since default WPILib optimize assumes continuous controller which CTRE and Rev onboard is not */
        // CTREModuleState actually works for any type of motor.
        desiredState = CTREModuleState.optimize(desiredState, getState().angle); 
        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);

        if(mDriveMotor.getFaults() != null)
        {
            DriverStation.reportWarning("Sensor Fault on Drive Motor ID:"+mDriveMotor.getDeviceId(), false);
        }

        if(mAngleMotor.getFaults() != null)
        {
            DriverStation.reportWarning("Sensor Fault on Angle Motor ID:"+mAngleMotor.getDeviceId(), false);
        }
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop)
    {
       
        if(isOpenLoop)
        {
            double percentOutput = desiredState.speedMetersPerSecond / RevSwerveConfig.maxSpeed;
            mDriveMotor.set(percentOutput);
            return;
        }
 
        double velocity = desiredState.speedMetersPerSecond;
        
        SparkClosedLoopController controller = mDriveMotor.getClosedLoopController();
        controller.setReference(velocity, ControlType.kVelocity, ClosedLoopSlot.kSlot0, 0);
        
    }

    private void setAngle(SwerveModuleState desiredState)
    {
        if(Math.abs(desiredState.speedMetersPerSecond) <= (RevSwerveConfig.maxSpeed * 0.01)) 
        {
            mAngleMotor.stopMotor();
            return;

        }
        Rotation2d angle = desiredState.angle; 
        //Prevent rotating module if speed is less then 1%. Prevents Jittering.
        
        SparkClosedLoopController controller = mAngleMotor.getClosedLoopController();
        
        double degReference = angle.getDegrees();
     
       
        
        controller.setReference (degReference, ControlType.kPosition, ClosedLoopSlot.kSlot0, 0);
        
    }

    public SwerveModuleState getDesiredState() {
        return m_desiredState; //TODO questionable
    }

    public Rotation2d getAngle()
    {
        return Rotation2d.fromDegrees(relAngleEncoder.getPosition());
    }

    public Rotation2d getDesiredAngle() {
        return getDesiredState().angle;
    }

    public double getDesiredVelocity() {
        return getDesiredState().speedMetersPerSecond;
    }

    public Rotation2d getCANcoder()
    {
        return Rotation2d.fromRotations(angleEncoder.getAbsolutePosition().getValueAsDouble());
        //return getAngle();
    }

    public int getModuleNumber() 
    {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) 
    {
        this.moduleNumber = moduleNumber;
    }

    private void resetToAbsolute()
    {
    
        double absolutePosition =getCANcoder().getDegrees() - rotOffset.getDegrees();
        relAngleEncoder.setPosition(absolutePosition);
    }

  

    public SwerveModuleState getState()
    {
        return new SwerveModuleState(
            relDriveEncoder.getVelocity(),
            getAngle()
        ); 
    }

    public SwerveModulePosition getPosition()
    {
        return new SwerveModulePosition(
            relDriveEncoder.getPosition(), 
            getAngle()
        );
    }

    

}