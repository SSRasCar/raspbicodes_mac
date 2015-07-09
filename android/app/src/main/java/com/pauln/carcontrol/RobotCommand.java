package com.pauln.carcontrol;

/**
 * Maps server robot commands to enums
 */
public enum RobotCommand
{
    Forward("FF", "Forward"),
    Back("BB", "Back"),
    Left("LL", "Left"),
    Right("RR", "Right"),
    Stop(".", "Stop", ButtonType.Toggle);

    private String commandCode;
    private String description;
    private ButtonType buttonType;

    RobotCommand(String commandCode, String description)
    {
        this.commandCode = commandCode;
        this.description = description;
        this.buttonType = ButtonType.Touch;
    }

    RobotCommand(String commandCode, String description, ButtonType buttonType)
    {
        this.commandCode = commandCode;
        this.description = description;
        this.buttonType = buttonType;
    }

    public String getCommandCode()
    {
        return commandCode;
    }

    public String getDescription()
    {
        return description;
    }

    public ButtonType getButtonType()
    {
        return buttonType;
    }
}
