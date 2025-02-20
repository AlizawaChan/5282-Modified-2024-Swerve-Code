package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.lib.util.swerveUtil.RevSwerveModuleConstants;


/**
 * This file comes with command robot projects, and is intended to contain
 * configuration information.
 * I think it would be prudent if this file only contained CanIDs, because it
 * is useful to have all the ids for the whole robot in one place.
 * other configuration goes into subsystem specific configuration files,
 * to make sure this one isn't cluttered.
 */
public final class RevSwerveConstants 
{
    public static final double stickDeadband = 0.1;
    public static final double limelightOffset = 3;
    
    /* Angle Encoder Invert */
    //public static final SensorDirectionValue cancoderInvert = chosenModule.cancoderInvert;

    public static final class REV
    {
        public static final int pigeonID = 5;

       
    
    }
    public static final class Swerve {
        /* Module Specific Constants */
    /* Front Left Module - Module 0 */
       
    public static final class Mod0 { 

            public static final int driveMotorID = 6;
            public static final int angleMotorID = 7;
            public static final int canCoderID = 8;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(-0.060547);
            public static final RevSwerveModuleConstants constants = 
                new RevSwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, rotOffset);
        }
        /* Front Right Module - Module 1 */
        public static final class Mod1 { 
            public static final int driveMotorID = 9;
            public static final int angleMotorID = 10;
            public static final int canCoderID = 11;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(0.468750);
            public static final RevSwerveModuleConstants constants = 
                new RevSwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, rotOffset);
        }
        /* Back Left Module - Module 2 */
        public static final class Mod2 { 
            public static final int driveMotorID = 12;
            public static final int angleMotorID = 13;
            public static final int canCoderID = 14;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(0.216553);
            public static final RevSwerveModuleConstants constants = 
                new RevSwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, rotOffset);
        }
         /* Back Right Module - Module 3 */
        public static final class Mod3 { 
            public static final int driveMotorID = 15;
            public static final int angleMotorID = 16;
            public static final int canCoderID = 17;
            public static final Rotation2d rotOffset = Rotation2d.fromRotations(-0.242188);
            public static final RevSwerveModuleConstants constants = 
                new RevSwerveModuleConstants(driveMotorID, angleMotorID, canCoderID, rotOffset);
        }
        
    }

     

    
    
    

   

}