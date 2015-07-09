package com.pauln.carcontrol;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 *
 */
public class UDButtonsFragment extends Fragment
{
    private ButtonDispatcher buttonDispatcher;

    public static UDButtonsFragment newInstance()
    {
        return new UDButtonsFragment();
    }

    public UDButtonsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ud_buttons, container, false);

        // Setup the listeners and map an arm command for each button
        initialiseButtons(view);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            // Get the button dispatcher held by the main activity
            ButtonDispatcherInterface buttonDispatcherInterface = (ButtonDispatcherInterface) activity;
            buttonDispatcher = buttonDispatcherInterface.getButtonDispatcher();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement ButtonDispatcherInterface");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        buttonDispatcher = null;
    }

    /**
     * Setup listeners and button commands for all buttons in this fragment
     */
    private void initialiseButtons(View view)
    {
        buttonDispatcher.setupButton((ImageButton) view.findViewById(R.id.moveForwardButton), RobotCommand.Forward);
        buttonDispatcher.setupButton((ImageButton) view.findViewById(R.id.moveBackButton), RobotCommand.Back);
    }

}
