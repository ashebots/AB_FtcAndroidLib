package org.ashebots.ftcandroidlib.misc;

/*
Generic boolean toggleable state, meant to be called in a loop.

toggleState(false) must be called at least once in between each toggleState(true)
 */
public class Toggle
{
    private boolean state = false;
    private boolean toggledOnLastCall = false;

    public Toggle()
    {
        this(false);
    }
    public Toggle(boolean initialState)
    {
        this.state = initialState;
    }

    //Meant to be called in loop
    //If shouldToggle is true, but was false last time, it toggles.
    //If shouldToggle is true, but was true last time, it won't toggle.
    public void toggleState(boolean shouldToggle)
    {
        if (shouldToggle)
        {
            if (toggledOnLastCall == false)
            {
                state = !state; //Invert state
                toggledOnLastCall = true;
            }
        }
        else
        {
            toggledOnLastCall = false; //Reset lock
        }
    }

    public boolean getState()
    {
        return this.state;
    }
}
